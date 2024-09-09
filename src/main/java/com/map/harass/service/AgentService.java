package com.map.harass.service;

import com.map.harass.entity.Agent;
import com.map.harass.dto.AgentDTO;
import com.map.harass.dto.MessageResponse;
import com.map.harass.dto.ResponseAgentSurveyDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AgentService {
   ResponseEntity <List<AgentDTO>> getAllUsers();
    MessageResponse createUser(Agent user);

    ResponseEntity<MessageResponse> updateUser(Long id, Agent updatedUser);

    MessageResponse deleteUser(Long id);


    ResponseEntity<AgentDTO> getAgentById(Long id);

    ResponseEntity<ResponseAgentSurveyDTO> getAllSurveyByAgentID(Long agentId);
    ResponseEntity<ResponseAgentSurveyDTO> getAllSurvey();

    ResponseEntity<List<AgentDTO>> getAllAgent();
}
