<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row>
        <el-col :span="24">
          <el-form-item label="上级菜单">
            <treeselect
              v-model="form.parentId"
              :options="menuOptions"
              key-id="menuId"
              :normalizer="normalizer"
              :show-count="true"
              placeholder="选择上级菜单"
            />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="菜单类型" prop="menuType">
            <el-radio-group v-model="form.menuType">
              <el-radio :label="0">目录</el-radio>
              <el-radio :label="1">菜单</el-radio>
              <el-radio :label="2">按钮</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="24" v-if="form.menuType!=2">
          <el-form-item label="菜单图标" prop="icon">
            <el-popover
              placement="bottom-start"
              width="460"
              trigger="click"
              @show="$refs['iconSelect'].reset()"
            >
              <IconSelect ref="iconSelect" @selected="selected"/>
              <el-input slot="reference" v-model="form.icon" placeholder="点击选择图标" readonly>
                <svg-icon
                  v-if="form.icon"
                  slot="prefix"
                  :icon-class="form.icon"
                  class="el-input__icon"
                  style="height: 32px;width: 16px;"
                />
                <i v-else slot="prefix" class="el-icon-search el-input__icon"/>
              </el-input>
            </el-popover>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="菜单名称" prop="menuName">
            <el-input v-model="form.menuName" placeholder="请输入菜单名称"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="显示排序" prop="orderNum">
            <el-input-number v-model="form.orderNum" controls-position="right" :min="0"/>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="form.menuType!=2">
          <el-form-item prop="path">
              <span slot="label">
                <el-tooltip content="访问的路由地址，如：`user`，如外网地址需内链访问则以`http(s)://`开头" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                路由地址
              </span>
            <el-input v-model="form.path" placeholder="请输入路由地址"/>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="form.menuType==1">
          <el-form-item prop="component">
              <span slot="label">
                <el-tooltip content="访问的组件路径，如：`system/user/index`，默认在`views`目录下" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                组件路径
              </span>
            <el-input v-model="form.component" placeholder="请输入组件路径"/>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="form.menuType != 0">
          <el-form-item>
            <el-input v-model="form.perms" placeholder="请输入权限标识" maxlength="100"/>
            <span slot="label">
                <el-tooltip content="控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasPermi('system:user:list')`)" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                权限字符
              </span>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="form.menuType == 1">
          <el-form-item>
            <el-input v-model="form.query" placeholder="请输入路由参数" maxlength="255"/>
            <span slot="label">
                <el-tooltip content='访问路由的默认传递参数，如：`{"id": 1, "name": "ry"}`' placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                路由参数
              </span>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="form.menuType != 2">
          <el-form-item>
              <span slot="label">
                <el-tooltip content="选择隐藏则路由将不会出现在侧边栏，但仍然可以访问" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                显示状态
              </span>
            <el-radio-group v-model="form.visible">
              <el-radio v-for="status in visibleOptions" :label="status.dictValue">{{ status.dictLabel }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="form.menuType != 2">
          <el-form-item>
              <span slot="label">
                <el-tooltip content="选择停用则路由将不会出现在侧边栏，也不能被访问" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                菜单状态
              </span>
            <el-radio-group v-model="form.status">
              <el-radio v-for="status in statusOptions" :label="status.dictValue">{{ status.dictLabel }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :sapn="24" style="text-align: center">
          <el-button type="primary" @click="add">添加</el-button>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>
<script>
import sysDictDataApi from "@/api/system/sysDictData";
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import sysMenuApi from "@/api/system/sysMenu"
import {handleTree} from "@/utils/leige";
import IconSelect from "@/components/IconSelect"

export default {
  name: "sys-menu-add",
  components: {
    Treeselect,
    IconSelect
  },
  props: {
    activeObj: {
      type: Object,
      default: undefined
    }
  },
  watch: {
    activeObj: {
      immediate: true,
      handler: function (newVal, oldVal) {
        if (newVal != null) {
          if (newVal.menuType == 0) {
            //目录下添加
            this.form = {parentId: newVal.menuId, icon: "", menuType: 1, visible: 0, status: 0}
          } else if (newVal.menuType == 1) {
            //菜单下添加
            this.form = {parentId: newVal.menuId, icon: "", menuType: 2, visible: 0, status: 0}
          }
        } else {
          this.form = {icon: "", menuType: 0, visible: 0, status: 0}
        }
      }
    }
  },
  data() {
    return {
      form: {
        icon: ""
      },
      rules: {},
      //菜单下拉列表的数据
      menuOptions: [],
      //是否显示的属性
      visibleOptions: [],
      //正常使用的属性
      statusOptions: []
    }
  },
  created() {
    sysMenuApi.listMenuMC().then(res => {
      this.menuOptions = [];
      let menu = {"menuId": 0, "menuName": "主类目", "children": []};
      menu.children = handleTree(res.data, "menuId");
      this.menuOptions.push(menu)
    })
    //查询字典数据
    sysDictDataApi.getDictDataByDictType("sys_status_type").then(res => {
      this.statusOptions = res.data;
    })
    //查询显示属性
    sysDictDataApi.getDictDataByDictType("sys_show_hide").then(res => {
      this.visibleOptions = res.data;
    })
  },
  methods: {
    //转换菜单数据结构
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children
      }
      return {
        id: node.menuId,
        label: node.menuName,
        children: node.children
      }
    },
    //图标选择器事件
    selected(val) {
      this.form.icon = val
    },
    //保存菜单的数据
    add() {
   this.$refs.form.validate(valid=>{
     if(valid){
      sysMenuApi.addMenu(this.form).then(res=>{
        this.$message.success(res.msg);
        this.$emit('after');
        this.$emit('close');
      });
     }
   });
    }
  }
}
</script>

<style scoped>

</style>
