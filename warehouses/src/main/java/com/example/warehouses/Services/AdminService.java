package com.example.warehouses.Services;

import com.example.warehouses.Exception.ClientAlreadyRegisteredException;
import com.example.warehouses.Exception.UserNotExististingException;
import com.example.warehouses.Exception.WrongPasswordException;
import com.example.warehouses.Interfaces.Administrator;
import com.example.warehouses.Interfaces.AdministratorFunctions;
import com.example.warehouses.Interfaces.Client;
import com.example.warehouses.Model.Agent;
import com.example.warehouses.Model.MasterAdmin;
import com.example.warehouses.Model.Owner;
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

    @Autowired
    public AdminService(AdminRepository adminRepository, ClientRepository clientRepository) {
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;

    }

    public void testService() {
        adminRepository.save(new MasterAdmin());
        Administrator admin = adminRepository.findById(1L).orElseThrow(() -> new IllegalStateException(
                "HAHAHA"
        ));
    }


    @Override
    public Optional<Administrator> isLoginClient(String email, String password, HttpServletResponse response) throws IOException {

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

        Client client = null;
        if(clientType.equals("Owner")){
            client = new Owner();
            ((Owner)client).init(email,password,firstName,lastName);
        } else if(clientType.equals("Agent")){
            client = new Agent();
            ((Agent)client).init(email,password,firstName,lastName);
        }


        Optional<Client> clientOpt = clientRepository.findClientByEmail(email);
        if (clientOpt.isPresent()==false) {
            clientRepository.save(client);
        } else if(clientOpt != null){
            throw new ClientAlreadyRegisteredException();
        }

        return clientOpt;
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