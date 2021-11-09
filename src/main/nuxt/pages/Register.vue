<template>
  <b-container class="regContainer" fluid>
    <b-row>
      <b-col />
      <b-col cols="6">
        <h4>Registration</h4>
        <b-form>
          <b-form-group
            id="input-group-email"
            label="Email address:"
            label-for="input-email"
          >
            <b-form-input
              id="input-email"
              v-model="email"
              placeholder="Enter email"
              required
              type="email"
            />
          </b-form-group>
          <b-form-group
            id="input-group-username"
            label="Your globally displayed username:"
            label-for="input-username"
          >
            <b-form-input
              id="input-username"
              v-model="username"
              placeholder="Enter username"
              required
            />
          </b-form-group>
          <b-form-group
            id="input-group-firstname"
            label="Firstname:"
            label-for="input-firstname"
          >
            <b-form-input
              id="input-firstname"
              v-model="firstname"
              placeholder="Enter firstname"
              required
            />
          </b-form-group>
          <b-form-group
            id="input-group-lastname"
            label="Lastname:"
            label-for="input-lastname"
          >
            <b-form-input
              id="input-lastname"
              v-model="lastname"
              placeholder="Enter lastname"
              required
            />
          </b-form-group>
          <b-form-group
            id="input-group-pw1"
            label="Enter password:"
            label-for="input-pw1"
          >
            <b-form-input
              id="input-pw1"
              v-model="pw1"
              placeholder="Enter password"
              required
              type="password"
            />
            <b-form-invalid-feedback :state="validationPassword">
              Your password needs to be at least 8 chars long!
            </b-form-invalid-feedback>
            <b-form-valid-feedback :state="validationPassword">
              Looks Good.
            </b-form-valid-feedback>
          </b-form-group>
          <b-form-group
            id="input-group-pw2"
            label="Enter again:"
            label-for="input-pw2"
          >
            <b-form-input
              id="input-pw2"
              v-model="pw2"
              placeholder="Enter the same again"
              required
              type="password"
            />
            <b-form-invalid-feedback :state="validationPasswordEq">
              Your passwords dont match!
            </b-form-invalid-feedback>
            <b-form-valid-feedback :state="validationPasswordEq">
              Passwords match!
            </b-form-valid-feedback>
          </b-form-group>
          <b-form-group>
            <b-form-checkbox
              id="checkBoxTerms"
              v-model="statusTerms"
              name="checkboxTerms"
              unchecked-value="not_accepted"
              value="accepted"
            >
              I accept the terms and use
            </b-form-checkbox>
            <b-form-checkbox
              id="checkBoxPrivacy"
              v-model="statusPrivacy"
              name="checkboxPrivacy"
              unchecked-value="not_accepted"
              value="accepted"
            >
              I read and accept privacy-terms (DSGVO)
            </b-form-checkbox>
          </b-form-group>

          <b-button variant="success" @click="register()"> Register </b-button>
        </b-form>
        <b-button class="mt-2" nuxt-link to="/"> Go back </b-button>
        <div v-if="responseSuccess" class="pt-2" style="text-align: center">
          <b-alert dismissible show variant="success">
            {{ responseMassage }}
            <b-button class="mt-2" variant="success" nuxt-link to="/login">
              Now you can login and start using our app !
            </b-button>
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
</template>

<script>
export default {
  asyncData() {
    return {
      email: '',
      username: '',
      firstname: '',
      lastname: '',
      pw1: '',
      pw2: '',
      statusTerms: 'not_accepted',
      statusPrivacy: 'not_accepted',
      responseSuccess: '',
      responseError: '',
    }
  },
  computed: {
    validationPassword() {
      return this.pw1.length >= 8
    },
    validationPasswordEq() {
      return this.pw1 === this.pw2
    },
  },
  methods: {
    async register() {
      try {
        if (
          this.username &&
          this.email &&
          this.pw1 &&
          this.statusTerms === 'accepted' &&
          this.statusPrivacy === 'accepted'
        ) {
          const response = await this.$axios.post('api/register', {
            username: this.username,
            email: this.email,
            firstname: this.firstname,
            lastname: this.lastname,
            password: this.pw1,
          })
          if (response.data.validated === true) {
            this.responseSuccess = response.data.successMessage
          } else {
            this.responseError = response.data.errorMessage
          }
        }
      } catch (e) {
        alert(e.toString())
        window.location.reload()
      }
    },
  },
}
</script>
