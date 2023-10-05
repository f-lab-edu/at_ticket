<template>
  <div>
    <nuxt-child/>
  </div>
</template>

<script>
export default {
  name: "index",
  created() {
    this.$router.push("/products")
  }
};
</script>
