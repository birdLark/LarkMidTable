<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from '../../mixins/resize'

export default {
  name:"Category",
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
          tooltip: {
            trigger: "item", //轴出发axis>>图形item
            /*    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
              type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
              } */
          },
          grid: {
            top: "5%",
            left: "0%",
            right: "4%",
            bottom: "3%",
            //刻度信息
            containLabel: true,
            show: true,
            borderColor: "rgba(0, 240, 255, 0.3)",
          },
          xAxis: [
            {
              type: "category",
              data: [
                "上海",
                "广州",
                "北京",
                "深圳",
                "合肥",
                "",
                "......",
                "",
                "杭州",
                "厦门",
                "济南",
                "成都",
                "重庆",
              ],
              axisTick: {
                alignWithLabel: false,
                show: false,
              },
              axisLabel: {
                color: "#4c9bfd",
              },
            },
          ],
          yAxis: [
            {
              type: "value",
              axisTick: {
                show: false,
              },
              axisLabel: {
                color: "#4c96fd",
              },
              splitLine: {
                lineStyle: {
                  color: "rgba(0, 240, 255, 0.3)",
                },
              },
            },
          ],
          series: [
            {
              name: "直接访问",
              type: "bar",
              barWidth: "60%",
              itemStyle: {
                color: new echarts.graphic.LinearGradient(
                  // (x1,y2) 点到点 (x2,y2) 之间进行渐变
                  0,
                  0,
                  0,
                  1,
                  [
                    { offset: 0, color: "#00fffb" }, // 0 起始颜色
                    { offset: 1, color: "#0061ce" }, // 1 结束颜色
                  ]
                ),
              },
              data: [
                2100,
                1900,
                1700,
                1560,
                1400,
                item,
                item,
                item,
                900,
                750,
                600,
                480,
                240,
              ],
            },
          ],
        })
    }
  }
}
</script>
