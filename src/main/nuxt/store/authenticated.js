export const state = () => ({
  authenticated: false,
  token: null,
  isAdmin: false,
})

export const mutations = {
  setAuthenticated(state, token) {
    if (token !== null) {
      state.token = token
      state.authenticated = true
      localStorage.setItem('user-token', token)
      const tokenString = localStorage.getItem('user-token')
      this.$axios.defaults.baseURL = 'http://localhost:8080/api'
      this.$axios.defaults.headers.common.Authorization = `Bearer ${tokenString}`
    } else {
      state.authenticated = false
    }
  },
  setRole(state, roles) {
    state.isAdmin = roles.includes('Admin')
  },
}

export const actions = {
  checkLogin({ commit }, auth) {
    const token = auth.jwtToken
    this.$axios.defaults.headers.common.Authorization = `Bearer ${token}`
    commit('setAuthenticated', token)
  },
  async checkRole({ commit }) {
    const roles = await this.$axios.get('/checkRole').catch()
    commit('setRole', roles.data)
  },
}

export const getters = {
  getAdmin: (state) => state.isAdmin,
  isLoggedIn: (state) => !!state.authenticated,
  token: (state) => state.token,
}

export const setters = {}
