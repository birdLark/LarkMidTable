import request from '@/plugin/axios'
import qs from 'qs'

export function listDatasources (pageIndex, pageSize = 20) {
  return request({
    url: 'meta/datasources',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { pageIndex, pageSize }
  })
}

export function addDatasource (data) {
  console.log(data)
  return request({
    url: 'meta/datasource',
    method: 'post',
    data: data
  })
}

export function editDatasource (data) {
  console.log(data)
  return request({
    url: 'meta/datasource',
    method: 'put',
    data: data
  })
}

export function delDatasource (id) {
  console.log(id)
  return request({
    url: 'meta/datasource',
    method: 'delete',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: qs.stringify({ id })
  })
}
