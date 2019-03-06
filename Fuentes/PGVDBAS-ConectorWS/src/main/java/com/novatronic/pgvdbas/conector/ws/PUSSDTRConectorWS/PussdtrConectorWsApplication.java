package com.novatronic.pgvdbas.conector.ws.PUSSDTRConectorWS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = {"com.novatronic.pgvdbas.conector.ws.listener"})
@ComponentScan(basePackages = {"com.novatronic.pgvdbas.conector.ws.service"})
public class PussdtrConectorWsApplication {
    
	public static void main(String[] args) {
		SpringApplication.run(PussdtrConectorWsApplication.class, args);
	}

}
