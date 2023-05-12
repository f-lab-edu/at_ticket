package com.atticket.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReservationApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReservationApplication.class, args);
	}
}
