<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from '../../mixins/resize'

export default {
  name:"line-s",
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
      let data = {
        year: [
          [24, 40, 101, 134, 90, 230, 210, 230, 120, 230, 210, 120],
          [40, 64, 191, 324, 290, 330, 310, 213, 180, 200, 180, 79],
        ],
        quarter: [
          [23, 75, 12, 97, 21, 67, 98, 21, 43, 64, 76, 38],
          [43, 31, 65, 23, 78, 21, 82, 64, 43, 60, 19, 34],
        ],
        month: [
          [34, 87, 32, 76, 98, 12, 32, 87, 39, 36, 29, 36],
          [56, 43, 98, 21, 56, 87, 43, 12, 43, 54, 12, 98],
        ],
        week: [
          [43, 73, 62, 54, 91, 54, 84, 43, 86, 43, 54, 53],
          [32, 54, 34, 87, 32, 45, 62, 68, 93, 54, 54, 24],
        ],
      };
        this.chart.setOption({
              //图标框配置
            grid: {
              top: "20%",
              left: "0%",
              right: "4%",
              bottom: "3%",
              //刻度信息
              containLabel: true,
              show: true,
              borderColor: "rgba(0, 240, 255, 0.3)",
            },
            // 工具提示
            tooltip: {
              trigger: "axis",
            },
            // 图例组件
            legend: {
              right: "10%",
              textStyle: {
                color: "#4c9bfd",
              },
            },
            xAxis: {
              type: "category",
              data: [
                "1月",
                "2月",
                "3月",
                "4月",
                "5月",
                "6月",
                "7月",
                "8月",
                "9月",
                "10月",
                "11月",
                "12月",
              ],
              // 剔除刻度
              axisTick: {
                show: false,
              },
              // 文字颜色
              axisLabel: {
                color: "#4c9bfd",
              },
              // 去除轴两端间距 留白
              boundaryGap: false,
            },
            yAxis: {
              type: "value",
              // 剔除刻度
              axisTick: {
                show: false,
              },
              // 文字颜色
              axisLabel: {
                color: "#4c9bfd",
              },
              // 分割线颜色
              splitLine: {
                lineStyle: {
                  color: "#012f4a",
                },
              },
            },
            series: [
              {
                name: "预期销售额",
                data: data.year[0],
                type: "line",
                smooth: true,

                itemStyle: {
                  color: "#00f2f1", // 线颜色
                },
              },
              {
                name: "实际销售额",
                data: data.year[1],
                type: "line",
                smooth: true,

                itemStyle: {
                  color: "#ed3f35", // 线颜色
                },
              },
            ],
        })
    }
  }
}
</script>
