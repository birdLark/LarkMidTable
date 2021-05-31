import request from '@/plugin/axios'
import qs from 'qs'

export function listDash (pageIndex, pageSize = 20) {
  return request({
    url: 'visual/dashboards',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: qs.stringify({ pageIndex, pageSize })
  })
}

export function addDash (data) {
  console.log(data)
  return request({
    url: 'visual/dashboard',
    method: 'post',
    data: data
  })
}

export function delDash (id) {
  console.log(id)
  return request({
    url: 'visual/dashboard',
    method: 'delete',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: qs.stringify({ id })
  })
}

export function getDash (id) {
  console.log(id)
  return request({
    url: 'visual/dashboard',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { id }
  })
}

export function getDashNames () {
  return request({
    url: 'visual/dashboard/name',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
  })
}
