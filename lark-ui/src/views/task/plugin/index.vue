<template>
  <d2-container>
    <template slot="header"></template>
    <el-row style="margin-bottom:20px">
      <el-button type="primary"  @click="dialogFormVisible = true">新增插件</el-button>
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
          prop="version"
          label="版本"
          width="180">
        </el-table-column>
        <el-table-column
          prop="admin"
          label="管理员"
          width="180">
        </el-table-column>
        <el-table-column
          prop="pluginType"
          label="类型">
        </el-table-column>
        <el-table-column
          prop="pluginCategory"
          label="分类">
        </el-table-column>
        <el-table-column
          prop="lang"
          label="语言">
        </el-table-column>
        <el-table-column
          prop="pluginStatus"
          label="状态">
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
            <!-- <el-button @click="editPlugin(scope.row)" type="text" size="small">编辑</el-button> -->
            <el-button @click="delPlugin(scope.row)" type="text" size="small">卸载</el-button>
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

    <el-dialog title="插件管理" :visible.sync="dialogFormVisible">
      <el-form :model="form" v-loading="loading">
        <el-form-item label="插件名称" :label-width="formLabelWidth">
          <el-input v-model="form.pkgName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="插件版本" :label-width="formLabelWidth">
          <el-input v-model="form.pkgVersion" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth">
          <el-select v-model="form.status" placeholder="插件状态">
            <el-option label="开发状态" value="Dev"></el-option>
            <el-option label="迭代状态" value="Release"></el-option>
          </el-select>
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
        <el-form-item label="描述" :label-width="formLabelWidth">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="标签" :label-width="formLabelWidth">
          <el-input v-model="form.tags" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="分类" :label-width="formLabelWidth">
          <el-select v-model="form.category" placeholder="请选择分组">
            <el-option label="通用" value="Basic"></el-option>
            <el-option label="领域" value="Domain"></el-option>
            <el-option label="业务线" value="Project"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="文件" :label-width="formLabelWidth">
          <input type="file" ref="myfile">
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addPlugin()">确 定</el-button>
      </div>
    </el-dialog>
  </d2-container>
</template>

<script>
import { listPluginPackages, delPluginPackage, uploadPlugin } from '@/api/task.plugin.js'

import { listAllProjects } from '@/api/operation.project.js'

import { publicMethod } from '@/api/util.js'

export default {
  inject: ['reload'],
  methods: {
    editPlugin (row) {
      this.form = row
      this.dialogFormVisible = true
      console.log(row)
    },
    delPlugin (row) {
      console.log(row)
      delPluginPackage(row.id)
      this.reload()
    },
    addPlugin () {
      this.loading = true
      // if (this.form.id) {
      //   console.log('edit')
      //   editPluginPackage(this.form)
      // } else {
      // addPluginPackage(this.form)
      // }

      let myfile = this.$refs.myfile
      let files = myfile.files
      let file = files[0]

      console.log(file)

      let formData = new FormData()
      formData.append('file', file)
      formData.append('pkgName', this.form['pkgName'])
      formData.append('pkgVersion', this.form['pkgVersion'])
      formData.append('status', this.form['status'])
      formData.append('projectName', this.form['projectName'])
      formData.append('description', this.form['description'])
      formData.append('tags', this.form['tags'])
      formData.append('category', this.form['category'])

      uploadPlugin(formData)
      this.dialogFormVisible = false
      this.form = {}
      this.loading = false
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
      listPluginPackages(this.pageIndex, this.pageSize)
        .then(res => {
          console.log(res)
          this.tableData = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    },
    handlePrevPage: function () {
      listPluginPackages(this.pageIndex - 1, this.pageSize)
        .then(res => {
          console.log(res)
          this.tableData = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    },
    handleNextPage: function () {
      listPluginPackages(this.pageIndex + 1, this.pageSize)
        .then(res => {
          console.log(res)
          this.tableData = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    },
    getHeader () {
      return { 'Content-Type': 'multipart/form-data' }
    }
  },
  mounted () {
    listPluginPackages(this.pageIndex, this.pageSize)
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
        name: '数据传输插件',
        admin: 'admin',
        version: '1.0',
        pluginType: 'transform',
        pluginCategory: 'Basic',
        lang: 'Python',
        pluginStatus: 'dev',
        createTime: '2020-01-02 12:20:00'
      }, {
        id: 2,
        name: 'XX业务处理插件',
        admin: 'admin',
        version: '1.0',
        pluginType: 'calc',
        pluginCategory: 'Basic',
        lang: 'Java',
        pluginStatus: 'dev',
        createTime: '2020-01-05 12:20:00'
      }, {
        id: 3,
        name: '数字工厂数据清理插件',
        admin: 'admin',
        version: '1.0',
        pluginType: 'calc',
        pluginCategory: 'Basic',
        lang: 'Python',
        pluginStatus: 'dev',
        createTime: '2020-01-12 12:20:00'
      }, {
        id: 4,
        name: '数据传输插件',
        admin: 'admin',
        version: '2.0',
        pluginType: 'transform',
        pluginCategory: 'Basic',
        lang: 'Python',
        pluginStatus: 'dev',
        createTime: '2020-02-02 12:20:00'
      }],
      pageSize: 20,
      pageIndex: 1,
      pageCount: 10,
      dialogFormVisible: false,
      formLabelWidth: '120px',
      form: {
      },
      projects: [],
      loading: false,
      param: {}
    }
  }
}
</script>
