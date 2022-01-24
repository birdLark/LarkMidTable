<template>
  <div class="headerStyle">
    <div class="avatar-wrapper" style="display: flex;align-items: center;width:210px;">
      <img
        src="../../../../public/logo.png"
        style="width:30px;height:30px;margin:0px 10px 0px 20px;border-radius:5px;"
      >
      <span>LMT 数据中台</span>
    </div>
    <el-menu
      background-color="#2e303a"
      active-text-color="#409EFF"
      text-color="#fff"
      mode="horizontal"
      @select="handleSelect"
    >
      <template v-for=" ite in headerMenu">
        <el-menu-item :index="ite.path">
          <item :icon="ite.icon" :title="ite.title"/>
        </el-menu-item>
      </template>
    </el-menu>
    <Logobar/>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Item from "./Item";
import Logobar from "../Logobar";
export default {
  name: "HeaderSidebar",
  components: {
    Logobar,
    Item
  },
  data() {
    return {
      headerMenu: []
    };
  },
  computed: {
    ...mapGetters(["permission_routes"])
  },
  mounted() {
    let data = [];
    this.permission_routes.forEach(item => {
      if (item.meta && !item.hidden) {
        data.push({
          path: item.path,
          title: item.meta.title,
          icon: item.meta.icon
        });
      }
    });
    this.headerMenu = data;
  },
  methods: {
    handleSelect(key, keyPath) {
      this.permission_routes.forEach(item => {
        if (item.children && item.path === key) {
          this.$store.dispatch("settings/setSidebarList", item.children);
        }
      });
    }
  }
};
</script>
