<template>
  <div>
    <el-form ref="form" :rules="rules" :model="sysRole" label-width="80px" size="small">
      <el-form-item label="角色名" prop="roleName">
        <el-input v-model="sysRole.roleName" placeholder="请输入角色名" clearable/>
      </el-form-item>
      <el-form-item label="排序" prop="roleSort">
        <el-input-number v-model="sysRole.roleSort" style="width: 100%" controls-position="right" :min="1"/>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="sysRole.remark" placeholder="请输入备注" type="textarea" clearable/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="sysRole.status">
          <el-radio v-for="status in statusOptions" :key="status.dictValue" :label="status.dictValue">{{ status.dictLabel }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="update">修改</el-button>
        <el-button type="warning" @click="sysRole = {}">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import sysRoleApi from '@/api/system/sysRole'
import sysDictDataApi from "@/api/system/sysDictData";
export default {
  props: {
    // 父组件传递的角色ID
    activeId: {
      type: Number,
      default: null
    }
  },
  watch: {
    activeId: {
      immediate: true,
      handler: function(newVal, oldVal) {
        this.getById(newVal)
      }
    }
  },
  name: "sys-role-update" ,
  data() {
    return {
      // 表单对象
      sysRole: {
        status: 0
      },
      // 表单校验
      rules: {
        roleName: [
          { required: true, message: "角色名称不能为空", trigger: "blur" }
        ],
        roleSort: [
          { required: true, message: "角色排序码不能", trigger: "blur" }
        ]
      },
      //状态数据
      statusOptions:[],
    }
  },
  created() {
    sysDictDataApi.getDictDataByDictType("sys_status_type").then(res=>{
      this.statusOptions=res.data;
    })
  },
  methods: {
    // 修改角色
    update() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          sysRoleApi.update(this.sysRole).then(res => {
            this.$message.success(res.msg)
            this.$emit('after')
            this.$emit('close')
          })
        }
      })
    },
    // 根据id查询
    getById(id) {
      sysRoleApi.get(id).then(res => {
        this.sysRole = res.data
      })
    }
  }
}
</script>

<style scoped>

</style>
