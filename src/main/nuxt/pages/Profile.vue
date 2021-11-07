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
            <b-row>
              <b-col />
              <b-col>
                <b-form-group
                  id="avatarFormGroup"
                  description="Your personal Account-Avatar"
                  label="Avatar:"
                  label-for="avatar"
                >
                  <div v-if="!profilePicture">
                    <b-img-lazy
                      :src="'https://gravatar.com/avatar/${hash}?d=identicon'"
                      height="150px"
                      width="150px"
                    />
                  </div>
                  <div v-else>
                    <b-img-lazy
                      :src="profilePicture"
                      height="150px"
                      width="150px"
                    />
                  </div>
                </b-form-group>
              </b-col>
              <b-col />
            </b-row>
            <b-row>
              <b-col />
              <b-col cols="12">
                <b-form-file
                  v-model="profilePictureUpload"
                  class="mb-2"
                  accept=".jpg"
                ></b-form-file>
                <b-row>
                  <b-col cols="3" />
                  <b-col>
                    <div v-if="profilePictureUpload">
                      <b-button
                        variant="secondary"
                        @click="uploadProfilePicture"
                      >
                        Upload my profile picture !
                      </b-button>
                    </div>
                  </b-col>
                  <b-col cols="3" />
                </b-row>
                <b-row class="mt-3">
                  <b-col cols="6">
                    <b-button variant="primary" @click="changePassword">
                      Change my password !
                    </b-button>
                  </b-col>
                  <b-col />
                  <b-col cols="5">
                    <b-button variant="danger" @click="deleteAccount">
                      Delete my account !
                    </b-button>
                  </b-col>
                </b-row>
                <div
                  v-if="validationAccountChange && validationAccountNew"
                  class="pt-2"
                >
                  <b-row>
                    <b-col cols="4" />
                    <b-col cols="4">
                      <b-button variant="success" @click="updateProfile">
                        Update my profile
                      </b-button>
                    </b-col>
                    <b-col cols="4" />
                  </b-row>
                </div>
                <div v-if="updated" class="pt-2" style="text-align: center">
                  <b-alert dismissible show variant="success">
                    Your profile was updated successfully!
                  </b-alert>
                </div>
              </b-col>
              <b-col />
            </b-row>
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
      profilePicture: null,
      profilePictureUpload: null,
      changed: false,
      updated: false,
      hash: null,
      responseError: null,
      responseSuccess: null,
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

      if (response.data.profilePictureID) {
        const profilePictureResponse = await this.$axios
          .get('profile/picture/', {
            responseType: 'arraybuffer',
          })
          .then((response) => Buffer.from(response.data, 'base64'))

        this.profilePicture = 'data:image/jpeg;base64,' + profilePictureResponse
      }
    } catch (e) {
      alert(e.toString())
    }
  },
  methods: {
    async uploadProfilePicture() {
      try {
        const formData = new FormData()
        formData.append('profilePicture', this.profilePictureUpload)

        const response = await this.$axios.post('/profile/picture', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        if (response.data.validated === true) {
          this.responseSuccess = response.data.successMessage
        } else {
          this.responseError = response.data.errorMessage
        }
      } catch (e) {
        alert(e.toString())
      }
    },
    async updateProfile() {
      try {
        const response = await this.$axios.put('user/update', {
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

<style>
.profilePicture {
  width: 80px;
  height: 80px;
}
</style>
