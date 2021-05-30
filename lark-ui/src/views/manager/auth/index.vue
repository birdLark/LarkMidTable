<template>
  <d2-container>
    <template slot="header"></template>
    <el-row style="margin-bottom:20px">
      <el-button type="primary" @click="dialogFormVisible = true">添加系统权限</el-button>
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
          prop="team"
          label="团队"
          width="180">
        </el-table-column>
        <el-table-column
          prop="privilegeType"
          label="权限类型"
          width="180">
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
            <el-button @click="editPrivilege(scope.row)" type="text" size="small">编辑</el-button>
            <el-button @click="delPrivilege(scope.row)" type="text" size="small">删除</el-button>
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

    <el-dialog title="系统权限管理" :visible.sync="dialogFormVisible">
  <el-form :model="form">
    <el-form-item label="团队" :label-width="formLabelWidth">
      <el-select v-model="form.team" placeholder="请选择团队">
        <el-option
      v-for="item in teams"
      :key="item"
      :label="item"
      :value="item">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="权限类型" :label-width="formLabelWidth">
      <el-select v-model="form.privilegeType" placeholder="请选择权限类型">
        <el-option label="系统管理" value="SYSTEM"></el-option>
        <el-option label="集群概览" value="MONITOR"></el-option>
        <el-option label="运营管理" value="PROJECT"></el-option>
        <el-option label="任务管理" value="TASK"></el-option>
        <el-option label="数据查询" value="QUERY"></el-option>
        <el-option label="数据可视化" value="VISUAL"></el-option>
      </el-select>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="addPrivilege()">确 定</el-button>
  </div>
</el-dialog>
  </d2-container>
</template>

<script>

import { listSystemPrivileges, addSystemPrivilege, editSystemPrivilege, delSystemPrivilege } from '@/api/manager.privilege.js'

import { listAllTeams } from '@/api/manager.team.js'

import { publicMethod } from '@/api/util.js'

export default {
  inject: ['reload'],
  methods: {
    editPrivilege (row) {
      this.form = row
      this.dialogFormVisible = true
      console.log(row)
    },
    delPrivilege (row) {
      console.log(row)
      delSystemPrivilege(row.id).then(res => {
        this.reload()
      })
    },
    addPrivilege () {
      console.log(this.form)
      this.dialogFormVisible = false
      if (this.form.id) {
        console.log('edit')
        editSystemPrivilege(this.form).then(res => {
          this.reload()
        })
      } else {
        addSystemPrivilege(this.form).then(res => {
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
      listSystemPrivileges(this.pageIndex, this.pageSize)
        .then(res => {
          console.log(res)
          this.tableData = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    },
    handlePrevPage: function () {
      listSystemPrivileges(this.pageIndex - 1, this.pageSize)
        .then(res => {
          console.log(res)
          this.tableData = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    },
    handleNextPage: function () {
      listSystemPrivileges(this.pageIndex + 1, this.pageSize)
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
    listSystemPrivileges(this.pageIndex, this.pageSize)
      .then(res => {
        console.log(res)
        this.tableData = res.pages
        this.pageIndex = res.pageIndex
        this.pageSize = res.pageSize
        this.pageCount = res.pageCount
      })

    listAllTeams().then(res => {
      console.log(res)
      this.teams = res
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
      },
      teams: []
    }
  }
}
</script>
