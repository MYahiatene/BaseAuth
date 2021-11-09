<template>
  <div>
    <b-container class="headerCont" fluid>
      <div id="nav">
        <b-navbar class="navBar fixed-top" type="dark">
          <b-navbar-nav>
            <b-nav-item nuxt-link to="/"> Home </b-nav-item>
            <b-nav-item nuxt-link to="/about"> About </b-nav-item>
            <b-nav-item nuxt-link to="/help"> Help </b-nav-item>
          </b-navbar-nav>
          <b-navbar-nav class="ml-auto">
            <div v-if="isLoggedIn">
              <b-row>
                <b-col>
                  <b-nav-item-dropdown v-if="isAdmin" right text="Admin">
                  </b-nav-item-dropdown>
                </b-col>
                <b-col>
                  <div v-if="!hasProfilePicture">
                    <b-avatar button @click="toProfile">
                      <b-img-lazy
                        :src="'https://gravatar.com/avatar/${hash}?d=identicon'"
                      />
                    </b-avatar>
                  </div>
                  <div v-else>
                    <b-avatar button @click="toProfile">
                      <b-img-lazy
                        :src="getProfilePicture"
                        height="80px"
                        width="80px"
                      />
                    </b-avatar>
                  </div>
                </b-col>
                <b-col>
                  <b-nav-item-dropdown right>
                    <template v-slot:button-content>
                      <em>{{ getUsername }}</em>
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
import { mapGetters } from 'vuex'

export default {
  name: 'Header',
  data() {
    return {
      hash: null,
      profilePicture: null,
    }
  },
  computed: {
    ...mapGetters({
      getProfilePicture: 'profile/getProfilePicture',
      getHash: 'profile/getHash',
      hasProfilePicture: 'profile/hasProfilePicture',
      isAdmin: 'authenticated/getAdmin',
      isLoggedIn: 'authenticated/isLoggedIn',
      getUsername: 'profile/getUsername',
    }),
  },
  mounted() {
    if (this.isLoggedIn) {
      if (this.hasProfilePicture) {
        this.profilePicture = this.getProfilePicture
      } else {
        this.hash = this.getHash
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
