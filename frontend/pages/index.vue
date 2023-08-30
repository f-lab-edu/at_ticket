<template>
  <div>
    <button @click="requestPay">결제하기</button>
    <div v-for="p in products">
      <div>{{ p.name }}</div>
    </div>
  </div>
</template>

<script>
import axios from "axios"
import _ from 'lodash'

export default {
  name: "index",
  data() {
    return {
      IMP: null,  // iamport 모듈,
      products: [],  // 상품 리스트,
      reservationId: this.$route.query.r_id,
      reservation: null,
      price: 0,
      userToken: "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJhNDljNThlZS03YjhlLTRmNzAtYjdmZC1jMGFkZGI5OTUxODIifQ.eyJleHAiOjE2OTM0Mzk5NjIsImlhdCI6MTY5MzQzMjc2MiwianRpIjoiODgyZmNhYTAtZjYwMC00MTY1LWFiMzAtMGY5MmJhNDlhOWIxIiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5hdHRpY2tldC5vLXIua3IvcmVhbG1zL2F0dGlja2V0IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjcwYWJhMDVmLTc3NDgtNDg0NS1iYmE5LThkNGFjZDdkZWJlZSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImF0dGlja2V0Iiwic2Vzc2lvbl9zdGF0ZSI6IjQyYmJhZmQ4LWQwZmYtNGMxYS04NmI5LTJjNDhiNjU5ZjlkYSIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiLyoiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1hdHRpY2tldCIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIHBob25lIiwic2lkIjoiNDJiYmFmZDgtZDBmZi00YzFhLTg2YjktMmM0OGI2NTlmOWRhIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiLquYDspIDsmIEiLCJwaG9uZV9udW1iZXIiOiIwMTAtMTIzNC0xMjM0IiwicHJlZmVycmVkX3VzZXJuYW1lIjoia2ltanVuYm8iLCJnaXZlbl9uYW1lIjoi6rmA7KSA7JiBIiwiZW1haWwiOiJraW1qdW5ib0B0ZXN0LmNvbSJ9.mEegivwMszk1EBNKgtae46Pdc7gpqLi8TUcQ_JhEUVs",
      user: null
    }
  },
  methods: {
    async getUser() {
      const res = await axios.get(`/users`, {
        headers: {
          Authorization: this.userToken
        }
      })
      if (_.isEmpty(res) || res.data.code !== 200) return
      this.user = res.data.data
      console.log(this.user)
    },
    async getReservation() {
      if (_.isEmpty(this.reservationId)) {
        console.log("예약 번호 없음")
        return
      }
      const res = await axios.get(`/reservations/${this.reservationId}`, {
        headers: {
          Authorization: this.userToken
        }
      }).catch(e => ({}))
      console.log("예약: ", res.data)
      if (_.isEmpty(res) || res.data.code !== 200) return
      this.reservation = res.data.data
    },
    async getPrice() {
      if (_.isEmpty(this.reservation)) {
        alert("예약정보가 없습니다")
        return
      }
      const showId = this.reservation.showId;
      const seatIds = this.reservation.seatIds;
      const res = await axios.post(`/shows/${showId}/seats/price`, {
        seatIds
      })

      this.price = res.data.data
    },
    requestPay() {
      if (_.isEmpty(this.reservation) || this.price === 0) {
        alert("예약정보가 없습니다")
        return
      }

      let today = new Date();
      let hours = today.getHours(); // 시
      let minutes = today.getMinutes();  // 분
      let seconds = today.getSeconds();  // 초
      let milliseconds = today.getMilliseconds();
      let makeMerchantUid = hours + minutes + seconds + milliseconds;

      const me = this
      this.IMP.request_pay({
        pg: 'kakaopay',
        merchant_uid: "IMP" + makeMerchantUid,  // 상점에서 관리하는 주문 번호
        name: '테스트1',
        amount: this.price,
        buyer_email: this.user.email,
        buyer_name: this.user.name,
        buyer_tel: this.user.phone,
        buyer_addr: '서울특별시 강남구 삼성동',
        buyer_postcode: '123-456',
        custom_data: {
          reservation_id: `${this.reservation.id}`,
          buyer_id: this.user.userId,
          seller_id: "seller"
        }
      }, async function (rsp) { // callback
        if (rsp.success) {
          console.log(rsp);
          await axios.post(`/reservations`, {
            "paymentId": rsp.imp_uid,
            "reservationId": me.reservation.id
          }, {
            headers: {
              Authorization: me.userToken
            }
          }).then(d => {
            if (d.data.code === 200) alert("예약 성공")
          })
        } else {
          console.log(rsp);
        }
      });
    },
  },
  async mounted() {
    this.IMP = window.IMP;
    this.IMP.init("imp05082280")
    await this.getReservation()
    if (!_.isEmpty(this.reservation)) await this.getPrice()
    await this.getUser()
  }
}
</script>

<style scoped>

</style>
