export default [
  {
    path: '/naga/v1/storage',
    method: 'get',
    handle () {
      var result = {
        code: 0,
        msg: '获取数据成功',
        data: { 'total': 100, 'dfsUsed': 80, 'totalBlocks': 100000, 'totalFiles': 2000, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-25 10:20:20' }
      }
      console.log(result)
      return result
    }
  }
]
