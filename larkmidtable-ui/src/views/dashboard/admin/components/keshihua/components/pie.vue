<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from '../../mixins/resize'

export default {
  name:"pie",
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '120px'
    },
    autoResize: {
      type: Boolean,
      default: true
    },
    // chartData: {
    //   type: Object || {},
    //   required: true
    // }
  },
  data() {
    return {
      chart: null,
      chartData:null
    }
  },
  watch: {
    // chartData: {
    //   deep: true,
    //   handler(val) {
    //     this.setOptions(val)
    //   }
    // }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      this.setOptions(this.chartData)
    },
    setOptions() {
        this.chart.setOption({
            tooltip: {
              trigger: "item",
              formatter: "{a} <br/>{b} : {c} ({d}%)",
            },
            series: [
              {
                name: "点位统计",
                type: "pie",
                radius: ["10%", "70%"],
                center: ["50%", "50%"],
                roseType: "radius",
                data: [
                  { value: 20, name: "云南" },
                  { value: 26, name: "北京" },
                  { value: 24, name: "山东" },
                  { value: 25, name: "河北" },
                  { value: 20, name: "江苏" },
                  { value: 25, name: "浙江" },
                  { value: 30, name: "四川" },
                  { value: 42, name: "湖北" },
                ],
                // 文字样式
                label: {
                  fontSize: 10,
                },
                // 引导线
                labelLine: {
                  // 连接图形
                  length: 8,
                  // 连接文字
                  length2: 10,
                },
              },
            ],
            color: [
              "#006cff",
              "#60cda0",
              "#ed8884",
              "#ff9f7f",
              "#0096ff",
              "#9fe6b8",
              "#32c5e9",
              "#1d9dff",
            ],
        })
    }
  }
}
</script>
