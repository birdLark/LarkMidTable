import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/* Router Modules */
// import componentsRouter from './modules/components'
// import chartsRouter from './modules/charts'
// import tableRouter from './modules/table'
// import nestedRouter from './modules/nested'
import toolRouter from './modules/tool'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    noCache: true                if set true, the page will no be cached(default is false)
    affix: true                  if set true, the tag will affix in the tags-view
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
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
    name: 'Dashboard',
    meta: { title: '数据报表', icon: 'dashboard', affix: true },
    children: [
      {
        path: '/dashboard',
        component: () => import('@/views/dashboard/admin/index'),
        name: 'Dashboard',
        meta: { title: '数据报表', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/datax/Infrastructure',
    component: Layout,
    redirect: '/datax/Infrastructure',
    name: 'Infrastructure',
    meta: { title: '基础建设', icon: 'guide' },
    children: [
      {
        path: '/datax/user',
        name: 'User',
        component: () => import('@/views/datax/user/index'),
        meta: { title: '用户管理', icon: 'peoples' }
      },
      {
        path: '/datax/resource',
        name: 'Resource',
        component: () => import('@/views/datax/resource/index'),
        meta: { title: '资源管理', icon: 'battery-line' }
      }
    ]
  },
  {
    path: '/datax/Database',
    component: Layout,
    redirect: '/datax/DatabaseSync',
    name: 'DatabaseSync',
    meta: { title: '数据治理', icon: 'skill' },
    children: [
      {
        path: '/datax/DatabaseSync/metadataManagement',
        name: 'MetadataManagement',
        component: () => import('@/views/datax/metadataManagement/index'),
        meta: { title: '元数据管理', icon: 'project' }
      },
      {
        path: '/datax/DatabaseSync/dataDirectory',
        name: 'dataDirectory',
        component: () => import('@/views/datax/dataDirectory/index'),
        meta: { title: '数据目录', icon: 'cfg-datasouce' }
      },
      {
        path: '/datax/DatabaseSync/dataRelated',
        name: 'dataRelated',
        component: () => import('@/views/datax/dataRelated/index'),
        meta: { title: '数据血缘', icon: 'icon' }
      },
      {
        path: '/datax/DatabaseSync/dataQualityTest',
        name: 'dataQualityTest',
        component: () => import('@/views/datax/dataQualityTest/index'),
        meta: { title: '数据质量检测', icon: 'json' }
      }
    ]
  }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  {
    path: '/profile',
    component: Layout,
    redirect: '/profile/index',
    hidden: true,
    children: [
      {
        path: 'index',
        component: () => import('@/views/profile/index'),
        name: 'Profile',
        meta: { title: 'Profile', icon: 'user', noCache: true }
      }
    ]
  },
  {
    path: '/error',
    component: Layout,
    redirect: 'noRedirect',
    name: 'ErrorPages',
    hidden: true,
    meta: {
      title: 'Error Pages',
      icon: '404'
    },
    children: [
      {
        path: '401',
        component: () => import('@/views/error-page/401'),
        name: 'Page401',
        meta: { title: '401', noCache: true }
      },
      {
        path: '404',
        component: () => import('@/views/error-page/404'),
        name: 'Page404',
        meta: { title: '404', noCache: true }
      }
    ]
  },
  {
    path: '/datax/DatabaseSync',
    component: Layout,
    redirect: '/datax/DatabaseSync',
    name: 'DatabaseSync',
    meta: { title: '数据集成', icon: 'project' },
    children: [
      {
        path: '/datax/DatabaseSync/JobProject',
        name: 'JobProject',
        component: () => import('@/views/datax/jobProject/index'),
        meta: { title: '项目管理', icon: 'project' }
      },
      // {
      //   path: '/datax/DatabaseSync/executor',
      //   name: 'Executor',
      //   component: () => import('@/views/datax/executor/index'),
      //   meta: { title: '执行器管理', icon: 'exe-cfg' }
      // },
      {
        path: '/datax/DatabaseSync/jdbcDatasource',
        name: 'JdbcDatasource',
        component: () => import('@/views/datax/jdbc-datasource/index'),
        meta: { title: '数据源管理', icon: 'cfg-datasouce' }
      },
      {
        path: '/datax/DatabaseSync/job/jobTemplate',
        name: 'JobTemplate',
        component: () => import('@/views/datax/jobTemplate/index'),
        meta: { title: '任务模板', icon: 'task-tmp' }
      },
      {
        path: '/datax/DatabaseSync/job/jsonBuild',
        name: 'JsonBuild',
        component: () => import('@/views/datax/json-build/index'),
        meta: { title: '任务构建', icon: 'guide', noCache: false }
      },
      {
        path: '/datax/DatabaseSync/job/jsonBuildBatch',
        name: 'JsonBuildBatch',
        component: () => import('@/views/datax/json-build-batch/index'),
        meta: { title: '任务批量构建', icon: 'batch-create', noCache: false }
      },
      {
        path: '/datax/DatabaseSync/job/jobInfo',
        name: 'JobInfo',
        component: () => import('@/views/datax/jobInfo/index'),
        meta: { title: '实例管理', icon: 'task-cfg' }
      },
      {
        path: '/datax/DatabaseSync/jobLog',
        name: 'JobLog',
        component: () => import('@/views/datax/jobLog/index'),
        meta: { title: '日志管理', icon: 'log' }
      }
    ]
  },
  {
    path: '/datax/dataDevelopment',
    component: Layout,
    redirect: '/datax/dataDevelopment',
    name: 'DatabaseSync',
    meta: { title: '数据开发', icon: 'cfg-datasouce' },
    children: [
      {
        path: '/datax/devEnvSetting',
        name: 'DevEnvSetting',
        component: () => import('@/views/datax/devEnvSetting/index'),
        meta: { title: '开发环境配置', icon: 'exe-cfg' }
      },
      {
        path: '/datax/dataDevelopment/UDFlistManageDevelopment',
        name: 'UDFlistManageDevelopment',
        component: () => import('@/views/datax/UDFlistManageDevelopment/index'),
        meta: { title: 'UDF列表管理', icon: 'fullscreen' }
      },
      {
        path: '/datax/dataDevelopment/SQLlistManageDevelopment',
        name: 'SQLlistManageDevelopment',
        component: () => import('@/views/datax/SQLlistManageDevelopment/index'),
        meta: { title: 'SQL列表管理', icon: 'cfg-datasouce' }
      },
      {
        path: '/datax/dataDevelopment/DevelopmentList',
        name: 'DevelopmentList',
        component: () => import('@/views/datax/DevelopmentList/index'),
        meta: { title: '开发任务列表', icon: 'nested' }
      },
      {
        path: '/datax/dataDevelopment/job/jobTemplate',
        name: 'JobTemplate',
        component: () => import('@/views/datax/jobTemplate/index'),
        meta: { title: '作业管理', icon: 'task-tmp' }
      }
    ]
  },
  {
    path: '/datax/Datawatchservices',
    component: Layout,
    redirect: '/datax/Datawatchservices',
    name: 'Dataservices',
    meta: { title: '监控告警', icon: 'task-tmp' },
    children: [
      {
        path: '/datax/DatabaseSync/executor',
        name: 'Executor',
        component: () => import('@/views/datax/executor/index'),
        meta: { title: '存储资源', icon: 'tree' }
      },
      {
        path: '/datax/DatabaseSync/executor',
        name: 'Executor',
        component: () => import('@/views/datax/executor/index'),
        meta: { title: '计算资源', icon: 'exe-cfg' }
      },
      {
        path: '/datax/Datawatchservices/warnSet',
        name: 'warnSet',
        component: () => import('@/views/datax/warnSet/index'),
        meta: { title: '告警设置', icon: 'project' }
      },
      {
        path: '/datax/Datawatchservices/warnLog',
        name: 'warnLog',
        component: () => import('@/views/datax/warnLog/index'),
        meta: { title: '告警日志', icon: 'bug' }
      }

    ]
  },
  {
    path: '/datax/Dataservices',
    component: Layout,
    redirect: '/datax/Dataservices',
    name: 'Dataservices',
    meta: { title: '数据服务', icon: 'exe-cfg' },
    children: [
      {
        path: '/datax/Dataservices/VisualAPI',
        name: 'VisualAPI',
        component: () => import('@/views/datax/VisualAPI/index'),
        meta: { title: '可视化API构建', icon: 'project' }
      }
    ]
  },
  {
    path: '/data/log',
    hidden: true,
    component: () => import('@/views/datax/jobLog/log'),
    meta: { title: '任务日志', icon: 'work' }
  },

  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: [...asyncRoutes, ...constantRoutes]
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
