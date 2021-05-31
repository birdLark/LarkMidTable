var dataA = {
  name: '流程A',
  nodeList: [
    {
      id: 'nodeA',
      name: '流程A-节点A',
      left: '26px',
      top: '161px',
      ico: 'el-icon-user-solid',
      show: true
    },
    {
      id: 'nodeB',
      name: '流程A-节点B',
      left: '340px',
      top: '161px',
      ico: 'el-icon-goods',
      show: true
    },
    {
      id: 'nodeC',
      name: '流程A-节点C',
      left: '739px',
      top: '161px',
      ico: 'el-icon-present',
      show: true
    }
  ],
  lineList: [{
    from: 'nodeA',
    to: 'nodeB'
  }, {
    from: 'nodeB',
    to: 'nodeC'
  }]
}

export function getDataA () {
  return dataA
}

var dataB = {
  name: '流程B',
  nodeList: [
    {
      id: 'nodeA',
      name: '流程B-节点A',
      left: '18px',
      top: '223px',
      ico: 'el-icon-user-solid',
      show: true
    },
    {
      id: 'nodeB',
      name: '流程B-节点B',
      left: '351px',
      top: '96px',
      ico: 'el-icon-goods',
      show: true
    },
    {
      id: 'nodeC',
      name: '流程B-节点C',
      left: '354px',
      top: '351px',
      ico: 'el-icon-present',
      show: true
    }, {
      id: 'nodeD',
      name: '流程B-节点D',
      left: '773px',
      top: '215px',
      ico: 'el-icon-present',
      show: true
    }
  ],
  lineList: [{
    from: 'nodeA',
    to: 'nodeB'
  }, {
    from: 'nodeA',
    to: 'nodeC'
  }, {
    from: 'nodeB',
    to: 'nodeD'
  }, {
    from: 'nodeC',
    to: 'nodeD'
  }
  ]
}

export function getDataB () {
  return dataB
}

var dataC = {
  name: '流程C',
  nodeList: [
    {
      id: 'nodeA',
      name: '流程C-节点A',
      left: '400px',
      top: '15px',
      ico: 'el-icon-user-solid',
      show: true
    },
    {
      id: 'nodeB',
      name: '流程C-节点B',
      left: '400px',
      top: '200px',
      ico: 'el-icon-goods',
      show: true
    },
    {
      id: 'nodeC',
      name: '流程C-节点C',
      left: '400px',
      top: '378px',
      ico: 'el-icon-present',
      show: true
    }
  ],
  lineList: [
    {
      from: 'nodeA',
      to: 'nodeB'
    }, {
      from: 'nodeB',
      to: 'nodeC'
    }
  ]
}

export function getDataC () {
  return dataC
}
