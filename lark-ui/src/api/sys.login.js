import request from '@/plugin/axios'
import qs from 'qs'

export function AccountLogin (data) {
  console.log(data)
  return request({
    url: 'system/login',
    method: 'post',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: qs.stringify(data)
  })
}
