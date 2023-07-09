package com.atticket.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import com.atticket.common.response.BaseExceptionHandler;

@SpringBootApplication
@Import(BaseExceptionHandler.class)
@EnableFeignClients
public class ReservationApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReservationApplication.class, args);
	}
}
