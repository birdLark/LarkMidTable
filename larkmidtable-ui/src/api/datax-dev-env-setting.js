import request from '@/utils/request'

// project

export function list(params) {
  return request({
    url: '/api/devEnvSetting',
    method: 'get',
    params
  })
}

export function updated(data) {
  return request({
    url: '/api/devEnvSetting',
    method: 'put',
    data
  })
}

export function created(data) {
  return request({
    url: '/api/devEnvSetting',
    method: 'post',
    data
  })
}

export function deleted(data) {
  return request({
    url: '/api/devEnvSetting',
    method: 'delete',
    params: data
  })
}

export function getDevEnvSettingList(params) {
  return request({
    url: 'api/devEnvSetting/list',
    method: 'get',
    params
  })
}

