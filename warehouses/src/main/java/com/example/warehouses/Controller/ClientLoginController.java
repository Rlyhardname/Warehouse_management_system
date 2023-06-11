package com.example.warehouses.Controller;

import com.example.warehouses.Interfaces.Client;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path ="/login")
public class ClientLoginController extends Client {
        @PostMapping("/page")
        public void someOtherPage(HttpServletResponse response) {
            try {
                response.sendRedirect("/index.html");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

}
