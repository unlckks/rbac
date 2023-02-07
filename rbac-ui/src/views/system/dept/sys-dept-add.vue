<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row>
        <el-col :span="24">
          <el-form-item label="上级部门" prop="parentId">
            <treeselect
              v-model="form.parentId"
              :options="deptOptions"
              :normalizer="normalizer"
              :show-count="true"
              placeholder="选择上级部门"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="部门名称" prop="deptName">
            <el-input v-model="form.deptName" placeholder="请输入部门名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="显示排序" prop="orderNum">
            <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="负责人" prop="deptName">
            <el-input v-model="form.leader" placeholder="请输入负责人" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联系电话" prop="orderNum">
            <el-input v-model="form.phone" placeholder="请输入联系电话" controls-position="right" :min="0" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="form.email" placeholder="请输入邮箱" controls-position="right" :min="0" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="部门状态" prop="status">
            <el-radio-group v-model="form.status">
              <el-radio v-for="status in statusOptions" :label="status.dictValue">{{ status.dictLabel }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :sapn="24" style="text-align: center">
          <el-button type="primary" @click="add">添加</el-button>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import sysDeptApi from '@/api/system/sysDept'
import sysDictDataApi from '@/api/system/sysDictData'
import { handleTree } from '@/utils/leige'

export default {
  name: 'SysDeptAdd',
  components: {
    Treeselect
  },
  props: {
    activeObj: {
      type: Object,
      default: undefined
    }
  },
  data() {
    return {
      form: {
        parentId: undefined,
        status: 0
      },
      rules: {
        parentId: [
          { required: true, message: '上级部门不能为空', trigger: 'blur' }
        ],
        deptName: [
          { required: true, message: '部门名称不能为空', trigger: 'blur' }
        ],
        orderNum: [
          { required: true, message: '显示排序不能为空', trigger: 'blur' }
        ],
        email: [
          {
            type: 'email',
            message: '请输入正确的邮箱地址',
            trigger: ['blur', 'change']
          }
        ],
        phone: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: '请输入正确的手机号码',
            trigger: 'blur'
          }
        ]
      },
      // 部门树的数据
      deptOptions: [],
      // 状态数据
      statusOptions: []
    }
  },
  watch: {
    activeObj: {
      // 对象里面监听[ 对对象实现监听]
      immediate: true,
      handler: function(newVal, oldVal) {
        if (newVal != null) {
          // 处理下拉树选中
          this.form = { status: 0, parentId: newVal.deptId }
        } else {
          this.form = { status: 0, parentId: undefined }
        }
      }
    }
  },
  created() {
    //添加弹出层中的数据
    sysDeptApi.listDept({}).then(res => {
      this.deptOptions = handleTree(res.data, 'deptId')
    })
    sysDictDataApi.getDictDataByDictType('sys_status_type').then(res => {
      this.statusOptions = res.data
    })
  },
  methods: {
    // 转换部门数据结构
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children
      }
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children
      }
    },
    // 添加部门
    add() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          sysDeptApi.addDept(this.form).then(res => {
            this.$message.success(res.msg)
            this.$emit('after')
            this.$emit('close')
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
