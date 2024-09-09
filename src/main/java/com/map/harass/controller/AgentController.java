package com.map.harass.controller;

import com.map.harass.entity.Agent;
import com.map.harass.service.AgentService;
import com.map.harass.dto.AgentDTO;
import com.map.harass.dto.MessageResponse;
import com.map.harass.dto.ResponseAgentSurveyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/api")

public class AgentController {
    @Autowired
    private AgentService agentService;


    @GetMapping("/getallagent")
    public ResponseEntity<List<AgentDTO>> getAllUsers() {
        return agentService.getAllUsers();
    }

    @GetMapping("/getAgent/{id}")
    public ResponseEntity<AgentDTO>getAgentbyID(@PathVariable Long id){
        return agentService.getAgentById(id);
    }

    @PostMapping("/addagent")
    public MessageResponse createUser(@RequestBody Agent user) {
        return agentService.createUser(user);
    }

    @PutMapping("/updateagent/{id}")
    public ResponseEntity<MessageResponse> updateUser(@PathVariable Long id, @RequestBody Agent updatedUser) {
        return agentService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/deleteagent/{id}")
    public MessageResponse deleteUser(@PathVariable Long id) {
        return agentService.deleteUser(id);
    }

    @GetMapping("/getAllAgentList")
    public ResponseEntity<List<AgentDTO>>getAllAgent(){
        return agentService.getAllAgent();
    }

    @GetMapping("/getAllSurveyByAgentID/{agentId}")
    public ResponseEntity<ResponseAgentSurveyDTO>getAllSurveyByAgentID(@PathVariable Long agentId){
        return agentService.getAllSurveyByAgentID(agentId);
    }
    @GetMapping("/getAllSurvey")
    public ResponseEntity<ResponseAgentSurveyDTO>getAllSurveyByAgentID(){
        return agentService.getAllSurvey();
    }

}
