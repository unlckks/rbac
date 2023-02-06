import request from '@/utils/request'

const groupName = 'sysDictType'
export default {
  /**
   * 分页查询所有字典类型
   */
  getByPage(data) {
    return request({
      url: `/${groupName}/getByPage`,
      method: 'post',
      data:data
    })
  },
  /**
   * 添加字典类型
   */
  save(data){
    return request({
      url: `/${groupName}/save`,
      method: 'post',
      data:data
    })
  },
  /**
   * 根据字典ID查询字典类型对象
   */
  getById(id){
    return request({
      url: `/${groupName}/get/`+id,
      method: 'get'
    })
  },
  /**
   * 修改字典类型
   */
  update(data){
    return request({
      url: `/${groupName}/update`,
      method: 'put',
      data:data
    })
  },
  /**
   * 删除字典类型
   */
  deleteById(id){
    return request({
      url: `/${groupName}/delete/`+id,
      method: 'delete'
    })
  },
  /**
   * 刷新缓存
   */
  refreshCache(){
    return request({
      url: `/${groupName}/refreshCache`,
      method: 'get'
    })
  }
}
