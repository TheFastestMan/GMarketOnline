package ru.rail.gmarketonline;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.rail.gmarketonline.service.UserService;

@SpringBootApplication
public class Runner {
    public static void main(String[] args) throws Exception {
        var con =  SpringApplication.run(Runner.class, args);

        var c = con.getBean(UserService.class);

        System.out.println(c.findAllUser());
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
