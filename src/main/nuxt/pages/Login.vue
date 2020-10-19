<template>
  <b-container>
    <b-form>
      <b-form-group
        id="input-group-username"
        label="Username:"
        label-for="input-username">
        <b-form-input
          id="input-username"
          v-model="username"
          placeholder="Enter username"
          required
          type="text"></b-form-input>
      </b-form-group>
      <b-form-group
        id="input-group-password"
        label="Password:"
        label-for="input-password">
        <b-form-input
          id="input-password"
          v-model="password"
          placeholder="Enter password"
          required
          type="password"></b-form-input>
      </b-form-group>
    </b-form>


    <b-btn @click="login(username,password)">Login</b-btn>
  </b-container>

</template>

<script>
import {mapActions, mapGetters, mapMutations, mapState} from 'vuex'

export default {
  name: "Login",
  data() {
    return {
      username: '',
      password: ''
    }
  }
  ,
  methods: {
    ...mapActions({'authAction': 'authenticated/checkLogin'}),
    login(username, password) {
      const obj = {username, password}
      this.authAction(obj)
    }
  }
}
</script>

<style scoped>

</style>
