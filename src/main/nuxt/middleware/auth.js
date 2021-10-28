export default function ({ store, redirect }) {
  if (!store.state.authenticated.token) {
    return redirect('/Login')
  }
}
