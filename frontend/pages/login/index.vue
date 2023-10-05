<template>
	<div class="flex-col">
		<div>
			ID :
			<input
				class="w-100"
				@input="input($event, 'username')"
				@keyup.enter="onLogin"
			/>
		</div>
		<div>
			PW :
			<input
				type="password"
				class="w-100"
				id="password"
				@input="input($event, 'password')"
				@keyup.enter="onLogin"
			/>
		</div>
		<button class="w-100" @click="onLogin">로그인</button>
	</div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
	name: "index",
	layout() {
		return "empty";
	},
	data() {
		return {
			username: "",
			password: "",
		};
	},
	computed: {
		...mapGetters("auth", ["auth"]),
	},
	methods: {
		...mapActions("auth", ["login"]),
		input({ target }, id) {
			this[id] = target.value;
		},
		async onLogin() {
			const res = await this.login({
				username: this.username,
				password: this.password,
			});
			if (res) await this.$router.push("/products");
		},
	},
};
</script>

<style scoped>
.flex-col {
	display: flex;
	flex-flow: column nowrap;
}

.w-100 {
	width: 100px;
}
</style>
