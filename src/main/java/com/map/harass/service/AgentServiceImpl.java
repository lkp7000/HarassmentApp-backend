package com.map.harass.service;

import com.map.harass.entity.Agent;
import com.map.harass.entity.AgentSurvey;
import com.map.harass.repository.AgentRepository;
import com.map.harass.repository.AgentSurveyRepository;
import com.map.harass.dto.AgentDTO;
import com.map.harass.dto.ListAgentSurveyDTO;
import com.map.harass.dto.MessageResponse;
import com.map.harass.dto.ResponseAgentSurveyDTO;
import com.map.harass.mapper.AgentMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private AgentSurveyRepository agentSurveyRepository;

    @Autowired
    private AgentMapper agentMapper;

    public ResponseEntity<List<AgentDTO>> getAllUsers() {
        List<Agent> agents = agentRepository.findAll();
        if (!agents.isEmpty()) {
            List<AgentDTO> activeAgents = agents.stream()
                    .filter(user -> user.getStatus().equalsIgnoreCase("active")) // Assuming status is a field in the AgentDTO class
                    .map(user -> {
                        AgentDTO dto = new AgentDTO();
                        dto.setAgentID(user.getAgentID());
                        dto.setAgentName(user.getAgentName());
                        dto.setEmail(user.getEmail());
                        dto.setRole(user.getRole());
                        dto.setPassword(user.getPassword());
                        return dto;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(activeAgents);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    public MessageResponse createUser(Agent user) {
        MessageResponse message=new MessageResponse();
        String plainPassword = user.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(plainPassword);
        Boolean verified=true;
        user.setPassword(hashedPassword);
        user.setAgentName(user.getAgentName());
        user.setRole("agent");
        user.setStatus("Active");
        user.setIsVerified(verified);
        agentRepository.save(user);
        message.setMessage("Created");
        return message;
    }

    public ResponseEntity<MessageResponse> updateUser(Long id, Agent newagent) {
        Agent agent =agentRepository.findById(id).orElse(null);
        MessageResponse messageResponse=new MessageResponse();
        if(agent!=null){
            agent.setAgentName(newagent.getAgentName());
            String plainPassword = newagent.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(plainPassword);
            agent.setPassword(hashedPassword);
            agent.setEmail(newagent.getEmail());
            agent.setStatus("Active");
            Agent updatedAgent=agentRepository.save(agent);
            messageResponse.setMessage("Agent Updated Successfully");
            return ResponseEntity.ok(messageResponse);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public MessageResponse deleteUser(Long id) {
        MessageResponse messageResponse = new MessageResponse();
        Optional<Agent> agentOptional = agentRepository.findById(id);

        if (agentOptional.isPresent()) {
            Agent agent = agentOptional.get();
            agent.setStatus("inactive");
            agent.setIsVerified(false);
            agentRepository.save(agent);

            messageResponse.setMessage("Agent deactivated");
        } else {
            messageResponse.setMessage("Agent not found");
        }

        return messageResponse;
    }

    @Override
    public ResponseEntity<AgentDTO> getAgentById(Long id) {
        Optional<Agent> agentOptional = agentRepository.findById(id);

        if (agentOptional.isPresent()) {
            Agent agent = agentOptional.get();
            AgentDTO agentDTO = new AgentDTO();
            agentDTO.setAgentName(agent.getAgentName());
            agentDTO.setEmail(agent.getEmail());
            agentDTO.setRole(agent.getRole());
            agentDTO.setPassword(agent.getPassword());

            return ResponseEntity.ok(agentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<ResponseAgentSurveyDTO> getAllSurveyByAgentID(Long agentId) {
        Agent agent = agentRepository.findById(agentId).orElse(null);

        if (agent == null) {
            return ResponseEntity.notFound().build();
        }

        List<ListAgentSurveyDTO> listAgentSurveyDTOS = agentSurveyRepository.findByAgentID(agent)
                .parallelStream() // Parallel processing
                .filter(agentSurvey -> !StringUtils.isEmpty(agentSurvey.getLatitude()) && !StringUtils.isEmpty(agentSurvey.getLongitude()))
                .map(agentSurvey -> {
                    ListAgentSurveyDTO listAgentSurveyDTO = new ListAgentSurveyDTO();
                    listAgentSurveyDTO.setAgentId(agent.getAgentID());
                    listAgentSurveyDTO.setAgentName(agent.getAgentName());
                    listAgentSurveyDTO.setAgentSurveyId(agentSurvey.getAgentSurveyID());
                    listAgentSurveyDTO.setDate(agentSurvey.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    return listAgentSurveyDTO;
                })
                .collect(Collectors.toList());

        ResponseAgentSurveyDTO responseAgentSurveyDTO = new ResponseAgentSurveyDTO();
        responseAgentSurveyDTO.setListAgentSurveyDTOS(listAgentSurveyDTOS);
        responseAgentSurveyDTO.setTotalSurvey(listAgentSurveyDTOS.size());

        return ResponseEntity.ok(responseAgentSurveyDTO);
    }

    @Override
    public ResponseEntity<ResponseAgentSurveyDTO> getAllSurvey() {
        List<AgentSurvey> allSurveys = agentSurveyRepository.findAll();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<ListAgentSurveyDTO> listAgentSurveyDTOS = allSurveys.parallelStream() // Parallel processing
                .filter(agentSurvey -> !StringUtils.isEmpty(agentSurvey.getLatitude()) && !StringUtils.isEmpty(agentSurvey.getLongitude()))
                .map(agentSurvey -> {
                    ListAgentSurveyDTO listAgentSurveyDTO = new ListAgentSurveyDTO();
                    Agent agent = agentSurvey.getAgentID(); // Fetch agent once
                    listAgentSurveyDTO.setAgentId(agent.getAgentID());
                    listAgentSurveyDTO.setAgentName(agent.getAgentName());
                    listAgentSurveyDTO.setAgentSurveyId(agentSurvey.getAgentSurveyID());
                    listAgentSurveyDTO.setDate(agentSurvey.getCreationDate().format(dateFormatter));
                    return listAgentSurveyDTO;
                })
                .collect(Collectors.toList());

        ResponseAgentSurveyDTO responseAgentSurveyDTO = new ResponseAgentSurveyDTO();
        responseAgentSurveyDTO.setListAgentSurveyDTOS(listAgentSurveyDTOS);
        responseAgentSurveyDTO.setTotalSurvey(listAgentSurveyDTOS.size());

        return ResponseEntity.ok(responseAgentSurveyDTO);
    }

    @Override
    public ResponseEntity<List<AgentDTO>> getAllAgent() {
        List<Agent> agentList =agentRepository.findAll();
        List<AgentDTO> agentDTOList = agentList.stream()
                .filter(agent -> "agent".equals(agent.getRole()))
                .map(agentMapper::agentToAgentDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(agentDTOList);
    }


}
