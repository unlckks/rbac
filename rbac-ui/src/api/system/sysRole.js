import request from '@/utils/request'
const groupName = 'sysRole'
export default {
  /**
   * 分页
   */
  getByPage(page) {
    return request({
      url: `/${groupName}/getByPage`,
      method: 'post',
      data: page
    })
  },
  /**
   * 添加
   */
  save(sysUser) {
    return request({
      url: `/${groupName}/save`,
      method: 'post',
      data: sysUser
    })
  },
  /**
   * 修改
   */
  update(sysUser) {
    return request({
      url: `/${groupName}/update`,
      method: 'put',
      data: sysUser
    })
  },

  /**
   * 删除
   */
  deleteById(id) {
    return request({
      url: `/${groupName}/delete/${id}`,
      method: 'delete'
    })
  },
  /**
   * 根据id查询
   */
  get(id) {
    return request({
      url: `/${groupName}/get/${id}`,
      method: 'get'
    })
  },
  /**
   * 根据角色ID查询当前角色拥有的菜单和权限
   */
  getCurrentRoleHasMenuIdsByRoleId(roleId){
    return request({
      url: `/${groupName}/getCurrentRoleHasMenuIdsByRoleId/`+roleId,
      method: 'get',
    })
  },
  /**
   * 保存角色菜单之间的关系
   */
  saveRoleMenu(roleId,menuIds){
    let params="roleId="+roleId
    menuIds.filter(menuId=>{
      params+="&menuIds="+menuId
    })
    return request({
      url: `/${groupName}/saveRoleMenu?`+params,
      method: 'post'
    })
  },
  /**
   * 查询所有角色不分页
   */
  getAllRoles() {
    return request({
      url: `/${groupName}/getAllRoles`,
      method: 'get',
    })
  },
  /**
   * 查询当前用户已拥有的角色IDS
   */
  getRoleIdsByUserId(userId) {
    return request({
      url: `/${groupName}/getRoleIdsByUserId/`+userId,
      method: 'get',
    })
  },
  /**
   * 保存用户和角色之间的关系
   */
  saveUserRoles(userId,roleIds){
    let params="userId="+userId
    roleIds.filter(roleId=>{
      params+="&roleIds="+roleId
    })
    return request({
      url: `/${groupName}/saveUserRoles?`+params,
      method: 'post'
    })
  }


}
