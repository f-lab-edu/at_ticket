<template>
	<div>
		<h1>상품</h1>
		<table>
			<tr>
				<th>상품명</th>
				<td>{{ product.name }}</td>
			</tr>
			<tr>
				<th>상품설명</th>
				<td>{{ product.explain }}</td>
			</tr>
			<tr>
				<th>지역</th>
				<td>{{ product.region }}</td>
			</tr>
			<tr>
				<th>기간</th>
				<td>{{ product.startDate }} - {{ product.endDate }}</td>
			</tr>
			<DatePicker
				v-model="selectedDate"
				value-type="format"
				format="YYYY-MM-DD"
				:clearable="false"
				:editable="false"
				@change="change"
				:disabled-date="notShowDate"
			></DatePicker>
			<div class="show_box">
				<div
					:class="selectedShow.id === show.id ? 'selected' : 'show'"
					@click="clickShow(show)"
					v-for="(show, i) in shows"
				>
					{{ showInfos[i] }}
				</div>
			</div>
			<div class="seat_box">
				<div v-html="seatCntInfo"></div>
			</div>
			<div class="reservation_btn" @click="goToReservationPage">예매하기</div>
		</table>
	</div>
</template>

<script>
import moment from "moment";

export default {
	name: "_id",
	data() {
		return {
			productId: this.$route.params.id,
			product: {},
			dates: [],
			selectedDate: "",
			selectedShow: {},
			shows: [],
			seatCnts: [],
		};
	},
	computed: {
		showInfos() {
			return this.shows.map((show, i) => `${i + 1}회 ${show.time}`);
		},
		seatCntInfo() {
			return this.seatCnts
				.map(
					(seatCnt) =>
						`${seatCnt.gradeNm}석 <strong>${
							seatCnt.seatCnt ? seatCnt.seatCnt : "매진"
						}</strong>`
				)
				.join(" / ");
		},
	},
	methods: {
		async setProduct() {
			const id = this.productId;
			const res = await this.$getProduct({ id });
			if (_.isEmpty(res.data)) return;
			this.product = res.data.product;
			this.dates = [...new Set(res.data.showDates)];
		},
		async setShows() {
			const res = await this.$getShows({
				productId: this.productId,
				date: this.selectedDate,
			});
			if (_.isEmpty(res.data)) return;
			this.shows = res.data.shows;
		},
		async setSeatCnts() {
			const res = await this.$getShowRemainSeatCnt({
				showId: this.selectedShow.id,
			});
			if (_.isEmpty(res.data)) {
				this.seatCnts = [];
				return;
			}
			this.seatCnts = res.data.remainSeats;
		},
		async clickShow(show) {
			this.selectedShow = show;
			await this.setSeatCnts();
		},
		async change(date) {
			this.selectedDate = date;
			await this.setShows();
			this.selectedShow = _.head(this.shows);
			await this.setSeatCnts();
		},
		notShowDate(date) {
			return !this.dates.includes(moment(date).format("YYYY-MM-DD"));
		},
		goToReservationPage() {
			this.$router.push(`/reservation?showId=${this.selectedShow.id}`);
		},
	},
	async mounted() {
		await this.setProduct();
		this.selectedDate = _.head(this.dates);
		await this.setShows();
		this.selectedShow = _.head(this.shows);
		await this.setSeatCnts();
	},
};
</script>
<style scoped>
.show_box {
	margin: 10px 0;
	display: flex;
	flex-flow: row nowrap;
	gap: 5px;
}

.show {
	padding: 5px;
	border: 2px solid gray;
	border-radius: 10px;
	color: gray;
	cursor: pointer;
}

.selected {
	padding: 5px;
	border: 2px solid blueviolet;
	border-radius: 10px;
	color: blueviolet;
	cursor: pointer;
}

.seat_box {
	display: flex;
	flex-flow: row nowrap;
	gap: 5px;
}

.reservation_btn {
	margin-top: 10px;
	width: max-content;
	padding: 5px;
	border-radius: 10px;
	color: white;
	cursor: pointer;
	background-color: blueviolet;
}
</style>
