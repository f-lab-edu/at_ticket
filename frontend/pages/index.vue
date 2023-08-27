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

export default {
  name: "index",
  data() {
    return {
      IMP: null,  // iamport 모듈,
      products: [],  // 상품 리스트,
    }
  },
  methods: {
    async getProducts() {
      const res = await axios.get(`/products`)
      this.products = res.data.data.products
      console.log(res.data)
    },
    async test() {
      const showId = 1;
      const seatIds = [1, 2]
      const res = await axios.post(`/shows/${showId}/seats/price`, {
        seatIds
      })

      console.log(res)
    },
    requestPay() {
      let today = new Date();
      let hours = today.getHours(); // 시
      let minutes = today.getMinutes();  // 분
      let seconds = today.getSeconds();  // 초
      let milliseconds = today.getMilliseconds();
      let makeMerchantUid = hours + minutes + seconds + milliseconds;


      this.IMP.request_pay({
        pg: 'kakaopay',
        merchant_uid: "IMP" + makeMerchantUid,  // 상점에서 관리하는 주문 번호
        name: '테스트1',
        amount: 30000,
        buyer_email: 'Iamport@chai.finance',
        buyer_name: 'at_ticket 테스트',
        buyer_tel: '010-1234-5678',
        buyer_addr: '서울특별시 강남구 삼성동',
        buyer_postcode: '123-456',
        custom_data: {
          reservation_id: "3",
          buyer_id: "buyer",
          seller_id: "seller"
        }
      }, function (rsp) { // callback
        if (rsp.success) {
          console.log(rsp);

        } else {
          console.log(rsp);
        }
      });
    },
  },
  mounted() {
    this.IMP = window.IMP;
    this.IMP.init("imp05082280")
    this.test()
    this.getProducts()
  }
}
</script>

<style scoped>

</style>
