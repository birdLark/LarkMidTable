import request from '@/plugin/axios'
import qs from 'qs'

export function listSystemPrivileges (pageIndex, pageSize = 20) {
  return request({
    url: 'system/privileges',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { pageIndex, pageSize }
  })
}

export function addSystemPrivilege (data) {
  console.log(data)
  return request({
    url: 'system/privilege',
    method: 'post',
    data: data
  })
}

export function editSystemPrivilege (data) {
  console.log(data)
  return request({
    url: 'system/privilege',
    method: 'put',
    data: data
  })
}

export function delSystemPrivilege (id) {
  console.log(id)
  return request({
    url: 'system/privilege',
    method: 'delete',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: qs.stringify({ id })
  })
}

export function listAllTeams () {
  return request({
    url: 'system/team/name',
    method: 'get'
  })
}
