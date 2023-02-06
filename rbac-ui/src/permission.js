import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login', '/auth-redirect'] // no redirect whitelist
//设置路由守卫
router.beforeEach(async(to, from, next) => {
  // 启动进度条
  NProgress.start()
  // 设置页头的title的值
  document.title = getPageTitle(to.meta.title)
  // 得到token
  const hasToken = getToken()
  if (hasToken) {
    if (to.path === '/login') {
      // 如果有token但是你的路由地址为/login 就直接跳转到主页
      next({ path: '/' })
      NProgress.done()
    } else {
      const roles = store.getters.roles && store.getters.roles.length > 0
      if (roles) {
        next() //直接放行路由，也就是可以切换
      } else {
        try {
          //会去调用user/getInfo
          const { permissions } = await store.dispatch('user/getInfo')
          const accessRoutes = await store.dispatch('permission/generateRoutes',permissions)
          // 处理动态路由【去请求后台的菜单】
          router.addRoutes(accessRoutes)
          next({ ...to, replace: true })
        } catch (error) {
          await store.dispatch('user/resetToken')
          Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/

    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
