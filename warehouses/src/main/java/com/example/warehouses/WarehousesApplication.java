package com.example.warehouses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ServletComponentScan
//@EnableWebMvc
public class WarehousesApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehousesApplication.class, args);
	}

}
