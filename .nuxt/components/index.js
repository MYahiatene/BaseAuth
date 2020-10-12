export { default as Logo } from '../../src/main/nuxt/components/Logo.vue'

export const LazyLogo = import('../../src/main/nuxt/components/Logo.vue' /* webpackChunkName: "components/Logo'}" */).then(c => c.default || c)
