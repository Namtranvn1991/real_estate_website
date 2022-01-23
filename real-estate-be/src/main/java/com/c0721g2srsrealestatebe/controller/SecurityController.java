package com.c0721g2srsrealestatebe.controller;

import com.c0721g2srsrealestatebe.dto.AppUserDTO;
import com.c0721g2srsrealestatebe.jwt.JwtUtils;
import com.c0721g2srsrealestatebe.model.account.AppUser;
import com.c0721g2srsrealestatebe.model.customer.Customer;
import com.c0721g2srsrealestatebe.model.employee.Employee;
import com.c0721g2srsrealestatebe.payload.request.*;
import com.c0721g2srsrealestatebe.payload.response.JwtResponse;
import com.c0721g2srsrealestatebe.payload.response.MessageResponse;
import com.c0721g2srsrealestatebe.service.account.IAppUserService;
import com.c0721g2srsrealestatebe.service.account.impl.AppUserServiceImpl;
import com.c0721g2srsrealestatebe.service.account.impl.MyUserDetailsImpl;
import com.c0721g2srsrealestatebe.service.customer.impl.CustomerServiceImpl;
import com.c0721g2srsrealestatebe.service.employee.impl.EmployeeServiceImpl;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.google.api.client.json.jackson2.JacksonFactory;


import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public")
@CrossOrigin("http://localhost:4200")
public class SecurityController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AppUserServiceImpl appUserService;

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${google.clientId}")
    String googleClientId;

    @Value("${secretPsw}")
    String secretPsw;


    @PostMapping("/login")
    public ResponseEntity<Object> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        MyUserDetailsImpl myUserDetails = (MyUserDetailsImpl) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateToken(myUserDetails);

        List<String> roles = myUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        JwtResponse jwtResponse = new JwtResponse();
        String urlImgDefault = "https://cdyduochopluc.edu.vn/wp-content/uploads/2019/07/anh-dai-dien-FB-200-1.jpg";

        if (roles.contains("ROLE_CUSTOMER")) {
            Customer customer = customerService.getCustomerByUsername(myUserDetails.getUsername());
            jwtResponse.setName(customer.getName());
            jwtResponse.setJwtToken(jwtToken);
            jwtResponse.setUsername(myUserDetails.getUsername());
            jwtResponse.setEmail(customer.getEmail());
            jwtResponse.setRoles(roles);
            jwtResponse.setIdCustomer(customer.getId());
            jwtResponse.setUrlImg(customer.getImage() == null ? urlImgDefault:customer.getImage().getUrl());
        } else {
            Employee employee = employeeService.getEmployeeByUsername(myUserDetails.getUsername());
            jwtResponse.setName(employee.getName());
            jwtResponse.setJwtToken(jwtToken);
            jwtResponse.setUsername(myUserDetails.getUsername());
            jwtResponse.setEmail(employee.getEmail());
            jwtResponse.setRoles(roles);
            jwtResponse.setIdCustomer(employee.getId());
            jwtResponse.setUrlImg(employee.getImage() == null ? urlImgDefault:employee.getImage().getUrl());
        }

        return ResponseEntity.ok(jwtResponse);

    }

    @PostMapping("/send-verification-email")
    public ResponseEntity<Object> reset(@RequestBody LoginRequest loginRequest) throws MessagingException, UnsupportedEncodingException {
        if (appUserService.existsUserByEmail(loginRequest.getEmail())) {
            appUserService.addVerificationCode(loginRequest.getEmail());
            return ResponseEntity.ok(new MessageResponse("Đã gửi email xác nhận"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Tài khoản không tồn tại"));
    }

    @PostMapping("/check-verification-code")
    public ResponseEntity<Object> checkVerificationCode(@RequestBody VerifyRequest verifyRequest) {
        Boolean isVerified = appUserService.findUserByVerificationCode(verifyRequest.getCode());
        if (Boolean.TRUE.equals(isVerified)) {
            return ResponseEntity.ok(new MessageResponse("valid"));
        } else {
            return ResponseEntity.ok(new MessageResponse("invalid"));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> doResetPassword(@Valid @RequestBody ResetPasswordRequest resetPassRequest) {
        if (resetPassRequest.getNewPassword().equals(resetPassRequest.getReNewPassword())) {
            appUserService.saveNewPassword(bCryptPasswordEncoder.encode(resetPassRequest.getNewPassword()), resetPassRequest.getVerificationCode());
            return ResponseEntity.ok(new MessageResponse("success"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("error"));

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @PostMapping("/google")
    public ResponseEntity<Object> google(@RequestBody TokenSocialRequest token) throws IOException {
        final NetHttpTransport transport = new NetHttpTransport();
        final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder verifier =
                new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                        .setAudience(Collections.singletonList(googleClientId));
        final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), token.getToken());
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();
        System.out.println("payload" + payload.toPrettyString());


        AppUser appUser;
        if (appUserService.existsUserByEmail(payload.getEmail())) {
            appUser = appUserService.getAppUserByEmail(payload.getEmail());
        } else {
            CustomerSocial customerSocial = new CustomerSocial();
            customerSocial.setEmail(payload.getEmail());
            customerSocial.setName(payload.get("name").toString());
            customerSocial.setUrlImg(payload.get("picture").toString());
            customerSocial.setPassword(bCryptPasswordEncoder.encode(secretPsw));

            appUser = appUserService.createCustomerSocial(customerSocial);

        }

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(appUser.getUsername());
        loginRequest.setPassword(secretPsw);
        return authenticateUser(loginRequest);
    }

    @PostMapping("/facebook")
    public ResponseEntity<Object> facebook(@RequestBody TokenSocialRequest tokenSocialRequest) {
        Facebook facebook = new FacebookTemplate(tokenSocialRequest.getToken());
        final String[] fields = {"email", "name"};
        User user = facebook.fetchObject("me", User.class, fields);

        AppUser appUser;
        if (appUserService.existsUserByEmail(user.getEmail())) {
            appUser = appUserService.getAppUserByEmail(user.getEmail());

        } else {
            String urlImg = facebook.getBaseGraphApiUrl()  + user.getId() + "/picture";

            CustomerSocial customerSocial = new CustomerSocial();
            customerSocial.setEmail(user.getEmail());
            customerSocial.setName(user.getName());
            customerSocial.setUrlImg(urlImg);
            customerSocial.setPassword(bCryptPasswordEncoder.encode(secretPsw));

            appUser = appUserService.createCustomerSocial(customerSocial);
        }
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(appUser.getUsername());
        loginRequest.setPassword(secretPsw);
        return authenticateUser(loginRequest);
    }

    //Thien

    @Autowired
    IAppUserService iAppUserService;

    @PatchMapping(value = "/password")
    public ResponseEntity<Object> update(@Valid @RequestBody AppUserDTO appUserDTO,
                                         BindingResult bindingResult) {
        try {
            AppUser appUser1 = iAppUserService.findAppUserByUserName(appUserDTO.getUsername());
            new AppUserDTO().validate(appUserDTO, bindingResult);
            if (bindingResult.hasFieldErrors("password")) {
                System.out.println("mật nhập không đúng form");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            System.out.println(appUserDTO.toString());
            if (bCryptPasswordEncoder.matches(
                    appUserDTO.getPassword(),appUser1.getPassword())
                    && !appUserDTO.getNewPassword().equals(appUserDTO.getPassword())
                    && appUserDTO.getNewPassword().equals(appUserDTO.getReNewPassword())) {

                appUserDTO.setPassword(bCryptPasswordEncoder.encode(appUserDTO.getNewPassword()));
                System.out.println("chưa lưu");
                System.out.println(appUserDTO.toString());
                iAppUserService.updatePassword(appUserDTO);
                System.out.println("đã lưu");
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                System.out.println("nhập sai password");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<AppUser> update(@RequestBody AppUserDTO password) {
        try {
            iAppUserService.updatePassword(password);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}