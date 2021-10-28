export const state = () => ({
  authenticated: false,
  token: null,
  username: null,
  isAdmin: false,
})

export const mutations = {
  setUsername(state, username) {
    state.username = username
  },
  setAuthenticated(state, token) {
    if (token !== null) {
      state.token = token
      state.authenticated = true
      localStorage.setItem('user-token', token)
      const tokenString = localStorage.getItem('user-token')
      this.$axios.defaults.baseURL = 'http://127.0.0.1:8080/api'
      this.$axios.defaults.headers.common = {
        Authorization: 'Bearer ' + tokenString,
        username: state.username,
      }
    } else {
      state.authenticated = false
    }
  },
  setRole(state, roles) {
    console.log(roles.includes('Admin'))
    state.isAdmin = roles.includes('Admin')
  },
}

export const actions = {
  async checkLogin({ commit }, payload) {
    const credentials = {
      username: payload.username,
      password: payload.password,
    }
    const response = await this.$axios
      .post('http://localhost:8080/api/login', credentials)
      .catch()
    const token = response.data
    console.log(token)

    commit('setAuthenticated', token)
    commit('setUsername', payload.username)
    console.log(payload.username)
    const roles = await this.$axios
      .post('http://localhost:8080/api/checkRole', {
        username: payload.username,
      })
      .catch()
    commit('setRole', roles.data)
  },
}

export const getters = {
  getAdmin: (state) => state.isAdmin,
}

export const setters = {}
