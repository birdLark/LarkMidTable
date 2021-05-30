export default [
  {
    path: '/naga/v1/calc/chart',
    method: 'get',
    handle () {
      var result = {
        code: 0,
        msg: '获取数据成功',
        data: {
          chartSettings: {
            dimension: ['queueName'],
            metrics: ['appsPending', 'appsRunging'],
            area: true
          },
          chartData: {
            columns: ['queueName', 'appsPending', 'appsRunging', 'allocatedMB', 'availableMB', 'ActiveUsers'],
            rows: [
              {
                'queueName': 'queue1',
                'appsPending': 1,
                'appsRunging': 3,
                'allocatedMB': 100,
                'availableMB': 20,
                'ActiveUsers': 2
              },
              {
                'queueName': 'queue2',
                'appsPending': 2,
                'appsRunging': 4,
                'allocatedMB': 80,
                'availableMB': 10,
                'ActiveUsers': 1
              },
              {
                'queueName': 'queue3',
                'appsPending': 2,
                'appsRunging': 3,
                'allocatedMB': 150,
                'availableMB': 80,
                'ActiveUsers': 6
              }
            ]
          }
        }

      }
      console.log(result)
      return result
    }
  }
]
