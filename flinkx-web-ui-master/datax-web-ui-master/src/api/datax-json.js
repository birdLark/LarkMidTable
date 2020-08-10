import request from '@/utils/request'

// buildJobJson
export function buildJobJson(data) {
  return request({
    url: '/api/dataxJson/buildJson',
    method: 'post',
    data
  })
}
