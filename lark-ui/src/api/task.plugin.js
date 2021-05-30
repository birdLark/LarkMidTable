import request from '@/plugin/axios'
import qs from 'qs'

export function listPluginPackages (pageIndex, pageSize = 20) {
  return request({
    url: 'task/plugins',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { pageIndex, pageSize }
  })
}

export function getPluginPackage (id) {
  return request({
    url: 'task/plugin',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { id }
  })
}

export function addPluginPackage (data) {
  console.log(data)
  return request({
    url: 'task/plugin',
    method: 'post',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: qs.stringify(data)
  })
}

// export function editPluginPackage (data) {
//   console.log(data)
//   return request({
//     url: 'task/plugin',
//     method: 'put',
//     data: data
//   })
// }

export function delPluginPackage (id) {
  console.log(id)
  return request({
    url: 'task/plugin',
    method: 'delete',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: qs.stringify({ id })
  })
}

export function uploadPlugin (data) {
  console.log(data)
  return request({
    url: 'task/plugin',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data: data
  })
}

export function getPluginNamesGroupBy () {
  return request({
    url: 'task/plugin/name',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
  })
}
