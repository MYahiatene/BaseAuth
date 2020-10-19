<template>
  <b-container>
    <h1>Test</h1>
    <b-input-group>
      <b-input></b-input>
    </b-input-group>
    <b-btn @click="login(username,password)">Login</b-btn>
  </b-container>

</template>

<script>
export default {
  name: "Login",
  methods: {
    login(username, password) {

    }
  }
}
</script>

<style scoped>

</style>
