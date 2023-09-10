export default function ({ app }, inject) {
	const SHOW_API_PREFIX = "/api/shows";
	/**
	 * 공연 남은 좌석 조회
	 * @param data {{showId: String}}
	 */
	const getShowRemainSeatCnt = ({ showId }) => {
		return app.$axios
			.get(`${SHOW_API_PREFIX}/${showId}/seats/count`)
			.then((d) => d.data)
			.catch((e) => e.response.data);
	};
	inject("getShowRemainSeatCnt", getShowRemainSeatCnt);
}
