<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from '../../mixins/resize'

export default {
  name:"gauge",
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
      default: '90px'
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
          let item = {
            value: 1200,
            tooltip: { extraCssText: "opacity:0" },
            itemStyle: {
              color: "#254065",
            },
            emphasis: {
              itemStyle: {
                color: "#254065",
              },
            },
          };
        this.chart.setOption({
              series: [
                {
                  name: "销售进度",
                  type: "pie",
                  radius: ["130%", "150%"],
                  center: ["48%", "80%"],
                  label: {
                    show: false,
                  },
                  //起始角度
                  startAngle: 180,
                  hoverOffset: 0,
                  data: [
                    {
                      value: 100,
                      //渐变颜色
                      itemStyle: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                          { offset: 0, color: "#00fffb" },
                          { offset: 1, color: "#0061ce" },
                        ]),
                      },
                    },
                    { value: 100, itemStyle: { color: "#12274d" } },
                    { value: 200, itemStyle: { color: "transparent" } },
                  ],
                },
              ],
        })
    }
  }
}
</script>
