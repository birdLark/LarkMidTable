import request from '@/plugin/axios'
import qs from 'qs'

export function listProjects (pageIndex, pageSize = 20) {
  return request({
    url: 'meta/projects',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { pageIndex, pageSize }
  })
}

export function addProject (data) {
  console.log(data)
  return request({
    url: 'meta/project',
    method: 'post',
    data: data
  })
}

export function editProject (data) {
  console.log(data)
  return request({
    url: 'meta/project',
    method: 'put',
    data: data
  })
}

export function delProject (id) {
  console.log(id)
  return request({
    url: 'meta/project',
    method: 'delete',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: qs.stringify({ id })
  })
}

export function listAllProjects () {
  return request({
    url: 'meta/project/name',
    method: 'get'
  })
}
