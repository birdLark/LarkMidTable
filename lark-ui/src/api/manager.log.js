import request from '@/plugin/axios'

export function LogInfo (pageIndex, pageSize) {
  return request({
    url: 'system/log',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { pageIndex, pageSize }
  })
}
