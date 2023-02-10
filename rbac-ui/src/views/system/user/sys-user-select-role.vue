<template>
  <div>
    <el-table
      ref="roleTable"
      v-loading="loading"
      :data="roleList"
      border
      stripe
      @selection-change="handleSelectionChange"
      style="width: 100%"
    >
      <el-table-column  type="selection" width="55"></el-table-column>
      <el-table-column prop="roleId" label="角色编号" align="center"/>
      <el-table-column prop="roleName" label="角色名" align="center"/>
      <el-table-column prop="roleSort" align="center" label="排序"/>
      <el-table-column prop="remark" align="center" label="备注"/>
      <el-table-column prop="createTime" align="center" label="创建时间"/>
    </el-table>
    <div style="text-align: center;margin-top: 10px">
      <el-button type="primary"  icon="el-icon-plus" @click="saveUserRole">保存用户和角色关系</el-button>
    </div>
  </div>
</template>

<script>
import sysRoleApi from "@/api/system/sysRole"

export default {
  name: "sys-user-select-role",
  props: {
    activeObj: {
      type: Object,
      default: {}
    }
  },
  data() {
    return {
      //加载层
      loading:false,
      //角色数据
      roleList: [],
      //存放当前选中的角色对象
      roleIds:[]
    }
  },
  created() {
    //查询所有可用的角色不分页
    this.loading=true;
    sysRoleApi.getAllRoles().then(res => {
      this.roleList = res.data;
      //根据当前用户ID查询用户已拥有的角色ID
      sysRoleApi.getRoleIdsByUserId(this.activeObj.userId).then(res => {
        //找到要选中的对象
        let selectObj=[];
        this.roleList.filter(role=>{
          res.data.filter(roleId=>{
            if(role.roleId==roleId){
              this.$refs.roleTable.toggleRowSelection(role)
            }
          })
        })
      })
      this.loading=false;
    })


  },
  methods: {
    handleSelectionChange(arr){
      this.roleIds=[]
      arr.filter(role=>{
        this.roleIds.push(role.roleId)
      })
      console.log(this.roleIds)
    },
    //
    saveUserRole(){
      sysRoleApi.saveUserRoles(this.activeObj.userId,this.roleIds).then(res=>{
        this.$message.success("分配角色成功");
        this.$emit("close")
      })
    }
  }
}
</script>

<style scoped>

</style>
