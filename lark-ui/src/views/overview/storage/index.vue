<template>
  <d2-container>
    <template slot="header"></template>
    <el-row :gutter="20">
      <el-col :span="6">
      <el-card class="box-card" header="总存储" shadow="always">
          {{ hdfs.total }}
      </el-card>
      </el-col>
      <el-col :span="6">
      <el-card class="box-card" header="已用存储"  shadow="always">
        <div class="text item">
          {{ hdfs.dfsUsed}}
        </div>
      </el-card>
      </el-col>
      <el-col :span="6">
      <el-card class="box-card"  header="文件数" shadow="always">
        <div class="text item">
          {{hdfs.totalFiles }}
        </div>
      </el-card>
      </el-col>
      <el-col :span="6">
      <el-card class="box-card" header="Block数" shadow="always">
        <div class="text item">
          {{ hdfs.totalBlocks}}
        </div>
      </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top:15px">
      <el-col :span="6">
      <el-card class="box-card" header="活跃节点"  shadow="always">
        <div class="text item">
          {{ hdfs.liveDataNodeNums }}
        </div>
      </el-card>
      </el-col>
      <el-col :span="6">
      <el-card class="box-card" header="死亡节点" shadow="always">
        <div class="text item">
          {{hdfs.deadDataNodeNums }}
        </div>
      </el-card>
      </el-col>
      <el-col :span="6">
      <el-card class="box-card"  header="丢失Block数"  shadow="always">
        <div class="text item">
          {{hdfs.missingBlocks }}
        </div>
      </el-card>
      </el-col>
      <el-col :span="6">
      <el-card class="box-card" header="坏盘数"   shadow="always">
        <div class="text item">
          {{hdfs. volumeFailuresTotal}}
        </div>
      </el-card>
      </el-col>
    </el-row>

    <el-row style="margin-top:15px">
      <el-card header="存储变化曲线"   shadow="always">
        <template>
          <ve-line :data="chartData" :settings="chartSettings"></ve-line>
        </template>
      </el-card>
    </el-row>

  </d2-container>
</template>

<script>
import { StorageInfo, StorageChart } from '@/api/overview.storage'

export default {
  name: 'storage',
  data () {
    return {
      chartSettings: {
        dimension: ['createTime'],
        metrics: ['totalBlocks', 'totalFiles'],
        area: false
      },
      chartData: {
        columns: ['total', 'dfsUsed', 'totalBlocks', 'totalFiles', 'liveDataNodeNums', 'deadDataNodeNums', 'missingBlocks', 'volumeFailuresTotal', 'createTime'],
        rows: [
          { 'total': 100, 'dfsUsed': 40, 'totalBlocks': 100000, 'totalFiles': 200, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-20 10:20:20' },
          { 'total': 100, 'dfsUsed': 51, 'totalBlocks': 100000, 'totalFiles': 500, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-21 10:20:20' },
          { 'total': 100, 'dfsUsed': 55, 'totalFiles': 100000, 'totalBlocks': 1000, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-22 10:20:20' },
          { 'total': 100, 'dfsUsed': 60, 'totalBlocks': 100000, 'totalFiles': 800, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-23 10:20:20' },
          { 'total': 100, 'dfsUsed': 71, 'totalBlocks': 100000, 'totalFiles': 1000, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-24 10:20:20' },
          { 'total': 100, 'dfsUsed': 80, 'totalBlocks': 100000, 'totalFiles': 2000, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-25 10:20:20' }
        ]
      },
      hdfs: { 'total': 10, 'dfsUsed': 5, 'totalBlocks': 1000, 'totalFiles': 20, 'liveDataNodeNums': 10, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0' }
    }
  },
  mounted () {
    StorageInfo()
      .then(res => {
        this.hdfs = res
        console.log(res)
      })
    StorageChart()
      .then(res => {
        console.log(res)
        // this.chartSettings = res.chartSettings
        this.chartData = res
      })
  }

}

</script>
