import $ from './jquery.min.js'
(function () {
  //封装html尺寸匹配函数
  function setfz() {
    //获取html
    let html = document.documentElement;
    // let html = document.documentElement;
    //获取html的宽
    let width = html.clientWidth;
    // let width = html.clientWidth;
    //判断屏显
    // if (width < 1024) width = 1024;
    // if (width > 1920) width = 1920;
    if (width < 1024) width = 1024;
    if (width > 1920) width = 1920;
    //80比例缩小
    // let fontSize = width / 80 + 'px';
    let fontsize = width / 80 + "px";
    //赋值给html
    // html.style.fontSize = fontSize;
    html.style.fontSize = fontsize;
  }
  // setfz()
  setfz();
  //尺寸改变事件
  /*  window.onresize = function () {
    setfz();
  } */
  window.onresize = function () {
    setfz();
  };
})();
//监控
(function () {
  $(".monitor").on("click", ".tabs a", function () {
    $(this).addClass("active").siblings().removeClass("active");
    //四种方法------》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》重点看四种方法
    // let index = $(this).index();
    // let index = $(this).data('index');
    let index = $(this).attr("data-index");
    //H5:this.dataset.index;
    //index对应显示值
    $(".monitor .content").eq(index).show().siblings(".content").hide();
  });
  //克隆数据---达到数据循环显示
  $(".monitor .marquee").each(function (index, elm) {
    let rows = $(elm).children().clone();
    $(this).append(rows);
  });
})();

// 点位区域-饼图
(function () {
  //实例化需dom对象>>>>>>>>>>>>>>>>>>>>>>>>>$('.pie')[0]
  let mychart = echarts.init($(".pie")[0]);
  // let myechart = echarts.init(document.getElementsByClassName('.pie'));
  let option = {
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
  };
  mychart.setOption(option);
})();

//柱形
(function () {
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
  let option = {
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
  };
  let mychart = echarts.init($(".bar")[0]);
  mychart.setOption(option);
})();

//订单区域
(function () {
  let data = {
    day365: { orders: "20,301,987", amount: "99834" },
    day90: { orders: "301,987", amount: "9834" },
    day30: { orders: "1,987", amount: "3834" },
    day1: { orders: "987", amount: "834" },
  };
  $(".order").on("click", ".filter a", function () {
    $(this).addClass("active").siblings().removeClass("active");

    let key = $(this).attr("data-key");
    let current = data[key];

    $(".order h4:eq(0)").html(current.orders);
    $(".order h4:eq(1)").html(current.amount);
  });
  //自动切换
  let index = 0;
  let timer = window.setInterval(function () {
    index++;
    if (index >= 4) index = 0;
    $(".order .filter a").eq(index).click();
  }, 1500);

  $(".order").on("mouseenter", function () {
    clearInterval(timer);
  })
    .on("mouseleave", function () {
      timer = window.setInterval(function () {
        index++;
        if (index >= 4) index = 0;
        $(".order .filter a").eq(index).click();
      }, 1500);
    });
})();
//销售统计
(function () {
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
  let option = {
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
  };
  let mychart = echarts.init($(".chart .line")[0]);
  mychart.setOption(option);
  //切换功能
  $(".sales").on("click", ".caption a", function () {
    $(this).addClass("active").siblings().removeClass("active");

    let key = $(this).attr("data-type");
    let current = data[key];

    option.series[0].data = current[0];
    option.series[1].data = current[1];
    mychart.setOption(option);
  });
  let index = 0;
  let time = window.setInterval(function () {
    index++;
    if (index >= 4) index = 0;
    $(".caption a").eq(index).click();
  }, 1500);

  $(".line")
    .on("mouseenter", function () {
      clearInterval(time);
    })
    .on("mouseleave", function () {
      time = window.setInterval(function () {
        index++;
        if (index >= 4) index = 0;
        $(".caption a").eq(index).click();
      }, 1500);
    });
})();
(function () {
  mychart = echarts.init($(".gauge")[0]);
  let option = {
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
  };
  mychart.setOption(option);
})();
(function () {
  var data = [
    { name: "可爱多", num: "9,086" },
    { name: "娃哈哈", num: "8,341" },
    { name: "喜之郎", num: "7,407" },
    { name: "八喜", num: "6,080" },
    { name: "小洋人", num: "6,724" },
    { name: "好多鱼", num: "2,170" },
  ];
  $(".top").on("mouseenter", ".sup li", function () {
    $(this).addClass("active").siblings().removeClass("active");
    let randomdata = data.sort(function () {
      return 0.5 - Math.random();
    });
    let html = "";
    randomdata.forEach(function (elm) {
      html += `<li><span>${elm.name}</span><span>${elm.num} <s class="icon-up"></s></span></li>`;
      $(".sub").html(html);
    });
  });
  let li = $(".top .sup li");
  li.eq(0).mouseenter();
  var index = 0;
  setInterval(function () {
    index++;
    if (index >= 5) {
      index = 0;
    }
    li.eq(index).mouseenter();
  }, 2000);
})();
