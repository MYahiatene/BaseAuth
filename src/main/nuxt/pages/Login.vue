<template>
  <b-container class="loginContainer">
    <b-form>
      <b-row>
        <b-col></b-col>
        <b-col cols="12">
          <h4>Login</h4>

          <b-form-group
            id="input-group-username"
            label="Username:"
            label-for="input-username"
          >
            <b-form-input
              id="input-username"
              v-model="username"
              placeholder="Enter username"
              required
              type="text"
            ></b-form-input>
          </b-form-group>
        </b-col>
        <b-col></b-col>
      </b-row>
      <b-row>
        <b-col></b-col>
        <b-col cols="12">
          <b-form-group
            id="input-group-password"
            label="Password:"
            label-for="input-password"
          >
            <b-form-input
              id="input-password"
              v-model="password"
              placeholder="Enter password"
              required
              type="password"
            ></b-form-input>
            <b-button class="mt-3" variant="danger" @click="submitRecovery()">
              I forgot my Password!
            </b-button>
          </b-form-group>
          <b-btn @click="login()">Login</b-btn>
          <div v-if="responseMessage" class="pt-2" style="text-align: center">
            <b-alert dismissible show variant="danger">
              {{ responseMessage }}
            </b-alert>
          </div>
        </b-col>
        <b-col></b-col>
      </b-row>
    </b-form>
  </b-container>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      username: null,
      password: null,
      responseMessage: null,
    }
  },
  methods: {
    async login() {
      try {
        const response = await this.$axios.post('/api/login', {
          username: this.username,
          password: this.password,
        })
        if (response.data.jwtToken) {
          const auth = { jwtToken: response.data.jwtToken }
          this.$store.dispatch('authenticated/checkLogin', auth)
          this.$store.dispatch('authenticated/checkRole')
          // TODO: save profile-image in store
          this.$router.replace('/')
        } else if (response.data.error) {
          this.responseMessage = response.data.error
        }
      } catch (e) {
        alert(e.toString())
      }
    },
    submitRecovery() {
      this.$router.push('/recovery')
    },
  },
}
</script>

<style scoped></style>
