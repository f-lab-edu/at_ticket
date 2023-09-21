<template>
	<div>
		<h1>좌석 선택</h1>
		<div class="seat_box">
			<div
				:class="
					reservedSeatIds.includes(seat.id)
						? 'reserved'
						: selectedSeatIds.includes(seat.id)
						? 'selected'
						: 'seat'
				"
				:style="{
					position: 'absolute',
					transform: `translateX(${Number(seat.locX) * 50}px) translateY(${
						Number(seat.locY) * 50
					}px)`,
				}"
				v-for="seat in seats"
				@click="click(seat)"
			>
				{{ `${seat.row}${seat.rowNum}` }}
			</div>
		</div>
		<div class="select_btn" @click="reserveSeat">좌석 선택 완료</div>
	</div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
	name: "index",
	data() {
		return {
			showId: this.$route.query.showId,
			seats: [],
			selectedSeatIds: [],
			reservedSeatIds: [],
		};
	},
	computed: {
		...mapGetters("auth", ["auth"]),
	},
	methods: {
		async setShowSeats() {
			// 전체좌석
			const res = await this.$getShowSeats({
				showId: this.showId,
			});
			if (_.isEmpty(res.data)) {
				this.seats = [];
				return;
			}
			this.seats = res.data.seats;
		},
		async setReservedSeats() {
			let seats = [];
			// 예약된 좌석
			const res1 = await this.$getShowReservedSeats({
				showId: this.showId,
			});
			if (!_.isEmpty(res1.data)) {
				seats = res1.data.reservedSeats.map((seat) => seat.seatId);
			}
			// 선예약된 좌석
			const res2 = await this.$getShowPreReservedSeats({
				showId: this.showId,
			});
			if (!_.isEmpty(res2.data)) {
				seats = [
					...seats,
					...res2.data.reservedSeats.map((seat) => seat.seatId),
				];
			}
			this.reservedSeatIds = seats;
		},
		click(seat) {
			const seatId = seat.id;
			if (this.reservedSeatIds.includes(seatId)) return;
			if (this.selectedSeatIds.includes(seatId)) {
				this.selectedSeatIds = this.selectedSeatIds.filter((d) => d !== seatId);
			} else {
				this.selectedSeatIds.push(seatId);
			}
		},
		async reserveSeat() {
			const res = await this.$preRegisterReservation({
				showId: this.showId,
				seatIds: this.selectedSeatIds,
				accessToken: this.auth.token.access_token,
			});
			if (!_.isEmpty(res.data)) {
				this.$router.push(`/pay?r_id=${res.data.reservationId}`);
			}
		},
	},
	mounted() {
		this.setShowSeats();
		this.setReservedSeats();
	},
};
</script>

<style scoped>
.seat_box {
	position: relative;
}

.seat {
	border: 2px solid gray;
	border-radius: 5px;
	width: 25px;
	height: 25px;
	text-align: center;
	cursor: pointer;
}

.reserved {
	border: 2px solid gray;
	border-radius: 5px;
	width: 25px;
	height: 25px;
	color: white;
	background-color: darkgray;
	text-align: center;
	cursor: pointer;
}

.selected {
	border: 2px solid gray;
	border-radius: 5px;
	width: 25px;
	height: 25px;
	color: white;
	background-color: blueviolet;
	text-align: center;
	cursor: pointer;
}

.select_btn {
	margin-top: 320px;
	width: max-content;
	padding: 5px;
	border-radius: 10px;
	color: white;
	cursor: pointer;
	background-color: blueviolet;
}
</style>
