package com.map.harass.login.service;

import com.map.harass.entity.Agent;
import com.map.harass.repository.AgentRepository;
import com.map.harass.login.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    AgentRepository agentRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Agent agent=agentRepository.findByemail(username);
        return new CustomUserDetails(agent);
    }
}
