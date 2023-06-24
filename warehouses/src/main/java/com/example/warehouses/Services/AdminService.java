package com.example.warehouses.Services;

import com.example.warehouses.Exception.Client.UserNotExististingException;
import com.example.warehouses.Exception.Login.WrongPasswordException;
import com.example.warehouses.Interfaces.Administrator;
import com.example.warehouses.Interfaces.AdministratorFunctions;
import com.example.warehouses.Interfaces.Client;
import com.example.warehouses.Repository.AdminRepository;
import com.example.warehouses.Repository.ClientRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Getter
@Service
public class AdminService implements AdministratorFunctions {

    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final GlobalService globalService;

    @Autowired
    public AdminService(AdminRepository adminRepository, ClientRepository clientRepository, GlobalService globalService) {
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
        this.globalService = globalService;

    }
    @Override
    public Optional<Administrator> isLoginAdmin(String email, String password, HttpServletResponse response) throws IOException {

        Optional<Administrator> adminOpt = Optional.of(adminRepository.findAdminByEmail(email).orElseThrow(
                () -> new UserNotExististingException()
        ));
        System.out.println(adminOpt.get().getEmail() + " " + adminOpt.get().getPassword());
        if (!adminOpt.get().getPassword().equals(password)) {
            throw new WrongPasswordException();
        }
        return adminOpt;
    }

    @Override
    public Optional<Client> createClient(String email,
                                         String password,
                                         String firstName,
                                         String lastName,
                                         String clientType,
                                         HttpServletResponse response) {

       return globalService.register( email,
                password,
                firstName,
                lastName,
                clientType,
                response);
    }
}

//                try {
//                    response.sendRedirect("http://localhost:8080/hidden/admin/login/potato");
//                    Връщане на резултата като json
//                    response.setContentType("application/json;charset=UTF-8");
//                    PrintWriter out = response.getWriter();
//                    Gson gson = new Gson();
//                    out.println(gson.toJson(admin)); //result обекта който щесе върне като резултат
//                    out.flush();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }