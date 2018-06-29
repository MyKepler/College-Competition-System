let login = (state, payload) => {
//   state.token = payload.token
  state.user = payload.user
  state.isLogin = true
}
let isLogin = state => {
  return !(state.token == null)
}

let logout = (state) => {
  state.token = null
  state.authToken = ''
  state.isLogin = false
  window.localStorage.removeItem('userId')
  window.localStorage.removeItem('user')
}

let addAuthToken = (state, token) => {
  state.authToken = token
}

let cancelAuth = (state) => {
  state.authToken = ''
}

let setLoginUser = (state, user) => {
  state.user = user
}

let clearStateLogin = (state) => {
  state.token = null
  state.user = null
  state.isLogin = false
}

export default {
  login,
  isLogin,
  addAuthToken,
  cancelAuth,
  setLoginUser,
  clearStateLogin,
  logout
}
