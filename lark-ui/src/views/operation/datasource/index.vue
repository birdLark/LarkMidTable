<template>
  <d2-container>
    <template slot="header"></template>
    <el-row style="margin-bottom:20px">
      <el-button type="primary" @click="dialogFormVisible = true">添加数据源</el-button>
    </el-row>
    <template>
      <el-table
        :data="tableData"
        border
        style="width: 100%">
        <el-table-column
          prop="id"
          label="序号"
          width="180">
        </el-table-column>
        <el-table-column
          prop="name"
          label="名称"
          width="180">
        </el-table-column>
        <el-table-column
          prop="sourceType"
          label="类型"
          width="180">
        </el-table-column>
        <el-table-column
          prop="admin"
          label="管理员">
        </el-table-column>
        <el-table-column
          prop="createTime"
          :formatter="dateFormat"
          label="创建时间">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button @click="editDs(scope.row)" type="text" size="small">编辑</el-button>
            <el-button @click="delDs(scope.row)" type="text" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>

    <el-pagination
      background
      layout="prev, pager, next"
      :page-count="pageCount" style="margin-top:20px"
      @current-change="handleCurrentChange"
      @prev-client="handlePrevPage"
      @next-client="handleNextPage">
    </el-pagination>

    <el-dialog title="数据源管理" :visible.sync="dialogFormVisible">
    <el-form :model="form">
      <el-form-item label="名称" :label-width="formLabelWidth">
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="业务线" :label-width="formLabelWidth">
        <el-select v-model="form.projectName" placeholder="请选择业务线">
          <el-option
        v-for="item in projects"
        :key="item"
        :label="item"
        :value="item">
      </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="数据源类型" :label-width="formLabelWidth">
        <el-select v-model="form.sourceType" placeholder="请选择数据源类型">
          <el-option label="Mysql" value="Mysql"></el-option>
          <el-option label="Ftp" value="Ftp"></el-option>
          <el-option label="Hive" value="Hive"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="链接详情" :label-width="formLabelWidth">
        <!-- 此处可根据不同的数据类型，展示不同的链接详情配置， 前端水平有限，后续持续更新，此处为json字符串 -->
        <el-input
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 8}"
          placeholder="请输入链接JSON"
          v-model="form.connectInfo">
        </el-input>
      </el-form-item>
    </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="addDs()">确 定</el-button>
  </div>
</el-dialog>
  </d2-container>
</template>

<script>
import { listDatasources, addDatasource, editDatasource, delDatasource } from '@/api/operation.datasource.js'

import { listAllProjects } from '@/api/operation.project.js'

import { publicMethod } from '@/api/util.js'

export default {
  inject: ['reload'],
  methods: {
    editDs (row) {
      this.form = row
      this.dialogFormVisible = true
      console.log(row)
    },
    delDs (row) {
      console.log(row)
      delDatasource(row.id).then(res => {
        this.reload()
      })
    },
    addDs () {
      console.log(this.form)
      this.dialogFormVisible = false
      if (this.form.id) {
        console.log('edit')
        editDatasource(this.form).then(res => {
          this.reload()
        })
      } else {
        addDatasource(this.form).then(res => {
          this.reload()
        })
      }
      this.form = {}
    },
    dateFormat: function (row, column) {
      var date = row[column.property]
      if (date === undefined) {
        return ''
      }
      return publicMethod.getTimestamp(date)
    },
    handleCurrentChange: function (currentPage) {
      this.pageIndex = currentPage
      console.log(this.pageIndex) // 点击第几页
      listDatasources(this.pageIndex, this.pageSize)
        .then(res => {
          console.log(res)
          this.tableData = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    },
    handlePrevPage: function () {
      listDatasources(this.pageIndex - 1, this.pageSize)
        .then(res => {
          console.log(res)
          this.tableData = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    },
    handleNextPage: function () {
      listDatasources(this.pageIndex + 2, this.pageSize)
        .then(res => {
          console.log(res)
          this.tableData = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    }
  },
  mounted () {
    listDatasources(this.pageIndex, this.pageSize)
      .then(res => {
        console.log(res)
        this.tableData = res.pages
        this.pageIndex = res.pageIndex
        this.pageSize = res.pageSize
        this.pageCount = res.pageCount
      })

    listAllProjects()
      .then(res => {
        console.log(res)
        this.projects = res
      })
  },
  data () {
    return {
      tableData: [{
        id: 1,
        name: 'mysql1',
        admin: 'admin',
        sourceType: 'JDBC',
        connectInfo: {},
        isPublic: true,
        createTime: 1491559642
      }, {
        id: 2,
        name: 'mysql2',
        admin: 'admin',
        sourceType: 'JDBC',
        connectInfo: {},
        isPublic: true,
        createTime: 1491559642
      }, {
        id: 3,
        name: 'mysql3',
        admin: 'admin',
        sourceType: 'JDBC',
        connectInfo: {},
        isPublic: true,
        createTime: 1491559642
      }, {
        id: 4,
        name: 'postgresql1',
        admin: 'admin',
        sourceType: 'JDBC',
        connectInfo: {},
        isPublic: true,
        createTime: 1491559642
      }],
      pageSize: 20,
      pageIndex: 1,
      pageCount: 10,
      dialogFormVisible: false,
      formLabelWidth: '120px',
      form: {
      },
      projects: []
    }
  }
}
</script>
