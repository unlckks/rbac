<template>
  <div>
    <el-form ref="form" :model="sysUser" :rules="rules" label-width="80px" size="small">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="所在部门">
            <treeselect
              v-model="sysUser.deptId"
              :options="deptOptions"
              :normalizer="normalizer"
              :show-count="true"
              placeholder="选择所在部门"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="账号" prop="userName">
            <el-input v-model="sysUser.userName" placeholder="请输入账号" clearable />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="昵称" prop="nickName">
            <el-input v-model="sysUser.nickName" placeholder="请输入昵称" clearable />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="sysUser.email" placeholder="请输入邮箱" clearable />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="sysUser.phone" placeholder="请输入手机号" clearable />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="性别" prop="sex">
            <el-radio-group v-model="sysUser.sex">
              <el-radio v-for="status in sexOptions" :key="status.dictValue" :label="status.dictValue">{{ status.dictLabel }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="sysUser.status">
              <el-radio v-for="status in statusOptions" :key="status.dictValue" :label="status.dictValue">{{ status.dictLabel }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" @click="add">添加</el-button>
        <el-button type="warning" @click="sysUser = {sex:0,status:0}">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import sysUserApi from '@/api/system/sysUser'
import sysDeptApi from '@/api/system/sysDept'
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import sysMenuApi from "@/api/system/sysMenu";
import {handleTree} from "@/utils/leige";
export default {
  components: {Treeselect},
  created() {
    this.sysUser={}
    this.getDataByType("sys_status_type").then(res=>{
      this.statusOptions=res.data;
    })
    this.getDataByType("sys_user_sex").then(res=>{
      this.sexOptions=res.data;
    })
    this.sysUser={sex:0,status:0}
    this.getTreeselect();
  },
  data() {
    return {
      // 用户表单
      sysUser: {},
      // 表单校验对象
      rules: {
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 5, message: '用户名最短5位', trigger: 'change' }
        ],
        nickName: [
          { required: true, message: '请输入昵称', trigger: 'blur' }
        ]
      },
      //性别字典数据
      sexOptions:[],
      //状态数据
      statusOptions:[],
      //部门数据
      deptOptions:[]
    }
  },
  methods: {
    // 添加
    add() {
      // 2.表单校验
      sysUserApi.save(this.sysUser).then(res => {
        this.$message.success(res.msg)
        // 关闭对话框，并刷新列表
        this.$emit('close')
        this.$emit('after')
      })
    },
    /** 转换菜单数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children
      };
    },
    /** 查询菜单下拉树结构 */
    getTreeselect() {
      sysDeptApi.listDept().then(res => {
        this.deptOptions = [];
        this.deptOptions.push(handleTree(res.data, "deptId")[0]);
      });
    }
  }
}
</script>

<style>

</style>
