export default function ({ app }, inject) {
	const SHOW_API_PREFIX = "/api/reservations";
	/**
	 * 공연 예약 좌석 조회
	 * @param data {{showId: String}}
	 */
	const getShowReservedSeats = ({ showId }) => {
		return app.$axios
			.get(`${SHOW_API_PREFIX}/show/${showId}/seats`)
			.then((d) => d.data)
			.catch((e) => e.response.data);
	};
	inject("getShowReservedSeats", getShowReservedSeats);

	/**
	 * 공연 예약 좌석 조회
	 * @param data {{showId: String}}
	 */
	const getShowPreReservedSeats = ({ showId }) => {
		return app.$axios
			.get(`${SHOW_API_PREFIX}/show/${showId}/seats/pre`)
			.then((d) => d.data)
			.catch((e) => e.response.data);
	};
	inject("getShowPreReservedSeats", getShowPreReservedSeats);

	/**
	 * 선좌석 예약
	 * @param data {{showId: String, seatIds: Array}}
	 */
	const preRegisterReservation = ({ showId, seatIds, accessToken }) => {
		return app.$axios
			.post(
				`${SHOW_API_PREFIX}/pre`,
				{
					showId,
					seatIds,
				},
				{
					headers: {
						Authorization: accessToken,
					},
				}
			)
			.then((d) => d.data)
			.catch((e) => e.response.data);
	};
	inject("preRegisterReservation", preRegisterReservation);
}
