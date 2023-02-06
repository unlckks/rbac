import request from '@/utils/request'

const groupName = 'sysDictData'
export default {
  /**
   * 根据字典类型获取字典数据
   */
  getDictDataByDictType(dictType) {
    return request({
      url: `/${groupName}/getDictDataByDictType/` + dictType,
      method: 'get'
    })
  },
  /**
   * 添加字典类型
   */
  save(data) {
    return request({
      url: `/${groupName}/save`,
      method: 'post',
      data: data
    })
  },
  /**
   * 根据字典ID查询字典类型数据对象
   */
  getById(id) {
    return request({
      url: `/${groupName}/get/` + id,
      method: 'get'
    })
  },
  /**
   * 修改字典数据
   */
  update(data) {
    return request({
      url: `/${groupName}/update`,
      method: 'put',
      data: data
    })
  },
  /**
   * 删除字典数据
   */
  deleteById(id) {
    return request({
      url: `/${groupName}/delete/` + id,
      method: 'delete'
    })
  }
}
