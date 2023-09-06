<template>
	<div></div>
</template>

<script>
export default {
	name: "_id",
	data() {
		return {
			id: this.$route.params.id,
		};
	},
	methods: {
		async getProduct() {
			const res = await this.$getProduct(this.id);
			console.log(res);
		},
	},
	created() {
		this.getProduct();
	},
};
</script>

<style scoped></style>
