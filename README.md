# At Ticket

---


![img](./readmeImage/at_ticket%20logo1.PNG)



<div style="font-size:20px; font-weight : bold;  " >  etiquette at ticket  </div> 

    콘서트, 뮤지컬, 영화 티켓 상품을 등록하고 예매할 수 있는 티켓 예매 플랫폼 프로젝트입니다.

## 프로젝트 구조

---

![img](./readmeImage/구조도.png)

## 사용 기술 및 개발환경

---

**Java11** , **spring boot** , **mysql**, **Jenkins**, **Docker** ,**kafka** , **Keycloak** , **Ngnix**

## 기능 목록

---

* 상품
    * 등록
    * 조회
* 공연
    * 등록
    * 조회
* 어쩌구 저쩌구

더 자세히 보고 싶으시다면 👉  [Use Case (wiki)](https://github.com/f-lab-edu/at_ticket/wiki/Use-Case)

## 협업을 중요시 합니다.

---

* 코드 컨벤션
    ```
    읽기 좋은 코드를 지향합니다.
    ```

    * 네이버 code style 적용 [(네이버 코드 컨벤션)](https://naver.github.io/hackday-conventions-java/)
    * 플러그인을 사용하여 코드 컨벤션을 유지 하였습니다.


* 코드 리뷰
    ```
      더 나은 코드를 위하여
    ```
    * PR 된 코드들은 코드리뷰를 통해 팀원의 의견을 들은 후 Main branch에 반영됩니다.

## 반복 작업을 자동화 하였습니다

---

**Jenkins**를 사용하여 반복적으로 이루어지는 코드 배포를 자동화하였습니다.

## 멀티모듈 사용

---

* 멀티 모듈을 사용하여, 공통적으로 사용하는 코드들은 Common 모듈로 분리하여 사용하였습니다.
* 독립적인 모듈 기능 어쩌구..
* gradle의 Custom plugin을 사용하여 필요한 기능에서 필요한 dependency만 사용할 수 있도록 작성하였습니다.

[//]: # (## 비동기 사용해봄)

## 프로젝트를 진행하면서 고민해본 고민들

---

* 예약하기 기능
    * 사용자가 동시에 같은 표를 예약하려고 하는 경우, 누구에게 표 구매 우선권을 주어야 하나?
    * 중간에 에러가 발생시 / 중간에 결제 하다 말았을 경우
* 좌석 등급 매핑은 어떻게 만드는 것이 좋은가
* JPA 사용시 entity의 관계를 최대한 활용하는 것이 좋을까?

* Common 모듈 사용시 어디까지가 공통으로 사용해야 할 것인가

 <span style="color:blue"> wiki  </span>

[//]: # (* 세션 관리는 어떻게 할 것 인가)

[//]: # (    * keyclock, JWT)

### Todo

---

* 성능 테스트
* 대용량 트래픽이 들어올때..?
* 말씀해 주신 거,........
