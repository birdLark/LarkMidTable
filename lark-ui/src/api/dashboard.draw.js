import request from '@/plugin/axios'
import qs from 'qs'

export function getChartData (sql) {
  console.log(sql)
  return request({
    url: 'visual/chart/data',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { sql }
  })
}

export function getChartSetting (type) {
  return request({
    url: 'visual/chart/setting',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { type }
  })
}
