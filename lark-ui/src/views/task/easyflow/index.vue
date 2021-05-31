<template>
  <d2-container>
    <template slot="header"></template>
    <template>
      <div v-if="easyFlowVisible" v-loading="loading">
        <el-row>
          <!--左侧可以拖动的菜单-->
          <el-col :span="3" ref="nodeMenu">
            <node-menu @addNode="addNode"></node-menu>
          </el-col>
          <el-col :span="21">
            <el-row>
              <!--顶部工具菜单-->
              <el-col :span="24">
                <div style="margin-bottom: 5px; margin-left: 10px">
                  <el-input placeholder="请输入流程名称" v-model="data.taskName" clearable size="medium" style="width:200px;margin:5px"></el-input>
                  <el-select v-model="data.projectName" placeholder="请选择业务线">
                    <el-option
                      v-for="item in projects"
                      :key="item"
                      :label="item"
                      :value="item">
                    </el-option>
                  </el-select>
                  <el-button type="info" icon="el-icon-document" @click="dataInfo"  style="margin:5px">流程信息</el-button>
                </div>
                <div>
                  <el-input placeholder="请输入调度时间" v-model="data.scheduleCron" clearable size="medium" style="width:200px; margin:5px"></el-input>
                  <el-switch
                    v-model="data.scheduled"
                    active-text="执行调度"
                    inactive-text="取消调度">
                  </el-switch>
                  <el-button type="primary" @click="saveWorkFlow" icon="el-icon-edit-outline"  style="margin:5px">保存</el-button>
                </div>
              </el-col>
            </el-row>
            <el-row>
              <!--画布-->
              <el-col :span="24">
                <div id="flowContainer" class="container">
                  <template v-for="node in data.nodeList">
                    <flow-node
                      v-show="node.show"
                      :id="node.id"
                      :node="node"
                      @deleteNode="deleteNode"
                      @changeNodeSite="changeNodeSite"
                      @nodeRightMenu="nodeRightMenu"
                      @editNode="editNode"
                      :key='node.id'
                    ></flow-node>
                  </template>
                </div>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
        <!-- 流程数据详情 -->
        <flow-info v-if="flowInfoVisible" ref="flowInfo" :data="data"></flow-info>
        <!-- 流程数据表单 -->
        <flow-node-form v-if="nodeFormVisible" ref="nodeForm"></flow-node-form>
      </div>
    </template>
  </d2-container>
</template>

<script>
import draggable from 'vuedraggable'
import { jsPlumb } from 'jsplumb'
import flowNode from './node'
import nodeMenu from './node_menu'
import FlowInfo from './info'
import FlowNodeForm from './node_form'
import lodash from 'lodash'
import uuid from 'uuid/v4'
import { getDataA, getDataB, getDataC } from '@/api/easyflow.test'

import { listAllProjects } from '@/api/operation.project.js'

import { getPluginPackage } from '@/api/task.plugin.js'

import { addWorkflow, getTaskGraph } from '@/api/task.workflow.js'

export default {
  data () {
    return {
      loading: false,
      projects: [],
      // jsPlumb 实例
      jsPlumb: null,
      easyFlowVisible: true,
      // 控制流程数据显示与隐藏
      flowInfoVisible: false,
      // 控制表单显示与隐藏
      nodeFormVisible: false,
      // 默认设置参数
      jsplumbSetting: {
        // 动态锚点、位置自适应
        Anchors: [
          'Top',
          'TopCenter',
          'TopRight',
          'TopLeft',
          'Right',
          'RightMiddle',
          'Bottom',
          'BottomCenter',
          'BottomRight',
          'BottomLeft',
          'Left',
          'LeftMiddle'
        ],
        Container: 'flowContainer',
        // 连线的样式 StateMachine、Flowchart
        Connector: 'StateMachine',
        // 鼠标不能拖动删除线
        ConnectionsDetachable: false,
        // 删除线的时候节点不删除
        DeleteEndpointsOnDetach: false,
        // 连线的端点
        // Endpoint: ["Dot", {radius: 5}],
        Endpoint: ['Rectangle', { height: 5, width: 5 }],
        // 线端点的样式
        EndpointStyle: { fill: 'rgba(255,255,255,0)', outlineWidth: 1 },
        LogEnabled: true, // 是否打开jsPlumb的内部日志记录
        // 绘制线
        PaintStyle: { stroke: 'black', strokeWidth: 3 },
        // 绘制箭头
        Overlays: [['Arrow', { width: 5, length: 3, location: 1 }]],
        RenderMode: 'svg'
      },
      // jsplumb连接参数
      jsplumbConnectOptions: {
        isSource: true,
        isTarget: true,
        // 动态锚点、提供了4个方向 Continuous、AutoDefault
        anchor: 'Continuous'
      },
      jsplumbSourceOptions: {
        /* "span"表示标签，".className"表示类，"#id"表示元素id */
        filter: '.flow-node-drag',
        filterExclude: false,
        anchor: 'Continuous',
        allowLoopback: false
      },
      jsplumbTargetOptions: {
        /* "span"表示标签，".className"表示类，"#id"表示元素id */
        filter: '.flow-node-drag',
        filterExclude: false,
        anchor: 'Continuous',
        allowLoopback: false
      },
      // 是否加载完毕
      loadEasyFlowFinish: false,
      // 数据
      data: {
        nodeList: [],
        lineList: []
      }
    }
  },
  components: {
    // eslint-disable-next-line vue/no-unused-components
    draggable,
    flowNode,
    nodeMenu,
    FlowInfo,
    FlowNodeForm
  },
  mounted () {
    let id = this.$route.query.id
    // id = 2
    if (typeof (id) === 'undefined') {
      this.$nextTick(() => {
        this.jsPlumb = jsPlumb.getInstance()
        this.$nextTick(() => {
          this.jsPlumbInit()
        })
      })
      console.log('new flow')
    } else {
      this.$nextTick(() => {
      // 默认加载流程A的数据、在这里可以根据具体的业务返回符合流程数据格式的数据即可
        getTaskGraph(id).then(res => {
          console.log(res)
          this.dataReload(res)
        })
      })
    }

    listAllProjects()
      .then(res => {
        console.log(res)
        this.projects = res
      })
  },
  methods: {
    saveWorkFlow () {
      console.log(this.data)
      this.loading = true
      addWorkflow(this.data).then(
        this.loading = false
      )
    },
    // 返回唯一标识
    getUUID () {
      return Math.random()
        .toString(36)
        .substr(3, 10)
    },
    jsPlumbInit () {
      this.jsPlumb.ready(() => {
        // 导入默认配置
        this.jsPlumb.importDefaults(this.jsplumbSetting)
        // 会使整个jsPlumb立即重绘。
        this.jsPlumb.setSuspendDrawing(false, true)
        // 初始化节点
        this.loadEasyFlow()
        // 单点击了连接线,
        this.jsPlumb.bind('click', (conn, originalEvent) => {
          this.$confirm('确定删除所点击的线吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
            .then(() => {
              this.jsPlumb.deleteConnection(conn)
            })
            .catch(() => {})
        })
        // 连线
        this.jsPlumb.bind('connection', evt => {
          console.log('connect')
          let from = evt.source.id
          let to = evt.target.id
          if (this.loadEasyFlowFinish) {
            console.log(from)
            console.log(to)
            this.data.lineList.push({ from: from, to: to })
          }
        })

        // 删除连线回调
        this.jsPlumb.bind('connectionDetached', evt => {
          this.deleteLine(evt.sourceId, evt.targetId)
        })

        // 改变线的连接节点
        this.jsPlumb.bind('connectionMoved', evt => {
          this.changeLine(evt.originalSourceId, evt.originalTargetId)
        })

        // 单击endpoint
        // jsPlumb.bind("endpointClick", function (evt) {
        //   console.log('endpointClick', evt)
        // })
        //
        // // 双击endpoint
        // jsPlumb.bind("endpointDblClick", function (evt) {
        //   console.log('endpointDblClick', evt)
        // })

        // contextmenu
        this.jsPlumb.bind('contextmenu', evt => {
          console.log('contextmenu', evt)
        })

        // 连线
        this.jsPlumb.bind('beforeDrop', evt => {
          console.log('drag')
          let from = evt.sourceId
          let to = evt.targetId
          if (from === to) {
            this.$message.error('不能连接自己')
            return false
          }
          if (this.hasLine(from, to)) {
            this.$message.error('不能重复连线')
            return false
          }
          if (this.hashOppositeLine(from, to)) {
            this.$message.error('不能回环')
            return false
          }
          this.$message({
            message: '连线成功',
            type: 'success'
          })
          return true
        })

        // beforeDetach
        this.jsPlumb.bind('beforeDetach', evt => {
          console.log('beforeDetach', evt)
        })
      })
    },
    // 加载流程图
    loadEasyFlow () {
      // 初始化节点
      for (var i = 0; i < this.data.nodeList.length; i++) {
        let node = this.data.nodeList[i]
        // 设置源点，可以拖出线连接其他节点
        this.jsPlumb.makeSource(node.id, this.jsplumbSourceOptions)
        // // 设置目标点，其他源点拖出的线可以连接该节点
        this.jsPlumb.makeTarget(node.id, this.jsplumbTargetOptions)

        this.jsPlumb.draggable(node.id, {
          containment: 'parent'
        })
      }

      // 初始化连线
      for (var j = 0; j < this.data.lineList.length; j++) {
        let line = this.data.lineList[j]
        this.jsPlumb.connect(
          {
            source: line.from,
            target: line.to
          },
          this.jsplumbConnectOptions
        )
      }
      this.$nextTick(function () {
        this.loadEasyFlowFinish = true
      })
    },
    // 删除线
    deleteLine (from, to) {
      this.data.lineList = this.data.lineList.filter(function (line) {
        if (line.from === from && line.to === to) {
          return false
        }
        return true
      })
    },
    // 改变连线
    changeLine (oldFrom, oldTo) {
      this.deleteLine(oldFrom, oldTo)
    },
    // 改变节点的位置
    changeNodeSite (data) {
      for (var i = 0; i < this.data.nodeList.length; i++) {
        let node = this.data.nodeList[i]
        if (node.id === data.nodeId) {
          node.left = data.left
          node.top = data.top
        }
      }
    },
    /**
     * 拖拽结束后添加新的节点
     * @param evt
     * @param nodeMenu 被添加的节点对象
     * @param mousePosition 鼠标拖拽结束的坐标
     */
    addNode (evt, nodeMenu, mousePosition) {
      let width = this.$refs.nodeMenu.$el.clientWidth
      let nodeId = uuid()
      let left = mousePosition.left
      let top = mousePosition.top
      if (left < 0) {
        left = evt.originalEvent.layerX - width
      }
      if (top < 0) {
        top = evt.originalEvent.clientY - 50
      }
      console.log(nodeMenu)
      // var node = getPluginInfo()
      var node = {
        id: nodeId,
        name: nodeMenu.name,
        left: left + 'px',
        top: top + 'px',
        ico: nodeMenu.ico,
        show: true
      }

      getPluginPackage(nodeMenu.id).then(res => {
        console.log(res)
        node['pkgId'] = res.id
        node['pkgName'] = res.name
        node['pkgVersion'] = res.version
        node['params'] = res.params
        /**
       * 这里可以进行业务判断、是否能够添加该节点
       */
        this.data.nodeList.push(node)
        this.$nextTick(function () {
          this.jsPlumb.makeSource(nodeId, this.jsplumbSourceOptions)
          this.jsPlumb.makeTarget(nodeId, this.jsplumbTargetOptions)
          this.jsPlumb.draggable(nodeId, {
            containment: 'parent'
          })
        })
      })
    },
    /**
     * 删除节点
     * @param nodeId 被删除节点Id
     */
    deleteNode (nodeId) {
      // this.$confirm('确定要删除节点' + nodeId + '?', '提示', {
      //   confirmButtonText: '确定',
      //   cancelButtonText: '取消',
      //   type: 'warning',
      //   closeOnClickModal: true
      // })
      //   .then(() => {
      /**
       * 这里需要进行业务判断，是否可以删除
       */
      // this.data.nodeList = this.data.nodeList.filter(function (node) {
      //   if (node.id === nodeId) {
      //     // 伪删除，将节点隐藏，否则会导致位置错位
      //     // node.show = false
      //     this.data.nodeList.splice(node)
      //   }
      //   return true
      // })
      this.$nextTick(function () {
        this.jsPlumb.removeAllEndpoints(nodeId)
      })
      // this.data.nodeList.splice(node)
      for (let i = 0; i < this.data.nodeList.length; i++) {
        if (this.data.nodeList[i].id === nodeId) {
          this.data.nodeList.splice(i, 1)
          i--
        }
      }
      //   })
      //   .catch(() => {})
      // return true
    },
    /**
     * 编辑节点
     * @param nodeId 被点击编辑的节点的ID
     */
    editNode (nodeId) {
      this.nodeFormVisible = true
      this.$nextTick(function () {
        this.$refs.nodeForm.init(this.data, nodeId)
      })
    },
    // 是否具有该线
    hasLine (from, to) {
      for (var i = 0; i < this.data.lineList.length; i++) {
        var line = this.data.lineList[i]
        if (line.from === from && line.to === to) {
          return true
        }
      }
      return false
    },
    // 是否含有相反的线
    hashOppositeLine (from, to) {
      return this.hasLine(to, from)
    },
    nodeRightMenu (nodeId, evt) {
      this.menu.show = true
      this.menu.curNodeId = nodeId
      this.menu.left = evt.x + 'px'
      this.menu.top = evt.y + 'px'
    },
    // 流程数据信息
    dataInfo () {
      this.flowInfoVisible = true
      this.$nextTick(function () {
        this.$refs.flowInfo.init()
      })
    },
    // 加载流程图
    dataReload (data) {
      this.easyFlowVisible = false
      this.data.nodeList = []
      this.data.lineList = []
      this.$nextTick(() => {
        data = lodash.cloneDeep(data)
        this.easyFlowVisible = true
        this.data = data
        console.log(this.data.nodeList)
        this.$nextTick(() => {
          this.jsPlumb = jsPlumb.getInstance()
          this.$nextTick(() => {
            this.jsPlumbInit()
          })
        })
      })
    },
    // 模拟载入数据dataA
    dataReloadA () {
      this.dataReload(getDataA())
    },
    // 模拟载入数据dataB
    dataReloadB () {
      this.dataReload(getDataB())
    },
    // 模拟载入数据dataC
    dataReloadC () {
      this.dataReload(getDataC())
    },
    changeLabel () {
      var lines = this.jsPlumb.getConnections({
        source: 'nodeA',
        target: 'nodeB'
      })
      lines[0].setLabel({
        label: 'admin',
        cssClass: 'labelClass a b'
      })
    }
  }
}
</script>

<style>
#flowContainer {
  background-image: linear-gradient(
      90deg,
      rgba(0, 0, 0, 0.15) 10%,
      rgba(0, 0, 0, 0) 10%
    ),
    linear-gradient(rgba(0, 0, 0, 0.15) 10%, rgba(0, 0, 0, 0) 10%);
  background-size: 10px 10px;
  height: 700px;
  background-color: rgb(251, 251, 251);
  /*background-color: #42b983;*/
  position: relative;
}

.labelClass {
  background-color: white;
  padding: 5px;
  opacity: 0.7;
  border: 1px solid #346789;
  /*border-radius: 10px;*/
  cursor: pointer;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}
</style>
