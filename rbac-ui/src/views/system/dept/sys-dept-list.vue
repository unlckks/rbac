<template>
  <div class="app-container">
    <!--查询条件开始-->
    <el-card>
      <el-form ref="queryForm" :model="queryParams" size="small" :inline="true">
        <el-form-item label="部门名称" prop="deptName">
          <el-input
            v-model="queryParams.deptName"
            placeholder="请输入部门名称"
            clearable
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="部门状态" clearable>
            <el-option v-for="dict in statusOptions" :value="dict.dictValue" :label="dict.dictLabel" />
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
          @click="toggleExpandAll"
        >
          {{ isExpandAll?"折叠":"展开" }}
        </el-button>
      </div>
      <el-table
        v-if="refreshTable"
        v-loading="loading"
        style="margin-top: 3px"
        border
        :data="deptList"
        row-key="deptId"
        :default-expand-all="isExpandAll"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      >
        <el-table-column prop="deptName" label="部门名称" :show-overflow-tooltip="true" width="200px" />
        <el-table-column prop="orderNum" align="center" label="排序" />
        <el-table-column prop="leader" align="center" label="负责人" :show-overflow-tooltip="true" />
        <el-table-column prop="phone" align="center" label="负责人电话" :show-overflow-tooltip="true" />
        <el-table-column prop="email" align="center" label="邮箱" :show-overflow-tooltip="true" />
        <el-table-column prop="status" align="center" label="状态">
          <template slot-scope="{row}">
            <dict-tag :value="row.status" :data-list="statusOptions" />
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" fixed="right">
          <template slot-scope="{row}">
            <el-dropdown class="handle-button">
              <span class="el-dropdown-link">
                <el-link icon="el-icon-s-operation" type="primary">操作<i class="el-icon-arrow-down el-icon--right" />
                </el-link>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="toUpdate(row)"
                  >修改</el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-plus"
                    @click="toAdd(row)"
                  >新增</el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    @click="toDelete(row)"
                  >删除</el-button>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!--添加部门的弹出层开始-->
    <el-dialog
      v-if="addDialog"
      :title="title"
      center
      width="60%"
      :visible.sync="addDialog"
      @before-close="closeDialog"
    >
      <sys-dept-add :active-obj="activeObj" @after="getAllDept" @close="closeDialog" />
    </el-dialog>
    <!--添加部门的弹出层结束-->

    <!--修改部门的弹出层开始-->
    <el-dialog
      v-if="updateDialog"
      :title="title"
      center
      width="60%"
      :visible.sync="updateDialog"
      @before-close="closeDialog"
    >
      <sys-dept-update :active-obj="activeObj" @after="getAllDept" @close="closeDialog" />
    </el-dialog>
    <!--修改部门的弹出层结束-->

  </div>
</template>

<script>
import { handleTree } from '@/utils/leige'
import sysDeptApi from '@/api/system/sysDept'
import sysDictDataApi from '@/api/system/sysDictData'
import SysDeptAdd from './sys-dept-add'
import SysDeptUpdate from './sys-dept-update'
export default {
  name: 'SysDeptList',
  // 私有组件注册
  components: {
    SysDeptAdd,
    SysDeptUpdate
  },
  data() {
    return {
      queryParams: {
        deptName: undefined,
        status: undefined
      },
      // 存放状态数据
      statusOptions: [],
      // 是否显示加载条
      loading: false,
      // 是否重新渲染表格【销毁】
      refreshTable: true,
      // 部门数据
      deptList: [],
      // 展开状态
      isExpandAll: true,
      // 是否打开添加部门的弹出层
      addDialog: false,
      // 是否打开修改部门的弹出层
      updateDialog: false,
      // 弹出层的标题
      title: '',
      // 当前操作的部门
      activeObj: undefined
    }
  },
  created() {
    sysDictDataApi.getDictDataByDictType('sys_status_type').then(res => {
      this.statusOptions = res.data
    })
    this.getAllDept()
  },
  methods: {
    // 查询所有部门
    getAllDept() {
      this.loading = true
      sysDeptApi.listDept(this.queryParams).then(res => {
        const data = res.data
        this.deptList = handleTree(data, 'deptId')
        this.loading = false
      })
    },
    search() {
      this.getAllDept()
    },
    // 展开和折叠部门列表的数据
    toggleExpandAll() {
      // 销毁表格
      this.refreshTable = false
      // 标记展开展开状态为反状态
      this.isExpandAll = !this.isExpandAll
      this.$nextTick(() => {
        this.refreshTable = true
      })
    },
    // 打开添加部门的弹出层
    toAdd(row) {
      this.activeObj = undefined
      this.title = '添加部门'
      console.log(row)
      if (row.deptId != undefined) {
        this.title = `在【${row.deptName}】下面添加部门`
        this.activeObj = row
      }
      // 打开弹出层
      this.addDialog = true
    },
    // 打开修改的弹出层
    toUpdate(row) {
      this.activeObj = row
      this.title = `修改【${row.deptName}】部门`
      // 打开弹出层
      this.updateDialog = true
    },
    // 删除部门
    toDelete(row) {
      this.$confirm('此操作会删除【' + row.deptName + '】该部门，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        sysDeptApi.deleteById(row.deptId).then(res => {
          this.$message.success(res.msg)
          // 右边表格的数据重新查询
          this.getAllDept()
        })
      }).catch(() => {
      })
    },
    // 关闭所有弹出层
    closeDialog() {
      this.addDialog = false
      this.updateDialog = false
    }
  }
}
</script>

<style scoped>

</style>
