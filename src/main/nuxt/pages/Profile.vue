<template>
  <div class="profileMain">
    <b-container class="profileHeaderContainer" fluid>
      <b-row>
        <b-col />
        <b-col>
          <h3>Profile</h3>
        </b-col>
        <b-col />
      </b-row>
    </b-container>
    <b-container class="profileContentContainer" fluid>
      <b-row>
        <b-col />
        <b-col>
          <b-form>
            <b-form-group
              id="usernameFormGroup"
              description="Keep in mind, your username is unique and cannot be changed !"
              label="Username:"
              label-for="username"
            >
              <b-form-input
                id="username"
                v-model="username"
                disabled="true"
                type="text"
              />
            </b-form-group>
            <b-form-group
              id="firstNameFormGroup"
              label="Firstname:"
              label-for="first"
            >
              <b-form-input
                id="firstName"
                v-model="firstname"
                placeholder="None"
                type="text"
              />
              <b-form-invalid-feedback :state="validationaccountNameChange">
                Your first name cannot be empty!
              </b-form-invalid-feedback>
              <b-form-invalid-feedback :state="validationaccountNameNew">
                No changes detected !
              </b-form-invalid-feedback>
              <b-form-valid-feedback :state="validationaccountNameChange" />
            </b-form-group>

            <b-form-group
              id="lastNameFormGroup"
              label="Lastname:"
              label-for="lastName"
            >
              <b-form-input
                id="lastName"
                v-model="lastname"
                placeholder="None"
                type="text"
              />
              <b-form-invalid-feedback :state="validationaccountNameChange">
                Your last name cannot be empty!
              </b-form-invalid-feedback>
              <b-form-invalid-feedback :state="validationaccountNameNew">
                No changes detected !
              </b-form-invalid-feedback>
              <b-form-valid-feedback :state="validationaccountNameChange" />
            </b-form-group>

            <b-form-group
              id="emailFormGroup"
              label="Email:"
              label-for="accountEmail"
            >
              <b-form-input
                id="accountEmail"
                v-model="email"
                placeholder="None"
                type="email"
              />
              <b-form-invalid-feedback :state="validationaccountNameChange">
                Your email cannot be empty!
              </b-form-invalid-feedback>
              <b-form-invalid-feedback :state="validationaccountNameNew">
                No changes detected !
              </b-form-invalid-feedback>
              <b-form-valid-feedback :state="validationaccountNameChange" />
            </b-form-group>

            <b-form-group
              id="avatarFormGroup"
              description="Your personal Account-Avatar"
              label="Avatar:"
              label-for="avatar"
            >
              <img :src="'https://gravatar.com/avatar/${hash}?d=identicon'" />
            </b-form-group>
            <b-button variant="secondary" @click="changePassword">
              Change my password !
            </b-button>
            <b-button variant="danger" @click="deleteAccount">
              Delete my account !
            </b-button>
            <div
              v-if="validationAccountChange && validationAccountNew"
              class="pt-2"
            >
              <b-button variant="success" @click="updateProfile">
                Update my profile
              </b-button>
            </div>
            <div v-if="updated" class="pt-2" style="text-align: center">
              <b-alert dismissible show variant="success">
                Your profile was updated successfully!
              </b-alert>
            </div>
          </b-form>
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
      initfirstname: '',
      initlastname: '',
      initemail: '',
      email: '',
      firstname: '',
      lastname: '',
      changed: false,
      updated: false,
      hash: null,
    }
  },
  computed: {
    validationAccountNew() {
      return (
        this.firstname.length !== 0 ||
        this.lastname.length !== 0 ||
        this.email.length !== 0
      )
    },
    validationAccountChange() {
      return (
        this.firstname !== this.initfirstname ||
        this.lastname !== this.initlastname ||
        this.email !== this.initemail
      )
    },
  },
  async mounted() {
    try {
      const response = await this.$axios.get('user/profile')
      this.username = response.data.username

      this.firstname = response.data.firstname
      this.initfirstname = this.firstname

      this.lastname = response.data.lastname
      this.initlastname = this.lastname

      this.email = response.data.email
      this.initemail = this.email

      this.hash = response.data.hash
    } catch (e) {
      alert(e.toString())
    }
  },
  methods: {
    async updateProfile() {
      try {
        const response = await this.$axios.put('user/profile/update', {
          username: this.username,
          firstname: this.firstname,
          lastname: this.lastname,
          email: this.email,
        })
        if (response.data.validated === true) {
          this.updated = true
        }
      } catch (e) {
        alert(e.toString())
      }
    },
    changePassword() {
      this.$router.push('/passwordChange')
    },
    deleteAccount() {
      this.$router.push('/accountDelete')
    },
  },
}
</script>
