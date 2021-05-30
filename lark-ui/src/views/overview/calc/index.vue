<template>
  <d2-container>
    <template slot="header"></template>
    <el-row :gutter="20">
      <el-col :span="6">
      <el-card class="box-card" header="Running任务" shadow="always">
          {{ yarn.runningApps }}
      </el-card>
      </el-col>
      <el-col :span="6">
      <el-card class="box-card" header="Pending任务"  shadow="always">
        <div class="text item">
          {{ yarn.pendingApps}}
        </div>
      </el-card>
      </el-col>
      <el-col :span="6">
      <el-card class="box-card"  header="总任务" shadow="always">
        <div class="text item">
          {{yarn.submittedApps }}
        </div>
      </el-card>
      </el-col>
      <el-col :span="6">
      <el-card class="box-card" header="Failed任务" shadow="always">
        <div class="text item">
          {{ yarn.failedApps}}
        </div>
      </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top:15px">
      <el-col :span="6">
      <el-card class="box-card" header="活跃节点"  shadow="always">
        <div class="text item">
          {{ yarn.liveNodeManagerNums }}
        </div>
      </el-card>
      </el-col>
      <el-col :span="6">
      <el-card class="box-card" header="死亡节点" shadow="always">
        <div class="text item">
          {{yarn.deadNodeManagerNums }}
        </div>
      </el-card>
      </el-col>
      <el-col :span="6">
      <el-card class="box-card"  header="不健康节点"  shadow="always">
        <div class="text item">
          {{yarn.unhealthyNodeManagerNums }}
        </div>
      </el-card>
      </el-col>
      <el-col :span="6">
      <el-card class="box-card" header="kill任务"   shadow="always">
        <div class="text item">
          {{yarn.killedApps}}
        </div>
      </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top:15px">
      <el-col :span="6">
      <el-card class="box-card" header="可用Mem"  shadow="always">
        <div class="text item">
          {{ yarn.availableMem }}
        </div>
      </el-card>
      </el-col>
      <el-col :span="6">
      <el-card class="box-card" header="可用Vcore" shadow="always">
        <div class="text item">
          {{yarn.availableCores }}
        </div>
      </el-card>
      </el-col>
      <el-col :span="6">
      <el-card class="box-card"  header="分配Mem"  shadow="always">
        <div class="text item">
          {{yarn.allocatedMem }}
        </div>
      </el-card>
      </el-col>
      <el-col :span="6">
      <el-card class="box-card" header="分配Vcore"   shadow="always">
        <div class="text item">
          {{yarn.allocatedCores}}
        </div>
      </el-card>
      </el-col>
    </el-row>

    <el-row style="margin-top:15px">
      <el-card header="队列状态柱状图"   shadow="always">
        <template>
          <ve-histogram :data="chartData" :settings="chartSettings"></ve-histogram>
        </template>
      </el-card>
    </el-row>
  </d2-container>
</template>

<script>
import { CalcInfo, QueueChart } from '@/api/overview.calc'

export default {
  name: 'calc',
  data () {
    return {
      chartSettings: {
        dimension: ['queueName'],
        metrics: ['appsPending', 'appsRunning']
      },
      chartData: {
        columns: ['queueName', 'appsPending', 'appsRunging', 'allocatedMB', 'availableMB', 'ActiveUsers'],
        rows: [
          {
            'queueName': 'queue1',
            'appsPending': 1,
            'appsRunging': 3,
            'allocatedMB': 100,
            'availableMB': 20,
            'ActiveUsers': 2
          },
          {
            'queueName': 'queue2',
            'appsPending': 2,
            'appsRunging': 4,
            'allocatedMB': 80,
            'availableMB': 10,
            'ActiveUsers': 1
          },
          {
            'queueName': 'queue3',
            'appsPending': 2,
            'appsRunging': 3,
            'allocatedMB': 150,
            'availableMB': 80,
            'ActiveUsers': 6
          }
        ]
      },
      yarn: { 'liveNodeManagerNums': 10,
        'deadNodeManagerNums': 0,
        'unhealthyNodeManagerNums': 0,
        'submittedApps': 20,
        'runningApps': 10,
        'pendingApps': 0,
        'completedApps': 10,
        'killedApps': 0,
        'failedApps': 0,
        'allocatedMem': 20,
        'allocatedCores': 10,
        'allocatedContainers': 0,
        'availableMem': 10,
        'availableCores': 0,
        'pendingMem': 0,
        'pendingCores': 20,
        'pendingContainers': 10,
        'reservedMem': 0,
        'reservedCores': 10,
        'reservedContainers': 0
      }
    }
  },
  mounted () {
    CalcInfo()
      .then(res => {
        this.yarn = res
      })
    QueueChart()
      .then(res => {
        console.log(res)
        // this.chartSettings = res.chartSettings
        this.chartData = res
      })
  }

}
</script>
