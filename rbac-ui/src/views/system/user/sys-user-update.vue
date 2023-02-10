<template>
  <div>
    <el-form ref="form" :model="sysUser"  :rules="rules" label-width="80px" size="small">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="所在部门">
            <treeselect
              v-model="sysUser.deptId"
              :options="deptOptions"
              :normalizer="normalizer"
              :show-count="true"
              placeholder="选择所在部门"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="账号" prop="userName">
            <el-input v-model="sysUser.userName" placeholder="请输入账号" clearable />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="昵称" prop="nickName">
            <el-input v-model="sysUser.nickName" placeholder="请输入昵称" clearable />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="sysUser.email" placeholder="请输入邮箱" clearable />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="sysUser.phone" placeholder="请输入手机号" clearable />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="性别" prop="sex">
            <el-radio-group v-model="sysUser.sex">
              <el-radio v-for="status in sexOptions" :key="status.dictValue" :label="status.dictValue">{{ status.dictLabel }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="sysUser.status">
              <el-radio v-for="status in statusOptions" :key="status.dictValue" :label="status.dictValue">{{ status.dictLabel }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item>
        <el-button type="primary" @click="update">修改</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import sysUserApi from "@/api/system/sysUser"
import {handleTree} from "@/utils/leige";
export default {
  components: {
    Treeselect
  },
  created() {
    this.deptOptions=handleTree(this.deptOptions,"deptId");
          this.sysUser=JSON.parse(JSON.stringify(this.activeObj));
  },
  props:{
    activeObj:{
      type:Object,
      default: {}
    },
    sexOptions:{
      type:Array,
      default: []
    },
    statusOptions:{
      type:Array,
      default:[]
    },
    deptOptions:{
      type:Array,
      default:[]
    }
  },
  data() {
    return {
      // 用户表单
      sysUser: {},
      // 表单校验对象
      rules: {
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 5, message: '用户名最短5位', trigger: 'change' }
        ],
        nickName: [
          { required: true, message: '请输入昵称', trigger: 'blur' }
        ]
      },
    }
  },
  methods: {
    // 根据id查询用户
    getById(id) {
      sysUserApi.get(id).then(res => {
        this.sysUser = res.data
      })
    },
    // 修改用户
    update() {
      sysUserApi.update(this.sysUser).then(res => {
        this.$message.success(res.msg)
        // 关闭对话框，并刷新列表
        this.$emit('close')
        this.$emit('after')
      })
    },
    /** 转换菜单数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children
      };
    },
    /** 查询菜单下拉树结构 */
    getTreeselect() {
      sysDeptApi.listDept().then(res => {
        this.deptOptions = [];
        this.deptOptions.push(handleTree(res.data, "deptId")[0]);
      });
    }
  }
}
</script>

<style>

</style>
