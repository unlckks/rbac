<template>
  <div>
    <div class="header-container" STYLE="text-align: center">
      <el-image
        style="width: 100px; height: 100px"
        :src="activeObj.avatar"
        fit="fill"
      />
    </div>
    <el-descriptions class="margin-top" :column="3"  border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          用户名
        </template>
        {{ activeObj.userName }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          昵称
        </template>
        {{ activeObj.nickName }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          手机号
        </template>
        {{ activeObj.phone }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-message"></i>
          邮箱
        </template>
        {{ activeObj.email }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          性别
        </template>
        <dict-tag :value="activeObj.sex" :data-list="sexOptions"></dict-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          状态
        </template>
        <dict-tag :value="activeObj.status" :data-list="statusOptions"></dict-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-picture-outline-round"></i>
          最后登录IP
        </template>
        <el-tag size="small">
          {{activeObj.loginIp}}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          最后登录时间
        </template>
        {{activeObj.loginDate}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-custom"></i>
          创建者
        </template>
        {{activeObj.createBy}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          创建时间
        </template>
        {{activeObj.createTime}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-custom"></i>
          更新者
        </template>
        {{activeObj.updateBy}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          更新时间
        </template>
        {{activeObj.updateTime}}
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script>
export default {
  name: "sys-user-info",
  props:{
    activeObj:{
      type:Object,
      default:undefined
    },
    sexOptions:{
      type:Array,
      default: []
    },
    statusOptions:{
      type:Array,
      default:[]
    }
  }
}
</script>

<style scoped>

</style>
