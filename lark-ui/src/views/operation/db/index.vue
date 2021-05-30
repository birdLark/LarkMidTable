<template>
  <d2-container>
    <template slot="header"></template>
    <el-row style="margin-bottom:20px">
      <el-button type="primary" @click="dialogFormVisible = true">添加数仓库</el-button>
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
          prop="level"
          label="类型"
          width="180">
        </el-table-column>
        <el-table-column
          prop="projectName"
          label="业务线"
          width="180">
        </el-table-column>
        <el-table-column
          prop="locationUri"
          label="Location"
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
            <el-button @click="editDb(scope.row)" type="text" size="small">编辑</el-button>
            <el-button @click="delDb(scope.row)" type="text" size="small">删除</el-button>
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

    <el-dialog title="数仓库管理" :visible.sync="dialogFormVisible">
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
    <el-form-item label="数据分层" :label-width="formLabelWidth">
      <el-select v-model="form.level" placeholder="请选择数据源类型">
        <el-option label="STG" value="STG"></el-option>
        <el-option label="ODS" value="ODS"></el-option>
        <el-option label="DWD" value="DWD"></el-option>
        <el-option label="DWS" value="DWS"></el-option>
        <el-option label="DIM" value="DIM"></el-option>
        <el-option label="DM" value="DM"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="详情" :label-width="formLabelWidth">
      <el-input v-model="form.detail" autocomplete="off"></el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="addDb()">确 定</el-button>
  </div>
</el-dialog>
  </d2-container>
</template>

<script>
import { listDbInfos, addDbInfo, editDbInfo, delDbInfo } from '@/api/operation.dbinfo.js'

import { listAllProjects } from '@/api/operation.project.js'

import { publicMethod } from '@/api/util.js'

export default {
  inject: ['reload'],
  methods: {
    editDb (row) {
      this.form = row
      this.dialogFormVisible = true
      console.log(row)
    },
    delDb (row) {
      console.log(row)
      delDbInfo(row.id).then(res => {
        this.reload()
      })
    },
    addDb () {
      console.log(this.form)
      this.dialogFormVisible = false
      if (this.form.id) {
        console.log('edit')
        editDbInfo(this.form).then(res => {
          this.reload()
        })
      } else {
        addDbInfo(this.form).then(res => {
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
      listDbInfos(this.pageIndex, this.pageSize)
        .then(res => {
          console.log(res)
          this.tableData = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    },
    handlePrevPage: function () {
      listDbInfos(this.pageIndex - 1, this.pageSize)
        .then(res => {
          console.log(res)
          this.tableData = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    },
    handleNextPage: function () {
      listDbInfos(this.pageIndex + 1, this.pageSize)
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
    listDbInfos(this.pageIndex, this.pageSize)
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
