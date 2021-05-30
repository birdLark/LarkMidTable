import request from '@/plugin/axios'
import qs from 'qs'

export function addChart (data) {
  console.log(data)
  return request({
    url: 'visual/chart',
    method: 'post',
    data: data
  })
}

export function delChart (id) {
  console.log(id)
  return request({
    url: 'visual/chart',
    method: 'delete',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: qs.stringify({ id })
  })
}

export function getChart (id) {
  console.log(id)
  return request({
    url: 'visual/chart',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { id }
  })
}

export function updateChartSize (id, w, h) {
  console.log(id)
  return request({
    url: 'visual/chart/size',
    method: 'put',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { id, w, h }
  })
}

export function updateChartLocation (id, x, y) {
  console.log(id)
  return request({
    url: 'visual/chart/location',
    method: 'put',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { id, x, y }
  })
}
