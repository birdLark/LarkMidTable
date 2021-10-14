import request from '@/plugin/axios'
import qs from 'qs'

export function ListSchemas () {
  return request({
    url: 'query/schemas',
    method: 'get'
  })
}

export function ListTables (database) {
  console.log(database)
  return request({
    url: 'query/tables',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { database }
  })
}

export function GetTableInfo (database, table) {
  return request({
    url: 'query/table',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { database, table }
  })
}

export function QuerySql (engine, sql, pageIndex, pageSize) {
  return request({
    url: 'query/sql',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    params: { engine, sql, pageIndex, pageSize }
  })
  // return dataC
}

export function SaveSql (name, sql) {
  return request({
    url: 'query/sql',
    method: 'post',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: qs.stringify({ name, sql })
  })
}

export function GetSaveSql () {
  return request({
    url: 'query/sqls',
    method: 'get',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: qs.stringify({})
  })
}
