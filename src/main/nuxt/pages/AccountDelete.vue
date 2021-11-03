<template>
  <div class="accountDeleteMain">
    <b-container class="accountDeleteContainer" fluid>
      <b-row>
        <b-col />
        <b-col>
          <h3>Account Deletion</h3>
        </b-col>
        <b-col />
      </b-row>
    </b-container>
    <b-container class="accountDeleteContentContainer" fluid>
      <b-row>
        <b-col />
        <b-col>
          <p>Are you sure you want to delete the account?</p>
          <p>This is a final decision and cannot be reversed!</p>
          <p>
            If you decide to use the app again, a new registration must be made!
          </p>
          <b-button variant="danger" @click="deleteAccountPerminantly">
            Yes please delete my account !
          </b-button>
          <div v-if="responseSuccess" class="pt-2" style="text-align: center">
            <b-alert dismissible show variant="success">
              {{ responseSuccess }}
            </b-alert>
          </div>
          <div v-if="responseError" class="pt-2" style="text-align: center">
            <b-alert dismissible show variant="danger">
              {{ responseError }}
            </b-alert>
          </div>
        </b-col>
        <b-col />
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  middleware: 'auth',
  asyncData() {
    return {
      username: '',
      responseSuccess: null,
      responseError: null,
    }
  },
  async mounted() {
    try {
      const response = await this.$axios.get('user/profile')
      this.username = response.data.username
    } catch (e) {
      alert(e.toString())
    }
  },
  methods: {
    async deleteAccountPerminantly() {
      try {
        const response = await this.$axios.delete(
          'user/profile/delete/' + this.username
        )
        if (response.data.validated === true) {
          this.responseSuccess = response.data.successMessage
          const delay = (ms) =>
            new Promise((resolve) => setTimeout(resolve, ms))
          await delay(10000)
          this.$store.dispatch('authenticated/logout')
        } else {
          this.responseError = response.data.errorMessage
        }
      } catch (e) {
        alert(e.toString())
      }
    },
  },
}
</script>

<style></style>
