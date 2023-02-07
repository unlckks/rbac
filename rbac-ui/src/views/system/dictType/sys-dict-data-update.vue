<template>
  <div>
    <el-form ref="form" :rules="rules" :model="sysDictData" label-width="80px" size="small">
      <el-form-item label="字典类型">
        <el-input v-model="sysDictData.dictType" :disabled="true" />
      </el-form-item>
      <el-form-item label="数据标签" prop="dictLabel">
        <el-input v-model="sysDictData.dictLabel" placeholder="请输入数据标签" />
      </el-form-item>
      <el-form-item label="数据键值" prop="dictValue">
        <el-input v-model="sysDictData.dictValue" placeholder="请输入数据键值" />
      </el-form-item>
      <el-form-item label="显示排序" prop="dictSort">
        <el-input-number v-model="sysDictData.dictSort" controls-position="right" :min="0" style="width: 100%" />
      </el-form-item>
      <el-form-item label="皮肤属性" prop="listClass">
        <el-select v-model="sysDictData.dictSkin" placeholder="请选择皮肤属性">
          <el-option
            v-for="item in dictSkinOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="sysDictData.remark" type="textarea" placeholder="请输入内容"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="sysDictData.status">
          <el-radio v-for="item in statusOptions" :label="item.dictValue">{{ item.dictLabel }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="update">修改</el-button>
        <el-button type="warning" @click="sysDictData = {}">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import sysDictDataApi from '@/api/system/sysDictData'

export default {
  props:{
    activeId:{
      type: Number,
      default: null
    }
  },
  watch: {
    activeId: {
      immediate: true,
      handler: function(newVal, oldVal) {
        this.getById(newVal)
      }
    }
  },
  data() {
    return {
      // 表单对象
      sysDictData: {
        status: 0
      },
      // 数据标签显示样式
      dictSkinOptions: [
        {value: "",label: "默认"},
        {value: "success", label: "成功"},
        {value: "info", label: "信息"},
        {value: "warning", label: "警告"},
        {value: "danger",label: "危险"}
      ],
      // 表单校验
      rules: {
        dictLabel: [
          { required: true, message: "数据标签不能为空", trigger: "blur" }
        ],
        dictValue: [
          { required: true, message: "数据键值不能为空", trigger: "blur" }
        ],
        dictSort: [
          { required: true, message: "数据顺序不能为空", trigger: "blur" }
        ]
      },
      //声明属性去存放字典数据
      statusOptions:[]
    }
  },
  created() {
    //加载字典类型加载数据状态的字典数据
    this.getDictDataByDictType();
  },
  methods: {
    //根据字典类型加载数据状态的字典数据
    getDictDataByDictType(){
      sysDictDataApi.getDictDataByDictType("sys_status_type").then(res=>{
        this.statusOptions=res.data;
      })
    },
    //根据ID查询
    getById(id){
      sysDictDataApi.getById(id).then(res=>{
        this.sysDictData=JSON.parse(JSON.stringify(res.data))
      })
    },
    // 更新字典数据
    update() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          sysDictDataApi.update(this.sysDictData).then(res => {
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
