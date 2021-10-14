<template>
  <d2-container>
    <template slot="header"></template>
    <el-container>
      <el-aside width="200px" style="height:600px;">
        <el-form :model="chartInfo" ref="chartInfo">
            <el-form-item label="图表名称">
              <el-input v-model="chartInfo.name" placeholder="请输入图表名称"></el-input>
            </el-form-item>
            <el-form-item label="图表类型">
              <el-select v-model="chartSettings.type" style="margin-top:10px" placeholder="请选择图表类型">
                <el-option label="折线图" value="line"></el-option>
                <el-option label="柱状图" value="histogram"></el-option>
                <el-option label="饼图" value="pie"></el-option>
              </el-select>
            </el-form-item>
            <template>
            </template>
            <el-form-item label="维度">
              <el-select v-model="chartSettings.dimension" multiple v-show="chartDimensionArr[chartSettings.type]==='array'" style="margin-top:10px" placeholder="请选择维度">
                <el-option
                  v-for="item in chartData.columns"
                  :key="item"
                  :label="item"
                  :value="item">
                </el-option>
              </el-select>
              <el-select v-model="chartSettings.dimension" v-show="chartDimensionArr[chartSettings.type]==='string'" style="margin-top:10px" placeholder="请选择维度">
                <el-option
                  v-for="item in chartData.columns"
                  :key="item"
                  :label="item"
                  :value="item">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="指标">
              <el-select v-model="chartSettings.metrics" multiple v-show="chartMetricArr[chartSettings.type]==='array'"  style="margin-top:10px" placeholder="请选择指标">
                <el-option
                  v-for="item in chartData.columns"
                  :key="item"
                  :label="item"
                  :value="item">
                </el-option>
              </el-select>

              <el-select v-model="chartSettings.metrics" v-show="chartMetricArr[chartSettings.type]==='string'"  style="margin-top:10px" placeholder="请选择指标">
                <el-option
                  v-for="item in chartData.columns"
                  :key="item"
                  :label="item"
                  :value="item">
                </el-option>
              </el-select>
            </el-form-item>
            <!-- <el-form-item :key="domain.name"
                      :label="domain.name"
                      v-for="domain in chartSettingByType">
              <el-input  autocomplete="off"  v-model="domain.value">
              </el-input>
          </el-form-item> -->
        </el-form>
      </el-aside>
      <el-container>
        <el-header>
          <template>
            <el-select placeholder="请选择DashBoard" v-model="chartInfo.dashboardId" style="margin-left:20px;margin-right:10px">
              <el-option
                v-for="item in dashboards"
                :key="item.name"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </template>
          <el-button type="primary" @click="saveChart">保存</el-button>
        </el-header>
        <el-main style="font-size:20px">
          <el-card header="图表效果展示" shadow="always"  style="width:100%;height:600px;margin:20px">
          <template >
            <ve-chart :data="chartData" :settings="chartSettings"></ve-chart>
          </template>
        </el-card>
        </el-main>
        <!-- <el-card shadow="never" class="page_card" style="width：600px;height:600px">
            <ve-histogram :data="chartData" :judge-width="true"></ve-histogram>
                 <ve-chart :data="chartData" :settings="chartSettings" :judge-width="true" ></ve-chart>
              </el-card> -->
      </el-container>
    </el-container>
  </d2-container>
</template>

<script>

import { getChartData, getChartSetting } from '@/api/dashboard.draw.js'
import { addChart } from '@/api/dashboard.chart.js'
import { getDashNames } from '@/api/dashboard.dash.js'
export default {
  data () {
    return {
      chartInfo: {

      },
      chartData: {
        columns: ['日期', '访问用户', '下单用户', '下单率'],
        rows: [
          { '日期': '1/1', '访问用户': 1393, '下单用户': 1093, '下单率': 0.32 },
          { '日期': '1/2', '访问用户': 3530, '下单用户': 3230, '下单率': 0.26 },
          { '日期': '1/3', '访问用户': 2923, '下单用户': 2623, '下单率': 0.76 },
          { '日期': '1/4', '访问用户': 1723, '下单用户': 1423, '下单率': 0.49 },
          { '日期': '1/5', '访问用户': 3792, '下单用户': 3492, '下单率': 0.323 },
          { '日期': '1/6', '访问用户': 4593, '下单用户': 4293, '下单率': 0.78 }
        ]
      },
      chartSettings: {
        type: 'line'
      },
      chartSettingByType: [],
      chartDimensionArr: { 'line': 'array', 'pie': 'string', 'histogram': 'array' },
      chartMetricArr: { 'line': 'array', 'pie': 'string', 'histogram': 'array' },
      dashboards: [],
      sql: ''
    }
  },
  mounted () {
    // 加载完成后显示提示
    this.sql = this.$route.query.sql
    console.log(this.sql)
    getChartData(this.sql).then(res => {
      console.log(res)
      this.chartData = res
    })

    getDashNames().then(res => {
      this.dashboards = res
    })
  },
  methods: {
    getChartSettingWithType (value) {
      getChartSetting(value).then(res => {
        console.log(res)
        this.chartSettingByType = res
      })
    },
    saveChart () {
      this.chartSettings['sql'] = this.sql
      this.chartSettings['title'] = this.chartInfo.name

      this.chartInfo['widgetX'] = 0
      this.chartInfo['widgetY'] = 0
      this.chartInfo['widgetW'] = 8
      this.chartInfo['widgetH'] = 8
      this.chartInfo['chartType'] = this.chartSettings.type
      this.chartInfo['chartSpecific'] = this.chartSettings

      addChart(this.chartInfo).then(res => {
        console.log(res)
        // this.$router.replace('/chart?id=' + this.chartInfo.dashboardId)
      })
    }
  }
}
</script>
