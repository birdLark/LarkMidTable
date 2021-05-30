import request from '@/plugin/axios'

export function StorageInfo () {
  return request({
    url: 'monitor/storage',
    method: 'get'
  })
}

export function StorageChart () {
  return request({
    url: 'monitor/storage/chart',
    method: 'get'
  })
}
