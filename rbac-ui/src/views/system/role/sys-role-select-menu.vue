<template>
  <div>
    <el-tree
      ref="menuTree"
      :data="menuList"
      show-checkbox
      node-key="menuId"
      empty-text="暂无数据"
      :default-expand-all="true"
      :props="{ children: 'children', label: 'menuName'}">
    </el-tree>
    <div style="text-align: center">
      <el-button type="primary" @click="saveRoleMenu">保存角色和菜单</el-button>
    </div>
  </div>
</template>

<script>
import sysMenuApi from "@/api/system/sysMenu"
import sysRoleApi from "@/api/system/sysRole"
import {handleTree} from "@/utils/leige";

export default {
  name: "sys-role-select-menu",
  props:{
    activeId:{
      type:Number,
      default:undefined
    }
  },
  watch: {
    activeId: {
      immediate: true,
      handler: function(newVal, oldVal) {
        this.getCurrentRoleHasMenuIdsByRoleId(newVal)
      }
    }
  },
  data(){
    return{
      menuList:[]
    }
  },
  created() {
    sysMenuApi.listMenu({status:0}).then(res=>{
      this.menuList=handleTree(res.data,"menuId");
    });

  },
  methods:{
    //根据角色ID查询当前用户拥有的角色ID
    getCurrentRoleHasMenuIdsByRoleId(roleId){
      sysRoleApi.getCurrentRoleHasMenuIdsByRoleId(roleId).then(res=>{
        console.log(res.data)
        this.$refs.menuTree.setCheckedKeys(res.data)
      })
    },
    //保存角色菜单数据
    saveRoleMenu(){
      //得到半选择的菜单ID
      let halfCheckKeys=this.$refs.menuTree.getHalfCheckedKeys();
      //得到所有选中的菜单id
      let checkKeys = this.$refs.menuTree.getCheckedKeys();
      // 合并选中的数组和半选中的数组
      checkKeys.push(...halfCheckKeys)
      //保存数据
      console.log(this.activeId)
      console.log(checkKeys)
      sysRoleApi.saveRoleMenu(this.activeId,checkKeys).then(res=>{
        this.$message.success("分配菜单权限成功");
        this.$emit("after");
        this.$emit("close");
      });
    }
  }
}
</script>

<style scoped>

</style>
