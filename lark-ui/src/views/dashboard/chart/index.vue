<template>
  <d2-container type="full" class="page">
    <d2-grid-layout
      v-bind="layout"
      @layout-updated="layoutUpdatedHandler">
      <d2-grid-item
        v-for="(item, index) in layout.layout"
        :key="index"
        v-bind="item"
        @resize="resizeHandler"
        @move="moveHandler"
        @resized="resizedHandler"
        @moved="movedHandler">
        <el-card shadow="never" class="page_card">
          <div slot="header" class="clearfix">
            <span>{{item.name}}</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="delChartInfo(item.i)">删除</el-button>
          </div>
          <ve-chart :data="item.chartData" :settings="item.chartSettings" :judge-width="true" :ref="item.i"></ve-chart>
        </el-card>
      </d2-grid-item>
    </d2-grid-layout>
  </d2-container>
</template>

<script>
import Vue from 'vue'
import { GridLayout, GridItem } from 'vue-grid-layout'
import { delChart, updateChartSize, updateChartLocation } from '@/api/dashboard.chart.js'
import { getDash } from '@/api/dashboard.dash.js'
Vue.component('d2-grid-layout', GridLayout)
Vue.component('d2-grid-item', GridItem)
export default {
  inject: ['reload'],
  data () {
    return {
      layout: {
        layout: [
          { 'x': 0,
            'y': 0,
            'w': 6,
            'h': 12,
            'i': '0',
            chartSettings: {
              dimension: ['日期'],
              metrics: ['访问用户', '下单用户'],
              area: true,
              type: 'line'
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
            } },
          { 'x': 6,
            'y': 0,
            'w': 6,
            'h': 12,
            'i': '1',
            chartSettings: {
              dimension: ['createTime'],
              metrics: ['dfsUsed', 'totalFiles'],
              area: false,
              type: 'line'
            },
            chartData: {
              columns: ['total', 'dfsUsed', 'totalBlocks', 'totalFiles', 'liveDataNodeNums', 'deadDataNodeNums', 'missingBlocks', 'volumeFailuresTotal', 'createTime'],
              rows: [
                { 'total': 100, 'dfsUsed': 40, 'totalBlocks': 100000, 'totalFiles': 200, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-20 10:20:20' },
                { 'total': 100, 'dfsUsed': 51, 'totalBlocks': 100000, 'totalFiles': 500, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-21 10:20:20' },
                { 'total': 100, 'dfsUsed': 55, 'totalBlocks': 100000, 'totalFiles': 1000, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-22 10:20:20' },
                { 'total': 100, 'dfsUsed': 60, 'totalBlocks': 100000, 'totalFiles': 800, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-23 10:20:20' },
                { 'total': 100, 'dfsUsed': 71, 'totalBlocks': 100000, 'totalFiles': 1000, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-24 10:20:20' },
                { 'total': 100, 'dfsUsed': 80, 'totalBlocks': 100000, 'totalFiles': 2000, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-25 10:20:20' }
              ]
            } },
          { 'x': 0,
            'y': 12,
            'w': 12,
            'h': 10,
            'i': '2',
            chartSettings: {
              dimension: ['日期'],
              metrics: ['访问用户', '下单用户'],
              type: 'histogram'
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
            } },
          { 'x': 0,
            'y': 24,
            'w': 12,
            'h': 10,
            'i': '3',
            chartSettings: {
              dimension: ['createTime'],
              metrics: ['dfsUsed', 'totalFiles'],
              type: 'bar'
            },
            chartData: {
              columns: ['total', 'dfsUsed', 'totalBlocks', 'totalFiles', 'liveDataNodeNums', 'deadDataNodeNums', 'missingBlocks', 'volumeFailuresTotal', 'createTime'],
              rows: [
                { 'total': 100, 'dfsUsed': 40, 'totalBlocks': 100000, 'totalFiles': 200, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-20 10:20:20' },
                { 'total': 100, 'dfsUsed': 51, 'totalBlocks': 100000, 'totalFiles': 500, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-21 10:20:20' },
                { 'total': 100, 'dfsUsed': 55, 'totalBlocks': 100000, 'totalFiles': 1000, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-22 10:20:20' },
                { 'total': 100, 'dfsUsed': 60, 'totalBlocks': 100000, 'totalFiles': 800, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-23 10:20:20' },
                { 'total': 100, 'dfsUsed': 71, 'totalBlocks': 100000, 'totalFiles': 1000, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-24 10:20:20' },
                { 'total': 100, 'dfsUsed': 80, 'totalBlocks': 100000, 'totalFiles': 2000, 'liveDataNodeNums': 1000, 'deadDataNodeNums': 0, 'missingBlocks': 0, 'volumeFailuresTotal': '0', 'createTime': '2020-01-25 10:20:20' }
              ]
            } }
        ],
        colNum: 12,
        rowHeight: 30,
        isDraggable: true,
        isResizable: true,
        isMirrored: false,
        verticalCompact: true,
        margin: [10, 10],
        useCssTransforms: true
      }
    }
  },
  mounted () {
    let id = this.$route.query.id
    getDash(id).then(res => {
      this.layout = res
    })
    // 加载完成后显示提示
    this.showInfo()
  },
  methods: {
    log (arg1 = 'log', ...logs) {
      if (logs.length === 0) {
        console.log(arg1)
      } else {
        console.group(arg1)
        logs.forEach(e => {
          console.log(e)
        })
        console.groupEnd()
      }
    },
    delChartInfo (id) {
      delChart(id).then(res => {
        console.log('del ok')
        this.reload()
      })
    },
    // 显示提示
    showInfo () {
      this.$notify({
        title: '提示',
        message: '你可以按住卡片拖拽改变位置；或者在每个卡片的右下角拖动，调整卡片大小'
      })
    },
    // 测试代码
    layoutUpdatedHandler (newLayout) {
      console.group('layoutUpdatedHandler')
      newLayout.forEach(e => {
        console.log(`{'x': ${e.x}, 'y': ${e.y}, 'w': ${e.w}, 'h': ${e.h}, 'i': '${e.i}'},`)
      })
      console.groupEnd()
    },
    resizeHandler (i, newH, newW) {
      this.log('resizeHandler', `i: ${i}, newH: ${newH}, newW: ${newW}`)
      this.$nextTick(_ => {
        this.$refs[`${i}`].echarts.resize()
      })
    },
    moveHandler (i, newX, newY) {
      this.log('moveHandler', `i: ${i}, newX: ${newX}, newY: ${newY}`)
    },
    resizedHandler (i, newH, newW, newHPx, newWPx) {
      updateChartSize(i, newW, newH).then(res => {
        this.log('resizedHandler', `i: ${i}, newH: ${newH}, newW: ${newW}, newHPx: ${newHPx}, newWPx: ${newWPx}`)
      })
    },
    movedHandler (i, newX, newY) {
      updateChartLocation(i, newX, newY).then(res => {
        this.log('movedHandler', `i: ${i}, newX: ${newX}, newY: ${newY}`)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.page {
  .vue-grid-layout {
    background-color: $color-bg;
    border-radius: 4px;
    margin: -10px;
    .page_card {
      height: 100%;
      @extend %unable-select;
    }
    .vue-resizable-handle {
      opacity: .3;
      &:hover{
        opacity: 1;
      }
    }
  }
}
</style>
