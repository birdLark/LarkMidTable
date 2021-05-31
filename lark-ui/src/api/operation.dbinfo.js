import request from '@/plugin/axios'
import qs from 'qs'

export function listDbInfos (pageIndex, pageSize = 20) {
  return request({
    url: 'meta/dbs',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { pageIndex, pageSize }
  })
}

export function addDbInfo (data) {
  console.log(data)
  return request({
    url: 'meta/db',
    method: 'post',
    data: data
  })
}

export function editDbInfo (data) {
  console.log(data)
  return request({
    url: 'meta/db',
    method: 'put',
    data: data
  })
}

export function delDbInfo (id) {
  console.log(id)
  return request({
    url: 'meta/db',
    method: 'delete',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: qs.stringify({ id })
  })
}
