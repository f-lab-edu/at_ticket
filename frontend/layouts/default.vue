<template>
  <div>
    <Header/>
    <main>
      <nuxt/>
    </main>
  </div>
</template>

<script>
import Header from "~/layouts/header";

export default {
  name: "main",
  components: {Header}
}
</script>

<style scoped>

</style>
