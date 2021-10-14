// 菜单 侧边栏
export default [
  { path: '/index', title: '首页', icon: 'home' },
  {
    title: '集群概览',
    icon: 'desktop',
    children: [
      { path: '/storage', icon: 'folder-open', title: '存储概览' },
      { path: '/calc', icon: 'object-group', title: '计算概览' },
      { path: 'http://47.108.140.82:3000', icon: 'magnet', title: '集群监控' }
    ]
  },
  {
    title: '运营管理',
    icon: 'male',
    children: [
      { path: '/app', icon: 'map-signs', title: '业务线管理' },
      { path: '/datasource', icon: 'database', title: '数据源管理' },
      { path: '/db', icon: 'database', title: '数仓库管理' },
      // { path: '/datasync', icon: 'exchange', title: '数据同步' },
      { path: '/privilege', icon: 'group', title: '权限管理' }
    ]
  },
  {
    title: '任务管理',
    icon: 'tasks',
    children: [
      { path: '/plugin', icon: 'puzzle-piece', title: '插件管理' },
      { path: '/var', icon: 'flag', title: '变量管理' },
      { path: '/workflow', icon: 'sitemap', title: '流程管理' }
    ]
  },
  {
    title: '数据查询',
    icon: 'hourglass',
    path: '/query'
  },
  {
    title: '数据可视化',
    icon: 'dashboard',
    path: '/dashboard'
  },
  {
    title: '系统管理',
    icon: 'cogs',
    children: [
      { path: '/user', icon: 'user-circle-o', title: '用户管理' },
      { path: '/team', icon: 'users', title: '团队管理' },
      { path: '/auth', icon: 'paper-plane', title: '权限管理' },
      { path: '/log', icon: 'pencil-square-o', title: '操作日志' }

    ]
  }
]
