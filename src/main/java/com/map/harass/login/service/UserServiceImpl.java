package com.map.harass.login.service;

import com.map.harass.entity.Agent;
import com.map.harass.exception.JwtValidationException;
import com.map.harass.repository.AgentRepository;
import com.map.harass.dto.AgentDTO;
import com.map.harass.dto.LoginRequest;
import com.map.harass.dto.LoginResponse;
import com.map.harass.login.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JWTUtil jwtUtil;
    public Agent findByUsername(String username)  {
        return agentRepository.findByemail(username);
    }

    public LoginResponse authenticate(LoginRequest loginRequest) throws Exception {
        Agent agent = findByUsername(loginRequest.getUsername());
        LoginResponse loginResponse = new LoginResponse();
        AgentDTO agentDto = new AgentDTO();
        agentDto.setEmail(loginRequest.getUsername());

        if (agent == null) {
            throw new JwtValidationException(HttpStatus.NOT_FOUND,"Can not find the username");
        } else if (!passwordEncoder.matches(loginRequest.getPassword(), agent.getPassword())) {
            throw new JwtValidationException(HttpStatus.NOT_FOUND,"Password do not match");
        }

        if (agent.getIsVerified()) {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        )
                );
            } catch (BadCredentialsException ex) {
                throw new Exception("Bad Credential Exception", ex);
            }

            final UserDetails userDetails = userDetailsService.loadUserByUsername(
                    loginRequest.getUsername());

            final String token = jwtUtil.generateToken(userDetails.getUsername());

            loginResponse.setJwtToken(token);
            loginResponse.setIsVerified(true);
            loginResponse.setFirstname(agent.getAgentName());
            loginResponse.setRole(agent.getRole());
            loginResponse.setAgentID(agent.getAgentID());
            return loginResponse;
        }
        return loginResponse;
    }
}
