package com.example.module2.web.controllers;

import com.example.module2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

//    @Autowired
//    private MyUserDetailsService userDetailsService;

//    @Autowired
//    private AuthenticationManager authenticationManager;

//    @Autowired
//    public AuthenticationController(MyUserDetailsService userDetailsService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
//        this.userDetailsService = userDetailsService;
//        this.jwtUtil = jwtUtil;
//        this.authenticationManager = authenticationManager;
//    }

    @PostMapping
    public ResponseEntity<?> authenticate() throws Exception {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
//        );
//
//        final String jwt = jwtUtil.createToken(authentication);
//
//        return ResponseEntity.ok(new AuthenticationResponse(jwt));

//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
//            );
//        }catch (Exception ex) {
//            throw new Exception("Incorrect username or password!", ex);
//        }
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//        final String jwt = jwtUtil.generateToken(userDetails);
//
//        return ResponseEntity.ok(new AuthenticationResponse(jwt));
        return ResponseEntity.ok().build();
    }
}
