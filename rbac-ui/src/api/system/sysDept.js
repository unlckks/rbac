import request from '@/utils/request'

const groupName = 'sysDept'
export default {
  /**
   * 查询所有部门
   */
  listDept(params) {
    return request({
      url: `/${groupName}/listDept`,
      method: 'get',
      params: params
    })
  },
  /**
   * 添加
   */
  addDept(data) {
    return request({
      url: `/${groupName}/save`,
      method: 'post',
      data: data
    })
  },
  /**
   * 修改
   */
  updateDept(data) {
    return request({
      url: `/${groupName}/update`,
      method: 'put',
      data: data
    })
  },
  /**
   * 删除
   */
  deleteById(id) {
    return request({
      url: `/${groupName}/delete/` + id,
      method: 'delete'
    })
  }
}
