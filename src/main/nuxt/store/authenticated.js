export const state = () => ({
  authenticated: false,
  token: null,
  username: null,
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
}

export const actions = {
  async checkLogin({ commit }, payload) {
    const credentials = {
      username: payload.username,
      password: payload.password,
    }
    const response = await this.$axios
      .post('http://localhost:8080/login', credentials)
      .catch()
    const token = response.data
    commit('setAuthenticated', token)
    commit('setUsername', payload.username)
  },
}

export const getters = {}

export const setters = {}
