<template>
	<div>
		<button @click="requestPay">결제하기</button>
		<div>{{ auth.user.name }}</div>
	</div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
	name: "index",
	data() {
		return {
			IMP: null, // iamport 모듈,
			reservationId: this.$route.query.r_id,
			reservation: null,
			price: 0,
		};
	},
	computed: {
		...mapGetters("auth", ["auth"]),
	},
	methods: {
		async getUser() {
			const res = await this.$axios.get(`/api/users`, {
				headers: {
					Authorization: this.userToken,
				},
			});
			if (_.isEmpty(res) || res.data.code !== 200) return;
			this.user = res.data.data;
			console.log(this.user);
		},
		async getReservation() {
			if (_.isEmpty(this.reservationId)) {
				console.log("예약 번호 없음");
				return;
			}
			const res = await this.$axios.get(
				`/api/reservations/${this.reservationId}`,
				{
					headers: {
						Authorization: this.userToken,
					},
				}
			);
			console.log("예약: ", res.data);
			if (_.isEmpty(res) || res.data.code !== 200) return;
			this.reservation = res.data.data;
		},
		async getPrice() {
			if (_.isEmpty(this.reservation)) {
				alert("예약정보가 없습니다");
				return;
			}
			const showId = this.reservation.showId;
			const seatIds = this.reservation.seatIds;
			const res = await this.$axios.post(`/api/shows/${showId}/seats/price`, {
				seatIds,
			});

			this.price = res.data.data;
		},
		requestPay() {
			if (_.isEmpty(this.reservation) || this.price === 0) {
				alert("예약정보가 없습니다");
				return;
			}

			let today = new Date();
			let hours = today.getHours(); // 시
			let minutes = today.getMinutes(); // 분
			let seconds = today.getSeconds(); // 초
			let milliseconds = today.getMilliseconds();
			let makeMerchantUid = hours + minutes + seconds + milliseconds;

			const me = this;
			this.IMP.request_pay(
				{
					pg: "kakaopay",
					merchant_uid: "IMP" + makeMerchantUid, // 상점에서 관리하는 주문 번호
					name: "테스트1",
					amount: this.price,
					buyer_email: this.user.email,
					buyer_name: this.user.name,
					buyer_tel: this.user.phone,
					buyer_addr: "서울특별시 강남구 삼성동",
					buyer_postcode: "123-456",
					custom_data: {
						reservation_id: `${this.reservation.id}`,
						buyer_id: this.user.userId,
						seller_id: "seller",
					},
				},
				async function (rsp) {
					// callback
					if (rsp.success) {
						console.log(rsp);
						await this.$axios
							.post(
								`/api/reservations`,
								{
									paymentId: rsp.imp_uid,
									reservationId: me.reservation.id,
								},
								{
									headers: {
										Authorization: me.userToken,
									},
								}
							)
							.then((d) => {
								if (d.data.code === 200) alert("예약 성공");
							});
					} else {
						console.log(rsp);
					}
				}
			);
		},
	},
	async mounted() {
		this.IMP = window.IMP;
		this.IMP.init("imp05082280");
		await this.getReservation();
		if (!_.isEmpty(this.reservation)) await this.getPrice();
		await this.getUser();
	},
};
</script>

<style scoped></style>
