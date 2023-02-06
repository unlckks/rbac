<template>
  <div class="app-container">
    <el-row>
      <!--字典类型管理开始-->
      <el-col :span="12">
        <el-card style="margin-top: 3px">
          <!-- 搜索表单开始 -->
          <el-form :inline="true" size="small" :model="page.params">
            <el-form-item>
              <el-input v-model="page.params.dictName" placeholder="字典名称" />
            </el-form-item>
            <el-form-item>
              <el-select v-model="page.params.status" clearable placeholder="请选择状态">
                <el-option
                  v-for="item in statusOptions"
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="small" @click="search">查询</el-button>
              <el-button type="warning" icon="el-icon-refresh" size="small" @click="page.params = {}">重置</el-button>
              <!--工具条开始-->
              <el-button size="small" type="success" icon="el-icon-plus" @click="toAddDictType">添加字典类型</el-button>
              <!--工具条结束-->
            </el-form-item>
          </el-form>
          <!-- 搜索表单结束 -->
          <!--数据表格开始-->
          <div style="margin-top: 3px">
            <el-table
              v-loading="loading"
              :data="dataPage.list"
              border
              stripe
              style="width: 100%"
              @row-click="dictTypeRowClick"
            >
              <el-table-column prop="dictId" label="类型编号" align="center" />
              <el-table-column prop="dictName" label="类型名称" align="center" />
              <el-table-column prop="dictType" align="center" label="字典类型" />
              <el-table-column prop="remark" align="center" label="字典备注" />
              <el-table-column prop="status" align="center" label="状态">
                <template slot-scope="{row}">
                  <dict-tag :data-list=" statusOptions" :value="row.status" />
                </template>
              </el-table-column>
              <el-table-column align="center" label="操作" fixed="right">
                <template slot-scope="{row}">
                  <el-dropdown class="handle-button">
                    <span class="el-dropdown-link">
                      <el-link icon="el-icon-s-operation" type="primary">操作<i class="el-icon-arrow-down el-icon--right" />
                      </el-link>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item>
                        <el-button type="text" icon="el-icon-edit" @click="toUpdateDictType(row)">修改</el-button>
                      </el-dropdown-item>
                      <el-dropdown-item>
                        <el-button type="text" icon="el-icon-delete" @click="toDeleteDictType(row)">删除</el-button>
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
          <!--数据表格结束-->
        </el-card>
      </el-col>
      <!--字典类型管理结束-->
      <!--字典数据管理开始-->
      <el-col :span="12">
        <el-card style="margin-top: 3px">
          <!--工具条开始-->
          <el-form>
            <el-form-item>
              <!--工具条开始-->
              <el-button size="small" :disabled="currentDictType==undefined" type="success" icon="el-icon-plus" @click="toAddDictData">添加字典数据</el-button>
              <!--工具条结束-->
            </el-form-item>
          </el-form>
          <!--字典数据管理表格开始-->
          <el-table
            v-loading="dataLoading"
            :data="dictDataList"
            border
            stripe
            style="width: 100%"
          >
            <el-table-column label="字典编码" align="center" prop="dictCode" />
            <el-table-column label="字典标签" align="center" prop="dictLabel" />
            <el-table-column label="字典键值" align="center" prop="dictValue" />
            <el-table-column label="字典排序" align="center" prop="dictSort" />
            <el-table-column label="字典类型" align="center" prop="dictType" />
            <el-table-column label="状态" align="center" prop="status">
              <template slot-scope="{row}">
                <dict-tag :data-list=" statusOptions" :value="row.status" />
              </template>
            </el-table-column>
            <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" />
            <el-table-column align="center" label="操作" fixed="right">
              <template slot-scope="{row}">
                <el-dropdown class="handle-button">
                  <span class="el-dropdown-link">
                    <el-link icon="el-icon-s-operation" type="primary">操作<i class="el-icon-arrow-down el-icon--right" />
                    </el-link>
                  </span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>
                      <el-button type="text" icon="el-icon-edit" @click="toUpdateDictData(row)">修改</el-button>
                    </el-dropdown-item>
                    <el-dropdown-item>
                      <el-button type="text" icon="el-icon-delete" @click="toDeleteDictData(row)">删除</el-button>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </template>
            </el-table-column>
          </el-table>
          <!--字典数据管理表格结束-->
        </el-card>
      </el-col>
      <!--字典数据管理结束-->
    </el-row>

    <!--字典类型添加的弹出层开始-->
    <el-dialog v-if="addDictTypeDialog" :title="title" center width="50%" :visible.sync="addDictTypeDialog">
      <sys-dict-type-add @after="getDictTypeByPage" @close="closeDialog" />
    </el-dialog>
    <!--字典类型添加的弹出层结束-->

    <!--字典类型修改的弹出层开始-->
    <el-dialog v-if="updateDictTypeDialog" :title="title" center width="50%" :visible.sync="updateDictTypeDialog">
      <sys-dict-type-update :active-id="dictTypeActiveId" @after="getDictTypeByPage" @close="closeDialog" />
    </el-dialog>
    <!--字典类型修改的弹出层结束-->

    <!--字典数据添加的弹出层开始-->
    <el-dialog v-if="addDictDataDialog" :title="title" center width="50%" :visible.sync="addDictDataDialog">
      <sys-dict-data-add :active-type="currentDictType" @after="queryDictDataByDictType" @close="closeDialog" />
    </el-dialog>
    <!--字典数据添加的弹出层结束-->

    <!--字典数据添加的弹出层开始-->
    <el-dialog v-if="updateDictDataDialog" :title="title" center width="50%" :visible.sync="updateDictDataDialog">
      <sys-dict-data-update :active-id="dictDataActiveId" @after="queryDictDataByDictType" @close="closeDialog" />
    </el-dialog>
    <!--字典数据添加的弹出层结束-->

  </div>
</template>

<script>
import sysDictTypeApi from '@/api/system/sysDictType'
import sysDictDataApi from '@/api/system/sysDictData'
import SysDictTypeAdd from './sys-dict-type-add'
import SysDictTypeUpdate from './sys-dict-type-update'
import SysDictDataAdd from './sys-dict-data-add'
import SysDictDataUpdate from './sys-dict-data-update'
export default {
  name: 'SysDictTypeList',
  components: {
    SysDictTypeAdd,
    SysDictTypeUpdate,
    SysDictDataAdd,
    SysDictDataUpdate
  },
  data() {
    return {
      // 字典类型加载条
      loading: false,
      // 字典数据的加载条
      dataLoading: false,
      // 查询条件
      page: {
        pageNumber: 1,
        pageSize: 10,
        params: {}
      },
      // 字典类型分页相关数据
      dataPage: {
        list: [],
        totalCount: 0// 总条数
      },
      // 字典数据
      dictDataList: [],
      // 弹出层标题
      title: '',
      // 是否显示字典类型添加的弹出层
      addDictTypeDialog: false,
      // 是否显示字典类型修改的弹出层
      updateDictTypeDialog: false,
      // 当前修改的字典类型的ID
      dictTypeActiveId: undefined,
      // 存放字典数据当前点击行的数据
      currentDictType: undefined,
      // 是否显示字典数据添加的弹出层
      addDictDataDialog: false,
      // 是否显示字典数据修改的弹出层
      updateDictDataDialog: false,
      // 当前修改的字典数据的ID
      dictDataActiveId: undefined,
      // 声明属性存放字典的数据
      statusOptions: []

    }
  },
  created() {
    // 查询字典类型
    this.getDictTypeByPage()
    // 加载字典类型加载数据状态的数据
    this.getDictDataByDictType('sys_status_type')
  },
  methods: {
    // 根据字典类型加载数据状态的字典数据
    getDictDataByDictType() {
      // 掉后台传值
      sysDictDataApi.getDictDataByDictType('sys_status_type').then(res => {
        this.statusOptions = res.data
      })
    },
    // 每页条数切换触发的方法
    handleSizeChange(val) {
      this.page.pageSize = val
      this.getDictTypeByPage()
    },
    // 页码变化触发的方法
    handleCurrentChange(val) {
      this.page.pageNumber = val
      this.getDictTypeByPage()
    },
    // 分页查询字典类型
    getDictTypeByPage() {
      this.loading = true
      sysDictTypeApi.getByPage(this.page).then(res => {
        this.dataPage.list = res.data.list
        this.dataPage.totalCount = res.data.totalCount
        this.loading = false
      })
    },
    // 搜索的方法
    search() {
      this.getDictTypeByPage()
    },
    // 打开添加字典类型的弹出层
    toAddDictType() {
      this.title = '添加字典类型'
      this.addDictTypeDialog = true
    },
    // 打开修改字典类型的弹出层
    toUpdateDictType(row) {
      this.updateDictTypeDialog = true
      this.title = `修改【${row.dictName}】的字典类型`
      this.dictTypeActiveId = row.dictId
    },
    // 删除字典数据
    toDeleteDictType(row) {
      this.$confirm('此操作会删除【' + row.dictName + '】该字典类型,它的类型数据会全部删除，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        sysDictTypeApi.deleteById(row.dictId).then(res => {
          this.$message.success(res.msg)
          this.getDictTypeByPage()
        })
      }).catch(() => {
      })
    },
    // 字典类型的行数据点击事件
    dictTypeRowClick(row) {
      this.currentDictType = row.dictType
      this.queryDictDataByDictType()
    },
    // 根据字典类型查询字典数据
    queryDictDataByDictType() {
      this.dataLoading = true
      sysDictDataApi.getDictDataByDictType(this.currentDictType).then(res => {
        this.dictDataList = res.data
        this.dataLoading = false
      })
    },
    // 关闭所有弹出层
    closeDialog() {
      this.addDictTypeDialog = false
      this.updateDictTypeDialog = false
      this.addDictDataDialog = false
      this.updateDictDataDialog = false
    },
    // 打开添加字典数据的弹出层
    toAddDictData() {
      this.title = '添加字典数据'
      this.addDictDataDialog = true
    },
    // 打开修改字典数据的弹出层
    toUpdateDictData(row) {
      this.updateDictDataDialog = true
      this.title = `修改【${row.dictLabel}】的字典数据`
      this.dictDataActiveId = row.dictCode
    },
    // 删除字典数据
    toDeleteDictData(row) {
      this.$confirm('此操作会删除【' + row.dictLabel + '】该字典数据，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        sysDictDataApi.deleteById(row.dictCode).then(res => {
          this.$message.success(res.msg)
          // 右边表格的数据重新查询
          this.queryDictDataByDictType()
        })
      }).catch(() => {
      })
    }
  }
}
</script>

<style scoped>

</style>
