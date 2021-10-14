import request from '@/plugin/axios'
import qs from 'qs'

export function listWorkflows (pageIndex, pageSize = 20) {
  return request({
    url: 'task/workflows',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { pageIndex, pageSize }
  })
}

export function addWorkflow (data) {
  console.log(data)
  return request({
    url: 'task/workflow',
    method: 'post',
    data: data
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

export function delWorkflow (id) {
  console.log(id)
  return request({
    url: 'task/workflow',
    method: 'delete',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: qs.stringify({ id })
  })
}

export function execWorkflow (id) {
  console.log(id)
  return request({
    url: 'task/workflow/exec',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { id }
  })
}

export function schedWorkflow (id) {
  console.log(id)
  return request({
    url: 'task/workflow/schedule',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { id }
  })
}

export function getWorkflowLog (data) {
  console.log(data)
  return request({
    url: 'task/workflow/log',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: data
  })
}

export function getTaskNames () {
  return request({
    url: 'task/names',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
  })
}

export function getTaskJobNames (taskName) {
  console.log(taskName)
  return request({
    url: 'task/jobs',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { taskName }
  })
}

export function getTaskJobOutputs (taskName, jobName) {
  return request({
    url: 'task/job/output',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { taskName, jobName }
  })
}

export function getTaskGraph (id) {
  return request({
    url: 'task/workflow',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { id }
  })
}
