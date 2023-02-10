import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/* Router Modules */





/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 * 进行常量（不可被改变）
 */
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        name: '工作台',
        meta: { title: '工作台', icon: 'dashboard', affix: true }
      }
    ]
  },

]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 * 动态路由
 */
export const asyncRoutes = [
  {
    path: '/system',
    component: Layout,
    redirect: '/system/dictType',
    name: 'system', // will always show the root menu
    meta: {
      title: '系统管理',
      icon: 'lock'
    },
    children: [
      {
        path: 'dictType',
        component: () => import('@/views/system/dictType/sys-dict-type-list'),
        name: 'dictType',
        meta: {
          title: '字典管理',
          icon: 'lock'
        }
      },
      {
        path: 'dept',
        component: () => import('@/views/system/dept/sys-dept-list'),
        name: 'dept',
        meta: {
          title: '部门管理',
          icon: 'lock'
        }
      },
      {
        path: 'menu',
        component: () => import('@/views/system/menu/sys-menu-list'),
        name: 'menu',
        meta: {
          title: '菜单管理',
          icon: 'lock'
        }
      },
      {
        path: 'role',
        component: () => import('@/views/system/role/sys-role-list'),
        name: 'role',
        meta: {
          title: '角色管理',
          icon: 'lock'
        }
      },
      {
        path: 'user',
        component: () => import('@/views/system/user/sys-user-list'),
        name: 'user',
        meta: {
          title: '用户管理',
          icon: 'lock'
        }
      }
    ]
  },


  /** when your routing map is too long, you can split it into small modules **/


  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
