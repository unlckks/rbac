<template>
  <div class="app-container">
    <el-row :gutter="5">
      <el-col :span="4" :xs="24">
        <el-card>
          <sys-user-left-tree @reloadTable="reloadTable"></sys-user-left-tree>
        </el-card>
      </el-col>
      <el-col :span="20" :xs="24">
        <el-card>
          <!--查询条件开始-->
          <el-card class="search-form">
            <el-form :inline="true" :model="page.params" size="small">
              <el-form-item>
                <el-input v-model="page.params.username" clearable placeholder="请输入账号"/>
              </el-form-item>
              <el-form-item>
                <el-input v-model="page.params.nickName" clearable placeholder="请输入昵称"/>
              </el-form-item>
              <el-form-item>
                <el-select v-model="page.params.status" clearable placeholder="请选择状态">
                  <el-option v-for="dict in statusOptions" :key="dict.dictValue" :value="dict.dictValue"
                             :label="dict.dictLabel"/>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-select v-model="page.params.sex" clearable placeholder="请选择性别">
                  <el-option v-for="dict in sexOptions" :key="dict.dictValue" :value="dict.dictValue"
                             :label="dict.dictLabel"/>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
                <el-button type="warning" icon="el-icon-refresh" @click="page.params = {}">重置</el-button>
              </el-form-item>
            </el-form>
          </el-card>
          <!--查询条件结束-->

          <el-card style="margin-top: 3px">
            <!-- 操作按钮组 -->
            <el-button type="primary" size="small" icon="el-icon-plus" @click="toAdd">
              添加
            </el-button>
            <!-- 操作按钮组结束 -->
            <!-- 数据表格开始 -->
            <el-table border v-loading="loading" :data="dataPage.list" stripe style="width: 100%;margin-top: 3px">
              <!--            <el-table-column align="center" prop="userId" label="编号"/>-->
              <el-table-column align="center" prop="userName" label="账号"/>
              <el-table-column align="center" prop="nickName" label="昵称"/>
              <el-table-column align="center" prop="email" label="邮箱" width="180px"/>
              <el-table-column align="center" prop="phone" label="电话" width="150px"/>
              <el-table-column align="center" prop="sex" label="性别">
                <template slot-scope="{row}">
                  <dict-tag :value="row.sex" :data-list="sexOptions"></dict-tag>
                </template>
              </el-table-column>
              <el-table-column align="center" prop="deptId" label="所在部门" width="150px">
                <template slot-scope="{row}">
                  <div v-for="dept in deptList" v-if="row.deptId==dept.deptId">{{dept.deptName}}</div>
                </template>
              </el-table-column>
              <el-table-column align="center" prop="status" label="状态">
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
                      <el-dropdown-item>
                        <el-button type="text" @click="toInfo(row)" icon="el-icon-document">详情</el-button>
                      </el-dropdown-item>
                      <el-dropdown-item>
                        <el-button type="text" @click="toUpdate(row)" v-if="row.userType==1" icon="el-icon-edit">修改</el-button>
                      </el-dropdown-item>
                      <el-dropdown-item>
                        <el-button type="text" @click="deleteById(row)" v-if="row.userType==1" icon="el-icon-delete">删除</el-button>
                      </el-dropdown-item>
                      <el-dropdown-item>
                        <el-button type="text" @click="resetPassword(row)" v-if="row.userType==1" icon="el-icon-refresh-left">重置密码</el-button>
                      </el-dropdown-item>
                      <el-dropdown-item>
                        <el-button type="text" @click="toSelectRole(row)" v-if="row.userType==1" icon="el-icon-edit">分配角色</el-button>
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                </template>
              </el-table-column>
            </el-table>
            <!-- 分页组件开始 -->
            <div style="margin-top: 3px">
              <el-pagination
                :current-page="page.pageNumber"
                :page-sizes="[10, 20, 30, 50]"
                :page-size="10"
                background
                layout="total, sizes, prev, pager, next, jumper"
                :total="dataPage.totalCount"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
              />
            </div>
            <!-- 分页组件结束 -->
          </el-card>
        </el-card>
      </el-col>
    </el-row>


    <!--详情的弹出层开始-->
    <el-dialog
      :title="title"
       center="true"
      :visible.sync="infoDialog"
      v-if="infoDialog"
      width="60%"
    >
      <sys-user-info  :active-obj="activeObj" :status-options="statusOptions" :sex-options="sexOptions"/>
    </el-dialog>
    <!--详情的弹出层结束-->

    <!--添加的弹出层开始-->
    <el-dialog
      :title="title"
      center="true"
      :visible.sync="addDialog"
      v-if="addDialog"
      width="60%"
    >
      <sys-user-add @close="closeDialog" @after="getByPage"  :dept-options="deptList" :status-options="statusOptions" :sex-options="sexOptions"/>
    </el-dialog>
    <!--添加的弹出层结束-->

    <!--修改的弹出层开始-->
    <el-dialog
      :title="title"
      center="true"
      :visible.sync="updateDialog"
      v-if="updateDialog"
      width="60%"
    >
      <sys-user-update @close="closeDialog" @after="getByPage" :active-obj="activeObj"  :dept-options="deptList" :status-options="statusOptions" :sex-options="sexOptions"/>
    </el-dialog>
    <!--修改的弹出层结束-->


    <!--分配角色的弹出层开始-->
    <el-dialog
      :title="title"
      center="true"
      :visible.sync="selectRoleDialog"
      v-if="selectRoleDialog"
      width="60%"
    >
      <sys-user-select-role @close="closeDialog"  :active-obj="activeObj" />
    </el-dialog>
    <!--分配角色的弹出层结束-->

  </div>
</template>

<script>
import SysUserLeftTree from "./sys-user-left-tree"
import sysUserApi from "@/api/system/sysUser"
import sysDeptApi from "@/api/system/sysDept"
import SysUserInfo from "./sys-user-info"
import SysUserAdd from "./sys-user-add"
import SysUserUpdate from "./sys-user-update"
import SysUserSelectRole from "./sys-user-select-role"


export default {
  name: "sys-user-list",
  components: {
    SysUserLeftTree,SysUserInfo,SysUserAdd,SysUserUpdate,SysUserSelectRole
  },
  data() {
    return {
      //加载层
      loading:false,
      //查询数据
      page: {
        pageNumber: 1,
        pageSize: 10,
        params: {}
      },
      //分页数据
      dataPage: {
        list: [],
        totalCount: 0
      },
      //声明字典数据
      statusOptions: [],
      sexOptions: [],
      //部门数据
      deptList:[],
      //弹出层标题
      title:"",
      //是否打开添加弹出层
      addDialog:false,
      //是否打开修改弹出层
      updateDialog:false,
      //是否打开分配角色弹出层
      selectRoleDialog:false,
      //是否打开详情弹出层
      infoDialog:false,
      //当前操作的对象
      activeObj:undefined,
    }
  },
  created() {
    this.getDictDataByDictType("sys_status_type").then(res => {
      this.statusOptions = res.data;
    })
    this.getDictDataByDictType("sys_user_sex").then(res => {
      this.sexOptions = res.data;
    })
    this.getByPage();
    //查询所有部门
    this.getDeptList()
  },
  methods: {
    //查询所有部门
    getDeptList(){
      sysDeptApi.listDept({status:0}).then(res=>{
        this.deptList=res.data;
      })
    },
    // 每页条数变更时触发
    handleSizeChange(val) {
      this.page.pageSize = val
      this.getByPage()
    },
    // 当前页变更时触发
    handleCurrentChange(val) {
      this.page.pageNumber = val
      this.getByPage()
    },
    //查询用户数据
    getByPage(){
      this.loading=true;
      sysUserApi.getByPage(this.page).then(res=>{
        this.dataPage.list=res.data.list;
        this.dataPage.totalCount=res.data.totalCount;
        this.loading=false;
      })
    },
    //查询
    search() {
      this.getByPage()
    },
    //打开添加用户的弹出层
    toAdd() {
      this.addDialog=true;
      this.title="添加用户";
    },
    //打开详情
    toInfo(row){
      this.title=`查看【${row.nickName}】的详情数据`;
      this.infoDialog=true;
      this.activeObj=row;
    },
    //打开修改的弹出层
    toUpdate(row){
      this.updateDialog=true;
      this.title=`修改【${row.nickName}】的用户数据`;
      this.activeObj=row;
    },
    //删除用户
    deleteById(row){
      this.$confirm('此操作会删除【' + row.nickName + '】该用户数据，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        sysUserApi.deleteById(row.userId).then(res => {
          this.$message.success(res.msg)
          // 右边表格的数据重新查询
          this.getByPage()
        })
      }).catch(() => {
      })
    },
    //重置密码
    resetPassword(row){
      this.$confirm('此操作会重置【' + row.nickName + '】该用户密码，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        sysUserApi.resetPwd(row.userId).then(res => {
          this.$message.success("重置密码成功")
        })
      }).catch(() => {
      })
    },
    //给左边的部门树调用的方法
    reloadTable(deptId) {
      this.page.params.deptId=deptId;
      this.getByPage();
    },
    //打开分配角色的弹出层
    toSelectRole(row){
      this.title=`给【${row.nickName}】的分配角色`;
      this.selectRoleDialog=true;
      this.activeObj=row;
    },
    //关闭弹出层
    closeDialog(){
      this.addDialog=false;
      this.updateDialog=false;
      this.selectRoleDialog=false;
      this.infoDialog=false;
    }
  }
}
</script>

<style scoped>

</style>
