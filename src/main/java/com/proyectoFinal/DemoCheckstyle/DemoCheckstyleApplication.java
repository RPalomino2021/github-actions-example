package com.proyectoFinal.DemoCheckstyle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DemoCheckstyleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoCheckstyleApplication.class, args);
		log.info("[Main] client running...");
	}

}
