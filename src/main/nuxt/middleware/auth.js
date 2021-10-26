export default function ({ store, redirect }) {
  if (!store.state.authenticated.authenticated) {
    return redirect('/Login')
  }
}
