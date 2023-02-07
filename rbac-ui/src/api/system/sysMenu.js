
import request from '@/utils/request'

const groupName = 'sysMenu'
export default {
  /**
   * 查询所有目录菜单权限
   */
  listMenu(params) {
    return request({
      url: `/${groupName}/listMenu`,
      method: 'get',
      params:params
    })
  },
  /**
   * 查询所有目录菜单
   */
  listMenuMC() {
    return request({
      url: `/${groupName}/listMenuMC`,
      method: 'get'
    })
  },
  /**
   * 添加
   */
  addMenu(data) {
    return request({
      url: `/${groupName}/save`,
      method: 'post',
      data:data
    })
  },
  /**
   * 修改
   */
  updateMenu(data) {
    return request({
      url: `/${groupName}/update`,
      method: 'put',
      data:data
    })
  },
  /**
   * 删除
   */
  deleteById(id) {
    return request({
      url: `/${groupName}/delete/`+id,
      method: 'delete'
    })
  }
}
