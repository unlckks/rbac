<template>
  <div class="app-container">
    <!-- 搜索表单开始 -->
    <el-card class="search-form">
      <el-form :inline="true" size="small" :model="page.params">
        <el-form-item>
          <el-input v-model="page.params.roleName" placeholder="角色名"/>
        </el-form-item>
        <el-form-item prop="status">
          <el-select v-model="page.params.status" placeholder="请选择状态" clearable>
            <el-option v-for="dict in statusOptions" :key="dict.dictValue" :value="dict.dictValue" :label="dict.dictLabel"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="small" @click="search">查询</el-button>
          <el-button type="warning" icon="el-icon-refresh" size="small" @click="page.params = {}">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <!-- 搜索表单结束 -->
    <el-card style="margin-top: 3px">
      <!-- 菜单组开始 -->
      <el-button size="small" type="primary" icon="el-icon-plus" @click="toAdd">添加</el-button>
      <!-- 菜单组结束-->
      <!-- 数据表格开始 -->
      <div style="margin-top: 3px">
        <el-table
          v-loading="loading"
          :data="dataPage.list"
          border
          stripe
          style="width: 100%"
        >
          <el-table-column  prop="roleId" label="角色编号" align="center"  />
          <el-table-column prop="roleName" label="角色名" align="center" />
          <el-table-column prop="roleSort" align="center" label="排序"/>
          <el-table-column prop="status" align="center"  label="状态">
            <template slot-scope="{row}">
              <dict-tag :value="row.status" :data-list="statusOptions"></dict-tag>
            </template>
          </el-table-column>
          <el-table-column align="center" label="操作" fixed="right">
            <template slot-scope="{row}">
              <el-dropdown class="handle-button">
              <span class="el-dropdown-link">
                <el-link icon="el-icon-s-operation" type="primary">操作<i class="el-icon-arrow-down el-icon--right"/>
                </el-link>
              </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item >
                    <el-button type="text" icon="el-icon-edit" @click="toUpdate(row)">修改</el-button>
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button type="text" @click="toDelete(row)" icon="el-icon-delete">删除</el-button>
                  </el-dropdown-item>
                  <el-dropdown-item >
                    <el-button type="text" @click="toSelectMenu(row)" icon="el-icon-setting">分配权限</el-button>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          class="pagination"
          :current-page="page.pageNumber"
          :page-sizes="[10,20,30,50]"
          :page-size="page.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="dataPage.totalCount"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
      <!-- 数据表格结束 -->
    </el-card>
    <!--添加角色弹出层开始-->
    <el-dialog :title="title" center width="50%" :visible.sync="addDialog"  v-if="addDialog">
      <sys-role-add @after="getByPage" :status-options="statusOptions" @close="closeDialog"/>
    </el-dialog>
    <!--添加角色弹出层结束-->
    <!--修改角色弹出层开始-->
    <el-dialog :title="title" center width="50%" :visible.sync="updateDialog"  v-if="updateDialog">
      <sys-role-update @after="getByPage" :status-options="statusOptions" :activeId="activeId" @close="closeDialog"/>
    </el-dialog>
    <!--修改角色弹出层结束-->

    <!--角色分配权限弹出层开始-->
    <el-dialog :title="title" center width="30%"  :visible.sync="selectRoleDialog"  v-if="selectRoleDialog">
      <sys-role-select-menu  :activeId="activeId" @close="closeDialog"/>
    </el-dialog>
    <!--角色分配权限弹出层结束-->
  </div>
</template>

<script>
import sysRoleApi from  "@/api/system/sysRole"
import SysRoleAdd from "./sys-role-add"
import SysRoleUpdate from "./sys-role-update"
import SysRoleSelectMenu from "./sys-role-select-menu"
export default {
  name: "sys-role-list",
  //注册组件
  components:{
    SysRoleAdd,
    SysRoleUpdate,
    SysRoleSelectMenu
  },
  data(){
    return{
      page:{
        pageNumber:1,
        pageSize:10,
        params:{
          roleName:undefined,
          status:undefined
        }
      },
      //状态数据
      statusOptions:[],
      //加载层
      loading:false,
      //分页的数据
      dataPage:{
        list:[],
        //总条数
        totalCount:0
      },
      //弹出层的标题
      title:"",
      //是否打开添加的弹出层
      addDialog:false,
      //是否打开修改的弹出层
      updateDialog:false,
      //当前要修改或者分配权限的角色ID
      activeId:undefined,
      //是否打开分配权限的弹出层
      selectRoleDialog:false
    }
  },
  created() {
    this.getDictDataByDictType("sys_status_type").then(res=>{
      this.statusOptions=res.data;
    })
    //查询角色数据
    this.getByPage()
  },
  methods:{
    //页码变化的事件
    handleCurrentChange(val){
      this.page.pageNumber=val;
      this.getByPage();
    },
    //每页条数变化的事件
    handleSizeChange(val){
      this.page.pageSize=val;
      this.getByPage();
    },
    //分页查询角色
    getByPage(){
      this.loading = true
      sysRoleApi.getByPage(this.page).then(res => {
        this.dataPage.list = res.data.list
        this.dataPage.totalCount = res.data.totalCount
        this.loading = false
      })
    },
    //搜索
    search(){
      this.getByPage()
    },

    //打开添加角色的页面
    toAdd(){
      this.addDialog=true;
      this.title="添加角色";
    },
    //打开修改的弹出层
    toUpdate(row){
      this.updateDialog=true;
      this.title=`修改【${row.roleName}】的角色数据`;
      this.activeId=row.roleId;
    },
    //删除角色
    toDelete(row){
      this.$confirm('此操作会删除【' + row.roleName + '】该角色数据，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        sysRoleApi.deleteById(row.roleId).then(res => {
          this.$message.success(res.msg)
          // 右边表格的数据重新查询
          this.getByPage()
        })
      }).catch(() => {
      })
    },
    //打开分配权限菜单的弹出层
    toSelectMenu(row){
      this.selectRoleDialog=true;
      this.title=`给【${row.roleName}】角色分配权限`;
      this.activeId=row.roleId;
    },
    //关闭所有弹出层
    closeDialog(){
      this.addDialog=false;
      this.updateDialog=false;
      this.selectRoleDialog=false;
    }
  }
}
</script>

<style scoped>

</style>
