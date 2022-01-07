<template>
  <div class="app-container">
    <div style="width:50%;height:300px;float:left;">
      <RDBMSReader v-show="dataSource!=='hive' && dataSource!=='hbase' && dataSource!=='mongodb'" ref="rdbmsreader" @selectDataSource="showDataSource" />
      <HiveReader v-show="dataSource==='hive'" ref="hivereader" @selectDataSource="showDataSource" />
      <HBaseReader v-show="dataSource==='hbase'" ref="hbasereader" @selectDataSource="showDataSource" />
      <MongoDBReader v-show="dataSource==='mongodb'" ref="mongodbreader" @selectDataSource="showDataSource" />
    </div>
    <div style="width:50%;height:300px;float:left;">
      <RDBMSReader v-show="dataSource!=='hive' && dataSource!=='hbase' && dataSource!=='mongodb'" ref="rdbmsreader" @selectDataSource="showDataSource_t" />
      <HiveReader v-show="dataSource==='hive'" ref="hivereader" @selectDataSource="showDataSource_t" />
      <HBaseReader v-show="dataSource==='hbase'" ref="hbasereader" @selectDataSource="showDataSource_t" />
      <MongoDBReader v-show="dataSource==='mongodb'" ref="mongodbreader" @selectDataSource="showDataSource_t" />
    </div>
  </div>
</template>
<script>
import RDBMSReader from '../json-build/reader/RDBMSReader'
import HiveReader from '../json-build/reader/HiveReader'
import HBaseReader from '../json-build/reader/HBaseReader'
import MongoDBReader from '../json-build/reader/MongoDBReader'
export default {
  name: 'Reader',
  components: { RDBMSReader, HiveReader, HBaseReader, MongoDBReader },
  data() {
    return {
      dataSource: '',
      dataSource_t: ''
    }
  },
  methods: {
    getData() {
      if (this.dataSource === 'hive') {
        return this.$refs.hivereader.getData()
      } else if (this.dataSource === 'hbase') {
        return this.$refs.hbasereader.getData()
      } else if (this.dataSource === 'mongodb') {
        return this.$refs.mongodbreader.getData()
      } else {
        return this.$refs.rdbmsreader.getData()
      }
    },
    showDataSource(data) {
      this.dataSource = data
      this.getData()
    },
    getData_t() {
      if (this.dataSource_t === 'hive') {
        return this.$refs.hivereader.getData()
      } else if (this.dataSource_t === 'hbase') {
        return this.$refs.hbasereader.getData()
      } else if (this.dataSource_t === 'mongodb') {
        return this.$refs.mongodbreader.getData()
      } else {
        return this.$refs.rdbmsreader.getData()
      }
    },
    showDataSource_t(data) {
      this.dataSource_t = data
      this.getData_t()
    }
  }
}
</script>
