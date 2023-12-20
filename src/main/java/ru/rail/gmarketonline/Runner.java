package ru.rail.gmarketonline;

import ru.rail.gmarketonline.repository.UserRepository;
import ru.rail.gmarketonline.service.UserService;

public class Runner {
    public static void main(String[] args) throws Exception {

        UserService userService = UserService.getInstance();
        String email = "admin1@example.com";
        String password = "password1";

        System.out.println("Hello " + userService.login(email,password));
    }
}
