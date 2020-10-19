export default function ({ store, redirect }) {
  if (this.$store.login.state.authenticated) {
    return redirect('/Login')
  }
}
