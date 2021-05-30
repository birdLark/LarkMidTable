export default [
  {
    path: '/naga/v1/log',
    method: 'get',
    handle ({ params, Repeat }) {
      console.log(params)
      var result = {
        code: 0,
        msg: '获取数据成功',
        data: {
          page: {
            total: 1000
          },
          logs: [
            {
              'id': 4,
              'user': 'admin',
              'opeation': '创建业务线',
              'obj': 'AppId:12 Name:XX业务',
              'createTime': '2020-01-02 12:00:00'
            }, {
              'id': 3,
              'user': 'admin',
              'opeation': '上传Plugin',
              'obj': 'PluginID:14 Name:测试Plugin2 Lang：Python',
              'createTime': '2020-01-01 12:00:00'
            }, {
              'id': 2,
              'user': 'admin',
              'opeation': '删除Plugin',
              'obj': 'PluginID:12 Name:测试Plugin Lang：Python',
              'createTime': '2020-01-01 10:00:00'
            },
            {
              'id': 1,
              'user': 'admin',
              'opeation': '上传Plugin',
              'obj': 'PluginID:12 Name:测试Plugin Lang：Python',
              'createTime': '2020-01-01 9:00:00'
            }
          ]
        }
      }
      console.log(result)
      return result
    }
  }
]
