<template>
  <div id="app">
    <router-view v-if="alive"/>
  </div>
</template>

<script>
import util from '@/libs/util'
export default {
  name: 'app',
  watch: {
    '$i18n.locale': 'i18nHandle'
  },
  created () {
    this.i18nHandle(this.$i18n.locale)
  },
  methods: {
    i18nHandle (val, oldVal) {
      util.cookies.set('lang', val)
      document.querySelector('html').setAttribute('lang', val)
    },
    reload () {
      this.alive = false
      this.$nextTick(() => {
        this.alive = true
      })
    }
  },
  provide () {
    return {
      reload: this.reload
    }
  },
  data () {
    return {
      alive: true
    }
  }
}
</script>

<style lang="scss">
@import '~@/assets/style/public-class.scss';
</style>
