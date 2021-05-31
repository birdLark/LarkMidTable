<template>
  <el-dialog title="节点信息" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="node" ref="dataForm" label-width="80px">
      <el-form-item label="名称" v-show=false>
        <el-input v-model="node.name"></el-input>
      </el-form-item>
      <el-form-item label="left坐标" v-show=false>
        <el-input v-model="node.left"></el-input>
      </el-form-item>
      <el-form-item label="top坐标" v-show=false>
        <el-input v-model="node.top"></el-input>
      </el-form-item>
      <el-form-item label="ico图标" v-show=false>
        <el-input v-model="node.ico"></el-input>
      </el-form-item>
      <el-form-item label="pkgId" v-show=false>
        <el-input v-model="node.pkgId"></el-input>
      </el-form-item>
      <el-form-item label="pkgName" v-show=false>
        <el-input v-model="node.pkgName"></el-input>
      </el-form-item>
      <el-form-item label="pkgVersion" v-show=false>
        <el-input v-model="node.pkgVersion"></el-input>
      </el-form-item>
      <el-form-item label="Job名称" >
        <el-input v-model="node.name"></el-input>
      </el-form-item>
      <el-form-item :key="domain.name"
                    :label="domain.name"
                    v-for="domain in node.params">
          <el-input  autocomplete="off"
                      v-model="domain.value" :disabled="domain.disable">
          </el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false" icon="el-icon-close">取消</el-button>
      <el-button type="primary" icon="el-icon-check" @click="visible = false">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      visible: false,
      node: {}
    }
  },
  methods: {
    /**
     * 表单修改，这里可以根据传入的ID进行业务信息获取
     * @param data
     * @param id
     */
    init (data, id) {
      this.visible = true
      data.nodeList.filter(node => {
        if (node.id === id) {
          this.node = node
          console.log(this.node.params)
        }
      })
    }
  }
}
</script>
