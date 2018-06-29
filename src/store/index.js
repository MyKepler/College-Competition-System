import Vue from 'vue'
import Vuex from 'vuex'
import mutations from './mutations'

Vue.use(Vuex)
const state = {
  // token: '', // 身份令牌
  user: {}, // 当前登录账号信息
  isLogin: false,
  authToken: ''
}
export default new Vuex.Store({
  state,
  mutations
})
