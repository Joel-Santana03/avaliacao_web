package com.web.av1unidade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;



@ServletComponentScan
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Av1unidadeApplication {

    public static void main(String[] args) {
        SpringApplication.run(Av1unidadeApplication.class, args);
    }
}
