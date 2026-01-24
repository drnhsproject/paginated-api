package com.example.pagination;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.pagination.config.properties.CacheProperties;
import com.example.pagination.config.properties.ExternalApiProperties;
import com.example.pagination.config.properties.PaginationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ ExternalApiProperties.class, PaginationProperties.class, CacheProperties.class })
public class PaginationApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaginationApplication.class, args);
	}

}
