<template>
  <div>
    <div v-if="flag">
      <el-tag v-for="(item, index) in dataList" v-if="item.dictValue==dictValue" :type="item.dictSkin">{{ item.dictLabel }}</el-tag>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Index',
  props: {
    dataList: {
      type: Array,
      default: []
    },
    value: {
      type: Number,
      default: null
    }
  },
  data() {
    return {
      flag: false,
      dictValue: undefined
    }
  },
  watch: {
    value: {
      immediate: true,
      handler: function(newVal, oldVal) {
        this.$nextTick(() => {
          this.flag = true
          this.dictValue = this.value
        })
      }
    }
  }
}
</script>
<style scoped>
</style>
