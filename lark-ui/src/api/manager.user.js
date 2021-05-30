import request from '@/plugin/axios'
import qs from 'qs'

export function listSystemUsers (pageIndex, pageSize = 20) {
  return request({
    url: 'system/users',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { pageIndex, pageSize }
  })
}

export function addSystemUser (data) {
  console.log(data)
  return request({
    url: 'system/user',
    method: 'post',
    data: data
  })
}

export function editSystemUser (data) {
  console.log(data)
  return request({
    url: 'system/user',
    method: 'put',
    data: data
  })
}

export function delSystemUser (id) {
  console.log(id)
  return request({
    url: 'system/user',
    method: 'delete',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: qs.stringify({ id })
  })
}
