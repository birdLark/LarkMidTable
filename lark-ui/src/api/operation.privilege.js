import request from '@/plugin/axios'
import qs from 'qs'

export function listPrivileges (pageIndex, pageSize = 20) {
  return request({
    url: 'ranger/privileges',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { pageIndex, pageSize }
  })
}

export function addPrivilege (data) {
  console.log(data)
  return request({
    url: 'ranger/privilege',
    method: 'post',
    data: data
  })
}

export function editPrivilege (data) {
  console.log(data)
  return request({
    url: 'ranger/privilege',
    method: 'put',
    data: data
  })
}

export function delPrivilege (id) {
  console.log(id)
  return request({
    url: 'ranger/privilege',
    method: 'delete',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: qs.stringify({ id })
  })
}
