<template>
  <div>
    <b-container class="headerCont" fluid>
      <div id="nav">
        <b-navbar class="navBar fixed-top" type="dark">
          <b-navbar-nav>
            <b-nav-item nuxt-link to="/"> Home </b-nav-item>
            <b-nav-item-dropdown v-if="isAdmin" right text="Admin">
            </b-nav-item-dropdown>

            <b-nav-item-dropdown right text="Modules">
              <b-dropdown-item nuxt-link to="/dashboard">
                Main-Dashboard
              </b-dropdown-item>
            </b-nav-item-dropdown>
            <b-nav-item nuxt-link to="/about"> About </b-nav-item>
            <b-nav-item nuxt-link to="/help"> Help </b-nav-item>
          </b-navbar-nav>
          <b-navbar-nav class="ml-auto">
            <div v-if="$store.state.authenticated.authenticated">
              <b-row>
                <b-col>
                  <b-avatar button nuxt-link to="/profile">
                    <img
                      :src="'https://gravatar.com/avatar/${hash}?d=identicon'"
                    />
                  </b-avatar>
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
    return {}
  },
  computed: {
    isAdmin() {
      return this.$store.getters['authenticated/getAdmin']
    },
  },
  methods: {
    logout() {
      this.$store.dispatch('auth/logout')
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
