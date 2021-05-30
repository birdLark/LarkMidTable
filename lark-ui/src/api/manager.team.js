import request from '@/plugin/axios'
import qs from 'qs'

export function listSystemTeams (pageIndex, pageSize = 20) {
  return request({
    url: 'system/teams',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { pageIndex, pageSize }
  })
}

export function addSystemTeam (data) {
  console.log(data)
  return request({
    url: 'system/team',
    method: 'post',
    data: data
  })
}

export function editSystemTeam (data) {
  console.log(data)
  return request({
    url: 'system/team',
    method: 'put',
    data: data
  })
}

export function delSystemTeam (id) {
  console.log(id)
  return request({
    url: 'system/team',
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
