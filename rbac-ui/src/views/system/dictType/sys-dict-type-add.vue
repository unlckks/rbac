<template>
  <div>
    <el-form ref="form" :rules="rules" :model="sysDictType" label-width="80px" size="small">
      <el-form-item label="字典名称" prop="dictName">
        <el-input v-model="sysDictType.dictName" placeholder="请输入字典名称" clearable />
      </el-form-item>
      <el-form-item label="字典类型" prop="dictType">
        <el-input v-model="sysDictType.dictType" placeholder="请输入字典类型" clearable />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="sysDictType.remark" placeholder="请输入备注" type="textarea" clearable />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="sysDictType.status">
          <el-radio v-for="item in statusOptions" :label="item.dictValue">{{ item.dictLabel }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="add">添加</el-button>
        <el-button type="warning" @click="sysDictType = {}">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import sysDictTypeApi from '@/api/system/sysDictType'
import sysDictDataApi from '@/api/system/sysDictData'

export default {
  data() {
    return {
      // 表单对象
      sysDictType: {
        status: 0
      },
      // 表单校验
      rules: {
        dictName: [
          { required: true, message: '字典名称不能为空', trigger: 'blur' }
        ],
        dictType: [
          { required: true, message: '字典类型不能为空', trigger: 'blur' }
        ]
      },
      // 声明属性存放字典的数据
      statusOptions: []
    }
  },
  created() {
    // 加载字典类型加载数据状态的数据
    this.getDictDataByDictType()
  },
  methods: {
    // 根据字典类型加载数据状态的字典数据
    getDictDataByDictType() {
      // 掉后台传值
      sysDictDataApi.getDictDataByDictType('sys_status_type').then(res => {
        this.statusOptions = res.data
      })
    },
    // 添加字典类型
    add() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          sysDictTypeApi.save(this.sysDictType).then(res => {
            this.$message.success(res.msg)
            this.$emit('after')
            this.$emit('close')
          })
        }
      })
    }
  }
}
</script>
<style>
</style>
