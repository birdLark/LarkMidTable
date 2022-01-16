import variables from '@/styles/element-variables.scss'
import defaultSettings from '@/settings'

const { showSettings, tagsView, fixedHeader, sidebarLogo, sidebarList } = defaultSettings

const state = {
  theme: variables.theme,
  showSettings: showSettings,
  tagsView: tagsView,
  fixedHeader: fixedHeader,
  sidebarLogo: sidebarLogo,
  sidebarList: sidebarList,

}

const mutations = {
  CHANGE_SETTING: (state, { key, value }) => {
    if (state.hasOwnProperty(key)) {
      state[key] = value
    }
  },
  SET_SIDEBARLIST: (state, data) => {
    state.sidebarList = data
  }
}

const actions = {
  changeSetting({ commit }, data) {
    commit('CHANGE_SETTING', data)
  },
  setSidebarList({ commit }, data) {
    commit('SET_SIDEBARLIST', data)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

