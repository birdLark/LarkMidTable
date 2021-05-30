<template>
  <d2-container>
    <template slot="header"></template>
    <el-row style="margin-bottom:20px">
      <el-button type="primary" @click="createWorkFlow()">新建流程</el-button>
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
          prop="azId"
          label="AzkabanId"
          width="180">
        </el-table-column>
        <el-table-column
          prop="scheduled"
          :formatter="formatBoolean"
          label="调度">
        </el-table-column>
        <el-table-column
          prop="scheduleCron"
          label="调度时间">
        </el-table-column>
        <el-table-column
          prop="projectName"
          label="业务名称">
        </el-table-column>
        <el-table-column
          prop="createTime"
          :formatter="dateFormat"
          label="创建时间">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="200">
          <template slot-scope="scope">
            <el-button @click="execWorkFlow(scope.row)" type="text" size="small">执行</el-button>
            <el-button @click="scheduledWorkFlow(scope.row)" type="text" size="small">调度</el-button>
            <el-button @click="editWorkFlow(scope.row)" type="text" size="small">编辑</el-button>
            <el-button @click="delWorkFlow(scope.row)" type="text" size="small">删除</el-button>
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
  </d2-container>
</template>

<script>
import { listWorkflows, execWorkflow, schedWorkflow, delWorkflow } from '@/api/task.workflow.js'

import { publicMethod } from '@/api/util.js'

export default {
  inject: ['reload'],
  methods: {
    execWorkFlow (row) {
      console.log(row)
      execWorkflow(row.id)
    },
    scheduledWorkFlow (row) {
      console.log(row)
      schedWorkflow(row.id)
    },
    editWorkFlow (row) {
      this.$router.replace('/easyflow?id=' + row.id)
    },
    delWorkFlow (row) {
      console.log(row)
      delWorkflow(row.id)
      this.reload()
    },
    createWorkFlow () {
      this.$router.replace('/easyflow')
    },
    formatBoolean: function (row, column, cellValue) {
      var ret = '' // 你想在页面展示的值
      if (cellValue) {
        ret = '是' // 根据自己的需求设定
      } else {
        ret = '否'
      }
      return ret
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
      listWorkflows(this.pageIndex, this.pageSize)
        .then(res => {
          console.log(res)
          this.tableData = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    },
    handlePrevPage: function () {
      listWorkflows(this.pageIndex - 1, this.pageSize)
        .then(res => {
          console.log(res)
          this.tableData = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    },
    handleNextPage: function () {
      listWorkflows(this.pageIndex + 2, this.pageSize)
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
    listWorkflows(this.pageIndex, this.pageSize)
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
      tableData: [{
        id: 1,
        name: '数据处理流程1',
        admin: 'admin',
        azId: '1',
        scheduled: false,
        scheduleCron: '',
        app: {
          'name': '数据中心业务线'
        },
        createTime: '2020-01-02 12:20:00'
      }, {
        id: 2,
        name: '金融业务线流程',
        admin: 'admin',
        azId: '1',
        scheduled: false,
        scheduleCron: '0 0/10 0 0 0 ？',
        app: {
          'name': '金融业务线'
        },
        createTime: '2020-01-05 12:20:00'
      }, {
        id: 3,
        name: '蚂蚁业务线流程',
        admin: 'admin',
        azId: '1',
        scheduled: false,
        scheduleCron: '0 0/1 0 0 0 ？',
        app: {
          'name': '蚂蚁业务线'
        },
        createTime: '2020-01-12 12:20:00'
      }, {
        id: 4,
        name: '短临业务线流程',
        azId: '1',
        scheduled: false,
        scheduleCron: '0 0 0 0 0 ？',
        app: {
          'name': '短临业务线'
        },
        createTime: '2020-02-02 12:20:00'
      }],
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
