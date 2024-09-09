package com.map.harass.mapper;

import com.map.harass.entity.Agent;
import com.map.harass.dto.AgentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AgentMapper {

    AgentMapper questionMapper = Mappers.getMapper(AgentMapper.class);

    AgentDTO agentToAgentDTO(Agent agent);
    Agent agentDtoToAgent(AgentDTO agentDTO);

    List<AgentDTO> agentListToAgentDTOList(List<Agent> agent);
    List<Agent> agentDtoListToAgentList(List<AgentDTO> agentDTO);
}
