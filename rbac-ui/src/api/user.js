import request from '@/utils/request'

/**
 * 用户登录接口
 * @param data
 * @returns {AxiosPromise}
 */
export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    params:data
  })
}

/**
 * 得到用户信息的接口
 * @returns {AxiosPromise}
 */
export function getInfo() {
  return request({
    url: '/getUserInfo',
    method: 'get'
  })
}

/**
 * 用户退出的接口
 * @returns {AxiosPromise}
 */
export function logout() {
  return request({
    url: '/logout',
    method: 'get'
  })
}
//得到验证码
export function getCaptcha(){
  return request({
    url: `/captcha/getCaptcha`,
    method: 'get'
  })
}
