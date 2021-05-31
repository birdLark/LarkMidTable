<template>
  <d2-container>
    <template slot="header"></template>
    <el-row style="margin-bottom:20px">
      <el-button type="primary" @click="dialogFormVisible = true">新建变量</el-button>
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
          prop="varType"
          label="变量类型">
        </el-table-column>
        <el-table-column
          prop="initValue"
          label="初始值">
        </el-table-column>
        <el-table-column
          prop="currentValue"
          label="当前值">
        </el-table-column>
        <el-table-column
          prop="preValue"
          label="历史值">
        </el-table-column>
        <el-table-column
          prop="app.name"
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
          width="100">
          <template slot-scope="scope">
            <el-button @click="editVar(scope.row)" type="text" size="small">编辑</el-button>
            <el-button @click="delVar(scope.row)" type="text" size="small">删除</el-button>
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

    <el-dialog title="业务变量" :visible.sync="dialogFormVisible">
  <el-form :model="form">
    <el-form-item label="变量名称" :label-width="formLabelWidth">
      <el-input v-model="form.name" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="变量类型" :label-width="formLabelWidth">
      <el-select v-model="form.varType" placeholder="请选择变量类型">
        <el-option label="指标" value="Indicator"></el-option>
        <el-option label="累加器" value="Accumulator"></el-option>
        <el-option label="队列" value="Queue"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="数据类型" :label-width="formLabelWidth">
      <el-select v-model="form.paramType" filterable placeholder="请选择数据类型">
        <el-option label="INT" value="INT"></el-option>
        <el-option label="FLOAT" value="FLOAT"></el-option>
        <el-option label="STRING" value="STRING"></el-option>
        <el-option label="BOOLEAN" value="BOOLEAN"></el-option>
        <el-option label="ARRAY" value="ARRAY"></el-option>
        <el-option label="MAP" value="MAP"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="绑定Task" :label-width="formLabelWidth">
      <el-select v-model="form.bindTaskName" filterable placeholder="请选择Task" @change="getJobs">
        <el-option
          v-for="item in workflows"
          :key="item"
          :label="item"
          :value="item">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="绑定Job" :label-width="formLabelWidth">
      <el-select v-model="form.bindJobName" filterable placeholder="请选择Job" @change="getParams">
        <el-option
          v-for="item in jobs"
          :key="item"
          :label="item"
          :value="item">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="绑定Param" :label-width="formLabelWidth">
       <el-select v-model="form.bindJobName" filterable placeholder="请选择OutPut Params">
        <el-option
          v-for="item in params"
          :key="item"
          :label="item"
          :value="item">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="初始值" :label-width="formLabelWidth">
      <el-input v-model="form.initValue" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="当前值" :label-width="formLabelWidth">
      <el-input v-model="form.currentValue" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="描述" :label-width="formLabelWidth">
      <el-input v-model="form.varDesc" autocomplete="off"></el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="addVar()">确 定</el-button>
  </div>
</el-dialog>
  </d2-container>
</template>

<script>

import { listVariables, addVariable, editVariable, delVariable } from '@/api/task.var.js'

import { getTaskNames, getTaskJobNames, getTaskJobOutputs } from '@/api/task.workflow.js'
import { publicMethod } from '@/api/util.js'

export default {
  inject: ['reload'],
  methods: {
    editVar (row) {
      this.form = row
      this.dialogFormVisible = true
      console.log(row)
    },
    delVar (row) {
      console.log(row)
      delVariable(row.id)
      this.reload()
    },
    addVar () {
      console.log(this.form)
      this.dialogFormVisible = false
      if (this.form.id) {
        console.log('edit')
        editVariable(this.form)
      } else {
        addVariable(this.form)
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
      listVariables(this.pageIndex, this.pageSize)
        .then(res => {
          console.log(res)
          this.tableData = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    },
    handlePrevPage: function () {
      listVariables(this.pageIndex - 1, this.pageSize)
        .then(res => {
          console.log(res)
          this.tableData = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    },
    handleNextPage: function () {
      listVariables(this.pageIndex + 1, this.pageSize)
        .then(res => {
          console.log(res)
          this.tableData = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    },
    getJobs (taskName) {
      this.taskName = taskName
      getTaskJobNames(taskName).then(res => {
        this.jobs = res
      })
    },
    getParams (jobName) {
      getTaskJobOutputs(this.taskName, jobName).then(res => {
        this.params = res
      })
    }
  },
  mounted () {
    listVariables(this.pageIndex, this.pageSize)
      .then(res => {
        console.log(res)
        this.tableData = res.pages
        this.pageIndex = res.pageIndex
        this.pageSize = res.pageSize
        this.pageCount = res.pageCount
      })

    getTaskNames().then(res => {
      console.log(res)
      this.workflows = res
    })
  },
  data () {
    return {
      tableData: [{
        id: 1,
        name: 'var1',
        admin: 'admin',
        varType: 'Accumulator',
        initValue: '0',
        currentValue: '1',
        preValue: '0',
        app: {
          'name': 'XX业务线'
        },
        createTime: '2020-01-02 12:20:00'
      }, {
        id: 2,
        name: 'var2',
        admin: 'admin',
        varType: 'Accumulator',
        initValue: '0',
        currentValue: '1',
        preValue: '0',
        app: {
          'name': 'XX业务线'
        },
        createTime: '2020-01-05 12:20:00'
      }, {
        id: 3,
        name: 'var3',
        admin: 'admin',
        varType: 'Accumulator',
        initValue: '0',
        currentValue: '1',
        preValue: '0',
        app: {
          'name': 'XX业务线'
        },
        createTime: '2020-01-12 12:20:00'
      }, {
        id: 4,
        name: 'var4',
        admin: 'admin',
        varType: 'Accumulator',
        initValue: '0',
        currentValue: '1',
        preValue: '0',
        app: {
          'name': 'XX业务线'
        },
        createTime: '2020-02-02 12:20:00'
      }],
      pageSize: 20,
      pageIndex: 1,
      pageCount: 10,
      dialogFormVisible: false,
      formLabelWidth: '120px',
      form: {
      },
      workflows: [],
      jobs: [],
      params: [],
      taskName: ''
    }
  }
}
</script>
