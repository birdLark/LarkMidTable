<template>
  <d2-container>
    <template slot="header"></template>
    <el-row style="margin-bottom:20px">
      <el-button type="primary" @click="dialogFormVisible = true">添加业务线</el-button>
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
          prop="admin"
          label="管理员"
          width="180">
        </el-table-column>
        <el-table-column
          prop="basePath"
          label="HDFS地址">
        </el-table-column>
        <el-table-column
          prop="ns"
          label="NameSpace">
        </el-table-column>
        <el-table-column
          prop="baseQueue"
          label="队列">
        </el-table-column>
        <el-table-column
          prop="dsQuota"
          label="文件配额">
        </el-table-column>
        <el-table-column
          prop="nsQuota"
          label="空间配额">
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
            <el-button @click="editApp(scope.row)" type="text" size="small">编辑</el-button>
            <el-button @click="delApp(scope.row)" type="text" size="small">删除</el-button>
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

    <el-dialog title="业务线管理" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="业务线名称" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="HDFS命名空间" :label-width="formLabelWidth">
          <el-input v-model="form.ns" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="HDFS目录" :label-width="formLabelWidth">
          <el-input v-model="form.basePath" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="YARN队列" :label-width="formLabelWidth">
          <el-input v-model="form.baseQueue" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="空间配额(GB)" :label-width="formLabelWidth">
          <el-input v-model="form.dsQuota" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="文件数配额" :label-width="formLabelWidth">
          <el-input v-model="form.nsQuota" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" :label-width="formLabelWidth">
          <el-input v-model="form.detail" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addApp()">确 定</el-button>
      </div>
    </el-dialog>
  </d2-container>
</template>

<script>

import { listProjects, addProject, editProject, delProject } from '@/api/operation.project.js'

import { publicMethod } from '@/api/util.js'

export default {
  inject: ['reload'],
  methods: {
    editApp (row) {
      this.form = row
      this.dialogFormVisible = true
      console.log(row)
    },
    delApp (row) {
      console.log(row)
      delProject(row.id).then(res => {
        this.reload()
      })
    },
    addApp () {
      console.log(this.form)
      this.dialogFormVisible = false
      if (this.form.id) {
        console.log('edit')
        editProject(this.form).then(res => {
          this.reload()
        })
      } else {
        addProject(this.form).then(res => {
          this.reload()
        })
      }
      this.form = {}
      this.reload()
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
      listProjects(this.pageIndex, this.pageSize)
        .then(res => {
          console.log(res)
          this.tableData = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    },
    handlePrevPage: function () {
      listProjects(this.pageIndex - 1, this.pageSize)
        .then(res => {
          console.log(res)
          this.tableData = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    },
    handleNextPage: function () {
      listProjects(this.pageIndex + 1, this.pageSize)
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
    listProjects(this.pageIndex, this.pageSize)
      .then(res => {
        console.log(res)
        this.tableData = res.pages
        this.pageIndex = res.pageIndex
        this.pageSize = res.pageSize
        this.pageCount = res.pageCount
      })
  },
  data () {
    return {
      tableData: [],
      pageSize: 20,
      pageIndex: 1,
      pageCount: 10,
      dialogFormVisible: false,
      formLabelWidth: '120px',
      form: {
      }
    }
  }
}
</script>
