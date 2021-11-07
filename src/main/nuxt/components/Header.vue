<template>
  <div>
    <b-container class="headerCont" fluid>
      <div id="nav">
        <b-navbar class="navBar fixed-top" type="dark">
          <b-navbar-nav>
            <b-nav-item nuxt-link to="/"> Home </b-nav-item>
            <div v-if="$store.state.authenticated.authenticated">
              <b-nav-item nuxt-link to="/dashboard"> Dashboard </b-nav-item>
            </div>
            <b-nav-item nuxt-link to="/about"> About </b-nav-item>
            <b-nav-item nuxt-link to="/help"> Help </b-nav-item>
          </b-navbar-nav>
          <b-navbar-nav class="ml-auto">
            <div v-if="$store.state.authenticated.authenticated">
              <b-row>
                <b-col>
                  <b-nav-item-dropdown v-if="isAdmin" right text="Admin">
                  </b-nav-item-dropdown>
                </b-col>
                <b-col>
                  <div v-if="!profilePicture">
                    <b-avatar button @click="toProfile">
                      <b-img-lazy
                        :src="'https://gravatar.com/avatar/${hash}?d=identicon'"
                      />
                    </b-avatar>
                  </div>
                  <div v-else>
                    <b-avatar button @click="toProfile">
                      <b-img-lazy
                        :src="profilePicture"
                        height="80px"
                        width="80px"
                      />
                    </b-avatar>
                  </div>
                </b-col>
                <b-col>
                  <b-nav-item-dropdown right>
                    <template v-slot:button-content>
                      <em>User</em>
                    </template>
                    <b-dropdown-item nuxt-link to="/profile">
                      Profile
                    </b-dropdown-item>
                    <b-dropdown-item @click="logout">
                      Sign Out
                    </b-dropdown-item>
                  </b-nav-item-dropdown>
                </b-col>
              </b-row>
            </div>
          </b-navbar-nav>
        </b-navbar>
      </div>
    </b-container>
  </div>
</template>

<script>
export default {
  name: 'Header',
  data() {
    return {
      hash: null,
      profilePicture: null,
    }
  },
  computed: {
    isAdmin() {
      return this.$store.getters['authenticated/getAdmin']
    },
    isLoggedIn() {
      return this.$store.getters['authenticated/isLoggedIn']
    },
  },
  async mounted() {
    if (this.isLoggedIn) {
      try {
        const response = await this.$axios.get('user/profile')
        this.hash = response.data.hash

        const profilePictureResponse = await this.$axios
          .get('profile/picture/', {
            responseType: 'arraybuffer',
          })
          .then((response) => Buffer.from(response.data, 'base64'))

        this.profilePicture = 'data:image/jpeg;base64,' + profilePictureResponse
      } catch (e) {
        alert(e.toString())
      }
    }
  },
  methods: {
    logout() {
      this.$store.dispatch('authenticated/logout')
    },
    toProfile() {
      this.$router.push('/profile')
    },
  },
}
</script>

<style>
.navBar {
  background: #8a8a8a;
}

.headerCont {
  margin-bottom: 5vh;
}

.nav-link {
  color: azure;
}
</style>
