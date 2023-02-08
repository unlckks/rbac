<template>
  <div>
    <div>
      <el-tree
        ref="authTree"
        :data="menuTree"
        show-checkbox
        node-key="menuId"
        default-expand-all
        :props="defaultProps">
      </el-tree>
    </div>
    <div style="text-align: center">
      <el-button type="primary" @click="saveRoleMenu">保存角色和菜单</el-button>
    </div>
  </div>
</template>

<script>
import sysRoleApi from '@/api/system/sysRole'
import sysMenuApi from "@/api/system/sysMenu"
import {handleTree} from "@/utils/leige";
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
        //根据角色ID查询当前角色拥有的权限菜单
        this.getCurrentRoleHasMenuIds(newVal);
      }
    }
  },
  name: "sys-role-update" ,
  data() {
    return {
      menuTree: [],
      defaultProps:{
        children: 'children',
        label: 'menuName'
      }
    }
  },
  created() {
    //查询所有菜单权限
    this.getMenuTreeList();
  },
  methods: {
    //查询所有菜单权限数据
    getMenuTreeList(){
      sysMenuApi.listMenu().then(res=>{
        this.menuTree=handleTree(res.data,"menuId");
        console.log(this.menuTree)
      })
    },
    //根据角色ID查询当前角色拥有的权限菜单
    getCurrentRoleHasMenuIds(roleId){
      sysRoleApi.getCurrentRoleHasMenuIdsByRoleId(roleId).then(res=>{
        console.log(res.data);
        // 手动设置选中
        this.$refs['authTree'].setCheckedKeys(res.data)
      })
    },
    //保存角色和菜单关系
    saveRoleMenu(){
      //得到所有选中的key
      const checkKeys = this.$refs.authTree.getCheckedKeys()
      //得到所有半选的key
      const halfCheckKeys = this.$refs.authTree.getHalfCheckedKeys()
      // 合并选中的数组和半选中的数组
      checkKeys.push(...halfCheckKeys)
      sysRoleApi.saveRoleMenu(this.activeId,checkKeys).then(res => {
        this.$message.success(res.msg)
      })
    }
  }
}
</script>

<style scoped>

</style>
