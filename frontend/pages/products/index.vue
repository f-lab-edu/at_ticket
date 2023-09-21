<template>
	<div>
		<div>
			상품
			<input
				class="w-100"
				@input="input($event, 'keyword')"
				@keyup.enter="search"
			/>
			<button @click="search">검색</button>
		</div>
		<p></p>
		<div v-for="p in products">
			<div class="product" @click="goDetail(p)">{{ p.name }}</div>
		</div>
		<div v-if="isEmpty">조회된 결과가 없습니다</div>
	</div>
</template>

<script>
export default {
	name: "index",
	data() {
		return {
			keyword: "",
			products: [], // 상품 리스트,
		};
	},
	computed: {
		isEmpty() {
			return _.isEmpty(this.products);
		},
	},
	methods: {
		input({ target }, id) {
			this[id] = target.value;
		},
		async search() {
			const res = await this.$getProducts({
				keyword: this.keyword,
			});
			if (res.code !== 200) return;
			if (_.isEmpty(res.data)) {
				this.products = [];
				return;
			}
			this.products = res.data.products;
		},
		goDetail({ id }) {
			this.$router.push(`/products/${id}`);
		},
	},
	created() {
		this.search();
	},
};
</script>

<style scoped>
.product {
	border: 2px solid black;
	width: 300px;
	margin-bottom: 5px;
	cursor: pointer;
}
</style>
