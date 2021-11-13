export const state = () => ({
  hash: null,
  profilePicture: null,
  hasProfilePicture: false,
  username: null,
  firstname: null,
  lastname: null,
  email: null,
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
  setProfileData(state, userdata) {
    state.username = userdata.username
    state.firstname = userdata.firstname
    state.lastname = userdata.lastname
    state.email = userdata.email
  },
}

export const actions = {
  async setProfileData({ commit }) {
    try {
      const response = await this.$axios.get('user/profile')
      const username = response.data.username
      const lastname = response.data.lastname
      const firstname = response.data.firstname
      const email = response.data.email

      const userdata = { username, lastname, firstname, email }
      await commit('setProfileData', userdata)
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
  getFirstname: (state) => state.firstname,
  getLastname: (state) => state.lastname,
  getEmail: (state) => state.email,
  getProfilePicture: (state) => state.profilePicture,
  hasProfilePicture: (state) => state.hasProfilePicture,
}

export const setters = {}
