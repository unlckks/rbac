<template>
  <div class="app-container">
    <!--查询条件开始-->
    <el-card>
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
        <el-form-item prop="menuName">
          <el-input
            v-model="queryParams.menuName"
            placeholder="请输入菜单名称"
            clearable
          />
        </el-form-item>
        <el-form-item prop="status">
          <el-select v-model="queryParams.status" placeholder="菜单状态" clearable>
            <el-option v-for="dict in statusOptions" :value="dict.dictValue" :label="dict.dictLabel"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
          <el-button icon="el-icon-refresh" @click="queryParams={}">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <!--查询条件结束-->

    <!--数据表格开始-->
    <el-card style="margin-top: 3px">
      <div style="margin-top: 3px">
        <el-button
          type="primary"
          icon="el-icon-plus"
          @click="toAdd"
        >新增
        </el-button>
        <el-button
          type="info"
          icon="el-icon-sort"
          @click="toggleExpandAll">展开/折叠
        </el-button>
      </div>
      <el-table style="margin-top: 3px"
                v-loading="loading"
                border
                v-if="refreshTable"
                :data="menuList"
                row-key="menuId"
                :default-expand-all="isExpandAll"
                :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      >
        <el-table-column prop="menuName" label="菜单名称" :show-overflow-tooltip="true" width="200px"></el-table-column>
        <el-table-column prop="icon" align="center" label="图标">
          <template slot-scope="{row}">
            <div>
              <svg-icon
                slot="prefix"
                :icon-class="row.icon"
                class="el-input__icon"
                style="height: 32px;width: 16px;"
              />
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="orderNum" align="center" label="排序"></el-table-column>
        <el-table-column prop="perms" align="center" label="权限标识" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column prop="component" align="center" label="组件路径" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="path" align="center" label="路由地址" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="status" align="center" label="状态">
          <template slot-scope="{row}">
            <dict-tag :value="row.status" :data-list="statusOptions"></dict-tag>
          </template>
        </el-table-column>
        <el-table-column label="类型" align="center" prop="menuType">
          <template slot-scope="{row}">
            <div>
              <el-tag :type="row.menuType==0?'success':row.menuType==1?'info':'warning'">
                {{ row.menuType == 0 ? '目录' : row.menuType == 1 ? '菜单' : '权限' }}
              </el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" fixed="right">
          <template slot-scope="{row}">
            <el-dropdown class="handle-button">
              <span class="el-dropdown-link">
                <el-link icon="el-icon-s-operation" type="primary">操作<i class="el-icon-arrow-down el-icon--right"/>
                </el-link>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="toUpdate(row)"
                  >修改
                  </el-button>
                </el-dropdown-item>
                <el-dropdown-item v-if="row.menuType!=2">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-plus"
                    @click="toAdd(row)"
                  >新增
                  </el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    @click="toDelete(row)"
                  >删除
                  </el-button>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!--数据表格结束-->
    <!--  添加菜单弹出层的开始-->
    <el-dialog title="添加菜单" center width="60%" :visible.sync="addDialog"  v-if="addDialog">
      <sys-menu-add @after="getAllMenu" :active-obj="activeObj" @close="closeDialog"/>
    </el-dialog>
    <!--  添加菜单弹出层的结束-->

    <!--  修改菜单弹出层的开始-->
    <el-dialog title="修改菜单" center width="60%" :visible.sync="updateDialog"  v-if="updateDialog">
      <sys-menu-update @after="getAllMenu" :active-obj="activeObj" @close="closeDialog"/>
    </el-dialog>
    <!--  修改菜单弹出层的结束-->
  </div>

</template>


<script>

import sysDictDataApi from "@/api/system/sysDictData"
import sysMenuApi from "@/api/system/sysMenu"
import {handleTree} from "@/utils/leige";
import sysMenuAdd from "./sys-menu-add"
import sysMenuUpdate from "./sys-menu-update"
import {title} from "@/settings";
import sysDeptApi from "@/api/system/sysDept";

export default {
  name: "sys-menu-list",
  //注册组件
  components:{
    sysMenuAdd,
    sysMenuUpdate,
  },
  data() {

    return {
      //进行查询条件
      queryParams: {},
      //状态数据字典
      statusOptions: [],
      //加载层
      loading: false,
      //重新渲染
      refreshTable: true,
      //菜单数据
      menuList: [],
      //进行展开层
      isExpandAll : false,
      //是否打开添加弹出层
      addDialog :false ,
      //是否打开修改弹出层
      updateDialog :false ,
      //弹出层的标题
      title :"",
      //添加修改时的对象
      activeObj : undefined,

    }
  },
  created() {
    //进行查询
    sysDictDataApi.getDictDataByDictType("sys_status_type").then(res => {
      //将数据放入再状态数据字典中
      this.statusOptions = res.data;
    })
    //查询菜单数据
    this.getAllMenu()
  },
  methods: {
    getAllMenu() {
      //结构错误，id填错
      sysMenuApi.listMenu(this.queryParams).then(res=>{
        this.menuList=handleTree(res.data,"menuId")
      })
    },
    //进行搜索的方法
    search() {
      this.getAllMenu();

    },
    //打开添加
    toAdd(row) {
      this.activeObj =undefined ;
      //有值
      if(row.menuId){
     this.activeObj =row ;
      }
     this.title="添加菜单";
      this.addDialog =true ;
    },
    //展开和收起
    toggleExpandAll(){
      // 销毁表格
      this.refreshTable = false
      // 标记展开展开状态为反状态
      this.isExpandAll = !this.isExpandAll
      this.$nextTick(() => {
        this.refreshTable = true
      })
    },

    //打开修改的弹出层
    toUpdate(row) {
      this.activeObj =row;
      this.title=`修改【${row.menuName}】菜单权限数据`;
      this.updateDialog =true ;
    },
    //删除
    toDelete(row) {
      this.$confirm('此操作会删除【' + row.menuName + '】该菜单，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        sysMenuApi.deleteById(row.menuId).then(res => {
          this.$message.success(res.msg)
          // 右边表格的数据重新查询
          this.getAllMenu()
        })
      }).catch(() => {
      })
    },
    //关闭所有的弹出层
    closeDialog(){
      this.addDialog = false ;
      this.updateDialog = false ;
    }
  }
}
</script>

<style scoped>

</style>

