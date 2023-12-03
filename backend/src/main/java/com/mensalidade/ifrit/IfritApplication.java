package com.mensalidade.ifrit;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "IFRIT", version = "1.0", description = "Sistema de Mensalidade e cobranças Ifrit."))
public class IfritApplication {

	public static void main(String[] args){
		SpringApplication.run(IfritApplication.class, args);
	}

}
