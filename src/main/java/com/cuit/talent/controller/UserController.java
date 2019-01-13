package com.cuit.talent.controller;

import com.cuit.talent.utils.valueobj.Token;
import com.cuit.talent.service.UserService;
import com.cuit.talent.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private Token token;


    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseEntity userLogin(){
        return ResponseEntity.ok(null);
    }
}
