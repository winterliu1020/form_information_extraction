package cn.edu.nchu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"cn.edu.nchu.mappers"})
@SpringBootApplication
public class FormInformationApplication {

    public static void main(String[] args) {
        SpringApplication.run(FormInformationApplication.class, args);
    }

}
