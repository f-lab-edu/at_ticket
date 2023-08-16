package com.atticket.kafka.consumer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.atticket.common.kafka.payload.WishProduct;
import com.atticket.kafka.config.MailSender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotifyConsumer {

	private final MailSender mailSender;

	/**
	 * 받은 데이터로 메일을 발송한다.
	 * @param data
	 */
	@KafkaListener(topics = "NOTIFY_MAIL", groupId = "myGroup")
	public void consume(WishProduct data) throws Exception {

		log.info(String.format("Product[sendNotifyMail] 관심 상품 등록 received -> %s", data.toString()));

		//메일 내용
		String mailTitle = "공연 등록 알림";
		String mailMessge =
			"[at_ticket]" + "알림 신청하신 공연 : " + data.getProductNm() + "이 등록되었습니다" + System.lineSeparator()
				+ "다음 일정의 공연이 등록되었습니다."
				+ System.lineSeparator();

		for (String show : data.getShowData()) {
			mailMessge = mailMessge + System.lineSeparator();
			mailMessge = mailMessge + show;
		}

		List<String> receiverMails = data.getUserData().stream().map(x -> x.getEmail()).collect(Collectors.toList());

		//메일 발송
		String tempMailMessge = mailMessge;
		receiverMails.forEach(
			x ->
			{
				try {
					mailSender.Send(mailTitle, tempMailMessge, x);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		);

	}

}
