<template>
  <d2-container>
    <template slot="header"></template>
    <el-container>
      <el-aside width="200px" style="background-color: rgb(238, 241, 246);height:100%;">
        <el-tree
          lazy
          :load="loadNode"
          :props="defaultProps" accordion style="font-size:20px"></el-tree>
      </el-aside>
      <el-container>
        <el-header>
          <el-button type="primary" @click="querySql">查询</el-button>
          <el-button type="primary" @click="drawChart">可视化</el-button>
          <el-button type="primary"  @click="dialogFormVisible = true">保存</el-button>
          <template>
            <el-select placeholder="历史查询"  value="选择SQL语句" style="margin-left:10px;margin-right:10px">
              <el-option
                v-for="item in history"
                :key="item.name"
                :label="item.name"
                :value="item.sqlContent"
                @click.native="selectSql(item.sqlContent)">
              </el-option>
            </el-select>
          </template>
          <template>
            <el-select v-model="engine" placeholder="请选择执行引擎">
              <el-option label="PRESTO" value="PRESTO"></el-option>
              <el-option label="HIVE" value="HIVE"></el-option>
            </el-select>
          </template>
        </el-header>
        <el-main style="font-size:20px">
          <template>
            <!-- bidirectional data binding（双向数据绑定） -->
            <codemirror v-model="code" :options="cmOptions"></codemirror>
          </template>
        </el-main>
        <el-footer>
          <el-table style="width: 100%" border :data="tableData">
            <template v-for="(item,index) in tableHead">
              <el-table-column :prop="item" :label="item" :key="index"></el-table-column>
            </template>
          </el-table>
        </el-footer>
      </el-container>
    </el-container>

    <el-dialog title="保存SQL" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="名称" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveSql()">确 定</el-button>
      </div>
    </el-dialog>
  </d2-container>
</template>

<script>
import { codemirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import { QuerySql, SaveSql, GetSaveSql, ListSchemas, ListTables, GetTableInfo } from '@/api/query.js'

// language js
import 'codemirror/mode/sql/sql.js'

export default {
  inject: ['reload'],
  components: {
    codemirror
  },
  methods: {
    selectSql (sql) {
      this.code = sql
    },
    querySql () {
      console.log(this.code)
      console.log(this.engine)
      QuerySql(this.engine, this.code).then(res => {
        console.log(res)
        this.tableData = res.rows
        this.tableHead = res.schema
      })
    },
    drawChart () {
      this.$router.replace('/draw?sql=' + this.code)
    },
    saveSql () {
      SaveSql(this.form['name'], this.code)
      this.dialogFormVisible = false
      this.form = {}
      this.reload()
    },
    loadNode (node, resolve) {
      if (node.level === 0) {
        ListSchemas().then(res => {
          console.log(res)
          return resolve(res)
        })
      }
      if (node.level === 1) {
        // 获取选中的key
        console.log(node)
        ListTables(node.data.name).then(res => {
          console.log(res)
          return resolve(res)
        })
      }
      if (node.level === 2) {
        // 获取选中的key
        GetTableInfo(node.data.database, node.data.name).then(res => {
          console.log(res)
          return resolve(res)
        }
        )
      }
    }
  },
  data () {
    return {
      cmOptions: {
        // codemirror options
        tabSize: 4,
        mode: 'text/x-mysql',
        lineNumbers: true,
        extraKeys: { 'Ctrl': 'autocomplete' },
        line: true
        // more codemirror options, 更多 codemirror 的高级配置...
      },
      code: '',
      tableData: [],
      tableHead: [],
      data: [
        {
          id: 1,
          label: 'db01',
          children: [
            {
              id: 4,
              label: 'log_dev1',
              children: [
                {
                  id: 9,
                  label: 'id int'
                },
                {
                  id: 10,
                  label: 'name varchar'
                }
              ]
            }
          ]
        },
        {
          id: 2,
          label: 'db02',
          children: [
            {
              id: 5,
              label: 'log_dev2'
            },
            {
              id: 6,
              label: 'log_dev23'
            }
          ]
        },
        {
          id: 3,
          label: 'db03',
          children: [
            {
              id: 7,
              label: 'log_dev3'
            },
            {
              id: 8,
              label: 'log_dev4'
            }
          ]
        }
      ],
      defaultProps: {
        label: 'name',
        children: 'zones',
        isLeaf: 'leaf'
      },
      history: [{
        name: 'XX查询',
        sqlContent: 'select * from db01.log_dev1'
      }, {
        name: 'XX查询2',
        sqlContent: 'select * from db01.log_dev2'
      }],
      form: {},
      dialogFormVisible: false,
      formLabelWidth: '120px',
      engine: 'PRESTO'
    }
  },
  mounted () {
    GetSaveSql().then(res => {
      this.history = res
    })
  },
  computed: {
    codemirror () {
      return this.$refs.myCm.codemirror
    }
  }
}
</script>

<style>
.codesql {
  font-size: 20px;
  font-family: Consolas, Menlo, Monaco, Lucida Console, Liberation Mono,
    DejaVu Sans Mono, Bitstream Vera Sans Mono, Courier New, monospace, serif;
}

.el-tree-node{
  position: relative;
  padding-left: 6px;
  font-size: 20px;
}
</style>
