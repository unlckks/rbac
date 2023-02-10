<template>
  <div>
    <el-input
      placeholder="输入关键字进行过滤"
      clearable
      v-model="deptName">
    </el-input>

    <el-tree
      style="margin-top: 5px"
      class="filter-tree"
      :data="deptList"
      :props="{children: 'children',label: 'deptName'}"
      default-expand-all
      :filter-node-method="filterNode"
      @node-click="handleNodeClick"
      ref="deptTree">
    </el-tree>
  </div>
</template>

<script>
import sysDeptApi from "@/api/system/sysDept"
import {handleTree} from "@/utils/leige";
export default {
  name: "sys-user-left-tree.vue",
  watch: {
    deptName(val) {
      this.$refs.deptTree.filter(val);
    }
  },
  data(){
    return{
      //过滤条件
      deptName:undefined,
      //树的数据
      deptList:[]
    }
  },
  created() {
    sysDeptApi.listDept({status:0}).then(res=>{
      this.deptList=handleTree(res.data,"deptId");
    })
  },
  methods:{
    filterNode(value, data) {
      if (!value) return true;
      return data.deptName.indexOf(value) !== -1;
    },
    //节点被点击事件
    handleNodeClick(node){
      let deptId=node.deptId;
      this.$emit("reloadTable",deptId)
    }
  }
}
</script>

<style scoped>

</style>
