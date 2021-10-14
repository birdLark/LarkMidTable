export default [
  {
    path: '/naga/v1/storage/chart',
    method: 'get',
    handle () {
      var result = {
        code: 0,
        msg: '获取数据成功',
        data: {
          chartSettings: {
            dimension: ['createTime'],
            metrics: ['dfsUsed', 'totalFiles'],
            area: true
          },
          chartData: {
            columns: ['total', 'dfsUsed', 'totalBlocks', 'totalFiles', 'liveDataNodeNums', 'deadDataNodeNums', 'missingBlocks', 'volumeFailuresTotal', 'createTime'],
            rows: [
              { 'total': 100, 'dfsUsed': 40, 'totalBlocks': 100000, 'totalFiles': 200, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-20 10:20:20' },
              { 'total': 100, 'dfsUsed': 51, 'totalBlocks': 100000, 'totalFiles': 500, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-21 10:20:20' },
              { 'total': 100, 'dfsUsed': 55, 'totalBlocks': 100000, 'totalFiles': 1000, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-22 10:20:20' },
              { 'total': 100, 'dfsUsed': 60, 'totalBlocks': 100000, 'totalFiles': 800, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-23 10:20:20' },
              { 'total': 100, 'dfsUsed': 71, 'totalBlocks': 100000, 'totalFiles': 1000, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-24 10:20:20' },
              { 'total': 100, 'dfsUsed': 80, 'totalBlocks': 100000, 'totalFiles': 2000, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-25 10:20:20' }
            ]
          }
        }

      }
      console.log(result)
      return result
    }
  }
]
