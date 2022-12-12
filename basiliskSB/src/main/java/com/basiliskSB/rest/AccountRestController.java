package com.basiliskSB.rest;
import com.basiliskSB.dto.account.*;
import com.basiliskSB.service.abstraction.AccountService;
import com.basiliskSB.utility.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseTokenDTO post(@RequestBody RequestTokenDTO dto){
        try{
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            authenticationManager.authenticate(token);
        }catch (Exception exception){

        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        String role = accountService.getAccountRole(dto.getUsername());
        String token = jwtToken.generateToken(dto.getSubject(),dto.getUsername(), dto.getSecretKey(), role, dto.getAudience());
        ResponseTokenDTO response = new ResponseTokenDTO(dto.getUsername(), role, token);
        return response;
    }
}
