import request from '@/plugin/axios'

export function CalcInfo () {
  return request({
    url: 'monitor/calc',
    method: 'get'
  })
}

export function CalcChart () {
  console.log('获取图表')
  return request({
    url: 'monitor/calc/chart',
    method: 'get'
  })
}

export function QueueChart () {
  console.log('获取图表')
  return request({
    url: 'monitor/calc/queue',
    method: 'get'
  })
}
