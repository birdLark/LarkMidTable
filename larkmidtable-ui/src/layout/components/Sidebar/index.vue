<template>
  <div>
    <logo v-if="showLogo" :collapse="isCollapse"/>
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        background-color="#42444d"
        text-color="#ffffff"
        :unique-opened="false"
        :active-text-color="variables.menuActiveText"
        :collapse-transition="false"
        mode="vertical"
      >
        <sidebar-item
          v-for="route in data"
          :key="route.path"
          :item="route"
          :base-path="route.path"
        />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Logo from "./Logo";
import SidebarItem from "./SidebarItem";
import HeaderSidebar from "./headerSidebar";
import variables from "@/styles/variables.scss";

export default {
  components: { SidebarItem, Logo, HeaderSidebar },
  computed: {
    ...mapGetters(["permission_routes", "sidebar", "sidebarList"]),
    data() {
      if (this.sidebarList.length > 0) {
        this.$router.push({ path: `${this.sidebarList[0].path}` });
        return this.sidebarList;
      } else {
        return [
          {
            path: "/",
            redirect: "/dashboard",
            name: "Dashboard",
            meta: { title: "首页", icon: "dashboard", affix: true },
            children: [
              {
                path: "dashboard",
                component: () => import("@/views/dashboard/admin/index"),
                name: "Dashboard",
                meta: { title: "首页", icon: "dashboard", affix: true }
              }
            ]
          }
        ];
      }
    },
    activeMenu() {
      const route = this.$route;
      const { meta, path } = route;
      // if set path, the sidebar will highlight the path you set
      if (meta.activeMenu) {
        return meta.activeMenu;
      }
      return path;
    },
    showLogo() {
      return this.$store.state.settings.sidebarLogo;
    },
    variables() {
      return variables;
    },
    isCollapse() {
      return !this.sidebar.opened;
    }
  }
};
</script>
