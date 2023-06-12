package com.example.warehouses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class WarehousesApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehousesApplication.class, args);
	}

}
