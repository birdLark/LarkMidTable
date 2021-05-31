export default [
  {
    path: '/naga/v1/calc',
    method: 'get',
    handle () {
      var result = {
        code: 0,
        msg: '获取数据成功',
        data: { 'liveNodeManagerNums': 10,
          'deadNodeManagerNums': 0,
          'unhealthyNodeManagerNums': 0,
          'submittedApps': 20,
          'runningApps': 10,
          'pendingApps': 0,
          'completedApps': 10,
          'killedApps': 0,
          'failedApps': 0,
          'allocatedMem': 20,
          'allocatedCores': 10,
          'allocatedContainers': 0,
          'availableMem': 10,
          'availableCores': 0,
          'pendingMem': 0,
          'pendingCores': 20,
          'pendingContainers': 10,
          'reservedMem': 0,
          'reservedCores': 10,
          'reservedContainers': 0
        }
      }
      console.log(result)
      return result
    }
  }
]
