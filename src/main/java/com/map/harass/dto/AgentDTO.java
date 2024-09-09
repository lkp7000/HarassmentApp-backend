package com.map.harass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentDTO {
    private Long agentID;
    private String agentName;
    private String email;
    private  String password;
    private String role;

}
