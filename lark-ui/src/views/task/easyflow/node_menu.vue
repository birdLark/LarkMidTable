<template>
  <div style="background-color: #66a6e0;" ref="tool">
    <el-menu :default-openeds="defaultOpeneds">
      <el-submenu :index="menu.id" v-for="menu  in  menuList" :key="menu.id">
        <!--一级菜单名称、不可拖拽 -->
        <template slot="title">
          <i :class="menu.ico"></i>
          <span>{{menu.name}}</span>
        </template>
        <!--一级菜单子菜单、可拖拽菜单-->
        <el-menu-item-group>
          <draggable
            @end="addNode"
            @choose="move"
            v-model="menu.children"
            :options="draggableOptions"
          >
            <el-menu-item
              v-for="son in menu.children"
              :key="son.id"
              :index="son.id"
              :type="son.type"
            >
              <i :class="son.ico"></i>
              {{son.name}}
            </el-menu-item>
          </draggable>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
  </div>
</template>
<script>
import draggable from 'vuedraggable'

import { getPluginNamesGroupBy } from '@/api/task.plugin.js'

var mousePosition = {
  left: -1,
  top: -1
}

export default {
  mounted () {
    getPluginNamesGroupBy()
      .then(res => {
        console.log(res)
        this.menuList = res
      })
  },
  data () {
    return {
      draggableOptions: {
        preventOnFilter: false
      },
      // 默认打开的左侧菜单的id
      defaultOpeneds: ['1'],
      menuList: [
        {
          id: '1',
          type: 'group',
          name: '基础插件',
          ico: 'el-icon-video-play',
          children: [
            {
              id: '11',
              type: 'task',
              name: 'Mysql导入插件',
              ico: 'el-icon-time'
            },
            {
              id: '12',
              type: 'task',
              name: '数据清洗插件',
              ico: 'el-icon-odometer'
            }
          ]
        },
        {
          id: '2',
          type: 'group',
          name: '业务插件',
          ico: 'el-icon-video-pause',
          children: [
            {
              id: '21',
              type: 'task',
              name: 'XX业务插件',
              ico: 'el-icon-caret-right'
            },
            {
              id: '22',
              type: 'task',
              name: 'XX服务计算',
              ico: 'el-icon-shopping-cart-full'
            }
          ]
        }
      ],
      nodeMenu: {}
    }
  },
  components: {
    draggable
  },
  created () {
    /**
     * 以下是为了解决在火狐浏览器上推拽时弹出tab页到搜索问题
     * @param event
     */
    if (this.isFirefox()) {
      document.body.ondrop = function (event) {
        // 解决火狐浏览器无法获取鼠标拖拽结束的坐标问题
        mousePosition.left = event.layerX
        mousePosition.top = event.clientY - 50
        event.preventDefault()
        event.stopPropagation()
      }
    }
  },
  methods: {
    // 根据类型获取左侧菜单对象
    getMenu (type) {
      for (let i = 0; i < this.menuList.length; i++) {
        let children = this.menuList[i].children
        for (let j = 0; j < children.length; j++) {
          if (children[j].type === type) {
            return children[j]
          }
        }
      }
    },
    // 拖拽开始时触发
    move (evt) {
      console.log(evt.item.attributes)
      var type = evt.item.attributes.type.nodeValue
      this.nodeMenu = this.getMenu(type)
    },
    // 拖拽结束时触发
    addNode (evt, e) {
      this.$emit('addNode', evt, this.nodeMenu, mousePosition)
    },
    // 是否是火狐浏览器
    isFirefox () {
      var userAgent = navigator.userAgent
      if (userAgent.indexOf('Firefox') > -1) {
        return true
      }
      return false
    }
  }
}
</script>
<style>
.flow-tool-menu {
  background-color: #eeeeee;
  cursor: pointer;
  padding-left: 5px;
  height: 50px;
  line-height: 50px;
  border-bottom: 1px solid #979797;
}

.flow-tool-submenu {
  background-color: white;
  padding-left: 20px;
  cursor: pointer;
  height: 50px;
  line-height: 50px;
  vertical-align: middle;
  border-bottom: 1px solid #d3d3d3;
}
</style>
