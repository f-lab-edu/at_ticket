<template>
  <div class="header">
    <div v-if="auth.loggedIn">
      {{ auth.user.name }}
      <button @click="logout">로그아웃</button>
    </div>
    <button v-else @click="login">로그인</button>
  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex";

export default {
  name: "header",
  computed: {
    ...mapGetters("auth", ["auth"])
  },
  methods: {
    ...mapActions("auth", ["logout"]),
    login() {
      this.$router.push("/login")
    },
  }
}
</script>

<style scoped>
.header {
  display: flex;
  justify-content: flex-end;
}
</style>
