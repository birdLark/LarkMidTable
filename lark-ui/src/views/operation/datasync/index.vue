<template>
  <d2-container>
    <template slot="header"></template>
    <el-row style="margin-bottom:20px">
      <el-button type="primary">添加数据同步</el-button>
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
          label="调度">
        </el-table-column>
        <el-table-column
          prop="scheduleCron"
          label="调度时间">
        </el-table-column>
        <el-table-column
          prop="app.name"
          label="业务名称">
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="150">
          <template slot-scope="scope">
            <el-button @click="editApp(scope.row)" type="text" size="small">执行</el-button>
            <el-button @click="editApp(scope.row)" type="text" size="small">调度</el-button>
            <el-button @click="delApp(scope.row)" type="text" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

    </template>

    <el-pagination
      background
      layout="prev, pager, next"
      :total="pageCount" style="margin-top:20px">
    </el-pagination>
  </d2-container>
</template>

<script>
export default {
  methods: {
    editApp (row) {
      console.log(row)
    },
    delApp (row) {
      console.log(row)
    }
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
      pageCount: 10
    }
  }
}
</script>
