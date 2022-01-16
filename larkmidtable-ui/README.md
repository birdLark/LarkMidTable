# LarkMidTableUI
中文 | [English](README_EN.md)

Lark中文名称 云雀，云代表大数据，雀代表平凡和自由。

LarkMidTable 是一站式开源的数据中台，实现管理，数据仓库开发，数据质量管理，数据的可视化，实现高效赋能数据前台并提供数据服务的产品。



# **产品愿景**

1.满足许多的小企业，提供一站式的解决方案。

2.做出世界级别，能够媲美BAT大厂的产品。

3.创造价值，产生价值，让世界变得更加美好。

# 技术选型

| 框架名称                                                     | 框架用途          | 主要功能         |
| ------------------------------------------------------------ | ----------------- | ---------------- |
| [vue-element-admin](https://github.com/PanJiaChen/vue-element-admin) | 后端管理界面模板  | 方便搭建管理界面 |
| [flinkx-web-ui](https://github.com/wxgzgl/flinkx-web-ui)       | flinkx后端管理界面 | 方便搭建管理界面 |

# **快速开始**

[后端项目地址]( https://github.com/wxgzgl/LarkMidTable )

运行

```
npm install [ 慢的话用  npm install --registry https://registry.npm.taobao.org]
```

修改配置

找到 `vue.config.js` 修改 `proxy` 里的属性即可

```
[process.env.VUE_APP_API]: {
        target: `http://localhost:${apiPort}/api`,
        changeOrigin: true,
        pathRewrite: {
          ['^' + process.env.VUE_APP_API]: ''
        }
```

启动

```
 npm run dev
```

打包

```
npm run build:prod
```

# 技术交流


**一个人走的很快，一群人走的更远。**

**扫描下面的QQ二维码加入Lark的数据中台开源社区，并为你提供全程免费服务，你也可以与其他伙伴交流大数据技术，如果觉得项目不错，可以star关注，LarkMidTable团队将十分感谢您的关注！**




![QQ群：](https://img2020.cnblogs.com/blog/622382/202009/622382-20200907124358049-997953244.png)
