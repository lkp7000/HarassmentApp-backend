package com.map.harass.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse implements Serializable {

    @Getter
    @Setter
    private String jwtToken;
    @Getter @Setter
    private String role;
    @Getter @Setter
    private String firstname;
    @Getter @Setter
    private Boolean isVerified;
    @Getter @Setter
    private Long agentID;


}
