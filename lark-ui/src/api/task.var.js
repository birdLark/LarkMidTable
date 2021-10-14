import request from '@/plugin/axios'
import qs from 'qs'

export function listVariables (pageIndex, pageSize = 20) {
  return request({
    url: 'param/vars',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { pageIndex, pageSize }
  })
}

export function addVariable (data) {
  console.log(data)
  return request({
    url: 'param/var',
    method: 'post',
    data: data
  })
}

export function editVariable (data) {
  console.log(data)
  return request({
    url: 'param/var',
    method: 'put',
    data: data
  })
}

export function delVariable (id) {
  console.log(id)
  return request({
    url: 'param/var',
    method: 'delete',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: qs.stringify({ id })
  })
}
