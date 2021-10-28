<template>
  <b-container class="regContainer">
    <b-form>
      <b-row>
        <b-col cols="1"></b-col>
        <b-col cols="11">
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
      </b-row>
      <b-row>
        <b-col cols="1"></b-col>
        <b-col cols="11">
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
            <b-button variant="danger" @click="submitRecovery()">
              I forgot my Password!
            </b-button>
          </b-form-group>
          <b-btn @click="login(username, password)">Login</b-btn>
        </b-col>
      </b-row>
    </b-form>
  </b-container>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'Login',
  data() {
    return {
      username: '',
      password: '',
    }
  },
  methods: {
    ...mapActions({ authAction: 'authenticated/checkLogin' }),
    async login(username, password) {
      const obj = { username, password }
      await this.authAction(obj)
      this.$router.replace('/')
    },
    submitRecovery() {
      this.$router.push('/recovery')
    },
  },
}
</script>

<style scoped></style>
