package com.openspection.service;


import com.openspection.model.Person;


import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class LoginService {


    @RequestMapping(value="authentication-failure", method = RequestMethod.GET)
    public ResponseEntity<String> apiAuthenticationFailure() {
        return new ResponseEntity<String>("Authentication failure.", HttpStatus.UNAUTHORIZED);
    }

}
