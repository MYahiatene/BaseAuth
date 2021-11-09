export const state = () => ({
  hash: null,
  profilePicture: null,
  hasProfilePicture: false,
  username: null,
})

export const mutations = {
  setProfilePicture(state, profilePicture) {
    localStorage.setItem('user-profile-picture', profilePicture)
    const profilePictureLocal = localStorage.getItem('user-profile-picture')
    state.profilePicture = profilePictureLocal
    state.hasProfilePicture = true
  },
  setProfileHash(state, hash) {
    localStorage.setItem('user-profile-hash', hash)
    const profileHashLocal = localStorage.getItem('user-profile-hash')
    state.hash = profileHashLocal
  },
  setProfileData(state, username) {
    state.username = username
  },
}

export const actions = {
  async setProfileData({ commit }) {
    // TODO: Propably expand to all user relevant data
    try {
      const response = await this.$axios.get('user/profile')
      const username = response.data.username
      await commit('setProfileData', username)
    } catch (e) {
      alert(e.toString())
    }
  },
  async setProfilePicture({ commit }) {
    try {
      const response = await this.$axios.get('user/profile')
      if (response.data.profilePictureID) {
        const profilePictureResponse = await this.$axios
          .get('profile/picture/', {
            responseType: 'arraybuffer',
          })
          .then((response) => Buffer.from(response.data, 'base64'))

        const profilePicture =
          'data:image/jpeg;base64,' + profilePictureResponse
        await commit('setProfilePicture', profilePicture)
      }
    } catch (e) {
      alert(e.toString())
    }
  },
  async setProfileHash({ commit }) {
    try {
      const response = await this.$axios.get('user/profile')
      const hash = response.data.hash
      await commit('setProfileHash', hash)
    } catch (e) {
      alert(e.toString())
    }
  },
}

export const getters = {
  getHash: (state) => state.hash,
  getUsername: (state) => state.username,
  getProfilePicture: (state) => state.profilePicture,
  hasProfilePicture: (state) => state.hasProfilePicture,
}

export const setters = {}
