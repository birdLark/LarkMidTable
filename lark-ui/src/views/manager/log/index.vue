<template>
  <d2-container>
    <template slot="header"></template>
    <div class="block">
       <el-timeline>
        <el-timeline-item
          v-for="(operation, index) in logs"
          :key="index"
          :timestamp="dateFormat(operation.createTime)">
          {{operation.user}} 执行了 {{operation.operation}} 操作对象是 {{operation.obj}}
        </el-timeline-item>
      </el-timeline>
    </div>

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
import { LogInfo } from '@/api/manager.log'
import { publicMethod } from '@/api/util.js'
export default {
  inject: ['reload'],
  methods: {
    dateFormat: function (date) {
      if (date === undefined) {
        return ''
      }
      return publicMethod.getTimestamp(date)
    },
    handleCurrentChange: function (currentPage) {
      this.pageIndex = currentPage
      console.log(this.pageIndex) // 点击第几页
      LogInfo(this.pageIndex, this.pageSize)
        .then(res => {
          console.log(res)
          this.logs = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    },
    handlePrevPage: function () {
      LogInfo(this.pageIndex - 1, this.pageSize)
        .then(res => {
          console.log(res)
          this.logs = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    },
    handleNextPage: function () {
      LogInfo(this.pageIndex + 1, this.pageSize)
        .then(res => {
          console.log(res)
          this.logs = res.pages
          this.pageIndex = res.pageIndex
          this.pageSize = res.pageSize
          this.pageCount = res.pageCount
        })
    }
  },
  data () {
    return {
      'logs': [{ 'createTime': 'sfds', 'operation': 'sfd' }],
      pageIndex: 1,
      pageSize: 20,
      pageCount: 10
    }
  },
  mounted () {
    LogInfo(this.pageIndex, this.pageSize)
      .then(res => {
        console.log(res)
        this.logs = res.pages
        this.pageIndex = res.pageIndex
        this.pageSize = res.pageSize
        this.pageCount = res.pageCount
      })
  }

}
</script>
