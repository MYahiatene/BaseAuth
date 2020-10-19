export default function ({ store, redirect }) {
  if (!this.$store.authenticated.state.authenticated) {
    return redirect('/Login')
  }
}
