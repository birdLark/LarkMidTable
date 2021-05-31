import layoutHeaderAside from '@/layout/header-aside'

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require('@/libs/util.import.' + process.env.NODE_ENV)

/**
 * 在主框架内显示
 */
const frameIn = [
  {
    path: '/',
    redirect: { name: 'index' },
    component: layoutHeaderAside,
    children: [
      // 首页
      {
        path: 'index',
        name: 'index',
        meta: {
          auth: true
        },
        component: _import('system/index')
      },
      // 功能页面
      {
        path: 'dashboard',
        name: 'dashboard',
        meta: {
          title: '仪表盘',
          auth: true
        },
        component: _import('dashboard/dash')
      },
      {
        path: 'dashboard/chart',
        name: 'chart',
        meta: {
          title: '图表',
          auth: true
        },
        component: _import('dashboard/chart')
      },
      {
        path: 'log',
        name: 'log',
        meta: {
          title: '操作日志',
          auth: true
        },
        component: _import('manager/log')
      },
      {
        path: 'user',
        name: 'user',
        meta: {
          title: '用户管理',
          auth: true
        },
        component: _import('manager/user')
      },
      {
        path: 'team',
        name: 'team',
        meta: {
          title: '团队管理',
          auth: true
        },
        component: _import('manager/team')
      },
      {
        path: 'auth',
        name: 'auth',
        meta: {
          title: '权限管理',
          auth: true
        },
        component: _import('manager/auth')
      },
      {
        path: 'app',
        name: 'app',
        meta: {
          title: '业务线管理',
          auth: true
        },
        component: _import('operation/app')
      },
      {
        path: 'datasource',
        name: 'datasource',
        meta: {
          title: '数据源管理',
          auth: true
        },
        component: _import('operation/datasource')
      },
      {
        path: 'db',
        name: 'db',
        meta: {
          title: '数仓库管理',
          auth: true
        },
        component: _import('operation/db')
      },
      {
        path: 'datasync',
        name: 'datasync',
        meta: {
          title: '数据同步',
          auth: true
        },
        component: _import('operation/datasync')
      },
      {
        path: 'privilege',
        name: 'privilege',
        meta: {
          title: '权限管理',
          auth: true
        },
        component: _import('operation/privilege')
      },
      {
        path: 'calc',
        name: 'calc',
        meta: {
          title: '计算概览',
          auth: true
        },
        component: _import('overview/calc')
      },
      {
        path: 'storage',
        name: 'storage',
        meta: {
          title: '存储概览',
          auth: true
        },
        component: _import('overview/storage')
      },
      {
        path: 'query',
        name: 'query',
        meta: {
          title: '数据查询',
          auth: true
        },
        component: _import('query')
      },
      {
        path: 'plugin',
        name: 'plugin',
        meta: {
          title: '插件管理',
          auth: true
        },
        component: _import('task/plugin')
      },
      {
        path: 'var',
        name: 'var',
        meta: {
          title: '变量管理',
          auth: true
        },
        component: _import('task/var')
      },
      {
        path: 'workflow',
        name: 'workflow',
        meta: {
          title: '流程管理',
          auth: true
        },
        component: _import('task/workflow')
      },
      {
        path: 'easyflow',
        name: 'easyflow',
        meta: {
          title: '新建流程',
          auth: true
        },
        component: _import('task/easyflow')
      },
      {
        path: 'draw',
        name: 'draw',
        meta: {
          title: '新建图表',
          auth: true
        },
        component: _import('dashboard/draw')
      },
      // 系统 前端日志
      {
        path: 'log',
        name: 'log',
        meta: {
          title: '前端日志',
          auth: true
        },
        component: _import('system/log')
      },
      // 刷新页面 必须保留
      {
        path: 'refresh',
        name: 'refresh',
        hidden: true,
        component: _import('system/function/refresh')
      },
      // 页面重定向 必须保留
      {
        path: 'redirect/:route*',
        name: 'redirect',
        hidden: true,
        component: _import('system/function/redirect')
      }
    ]
  }
]

/**
 * 在主框架之外显示
 */
const frameOut = [
  // 登录
  {
    path: '/login',
    name: 'login',
    component: _import('system/login')
  }
]

/**
 * 错误页面
 */
const errorPage = [
  {
    path: '*',
    name: '404',
    component: _import('system/error/404')
  }
]

// 导出需要显示菜单的
export const frameInRoutes = frameIn

// 重新组织后导出
export default [
  ...frameIn,
  ...frameOut,
  ...errorPage
]
