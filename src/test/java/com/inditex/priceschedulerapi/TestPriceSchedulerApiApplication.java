package com.inditex.priceschedulerapi;

import org.springframework.boot.SpringApplication;

public class TestPriceSchedulerApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(PriceSchedulerApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
