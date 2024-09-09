package com.map.harass.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.map.harass.entity.*;
import com.map.harass.repository.*;
import com.map.harass.dto.QuestionAnswerDTO;
import com.map.harass.dto.ResponseQuestionAnswerDTO;
import com.map.harass.mapper.QuestionAnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService {
    @Autowired
    private QuestionAnswerMapper questionAnswerMapper;
    @Autowired
    private QuestionAnswerRepo questionAnswerRepo;

    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private AgentSurveyRepository agentSurveyRepository;
    @Autowired
    private QuestionOptionsRepository questionOptionsRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private QuestionGroupRepository questionGroupRepository;


    @Override
    public List<QuestionAnswerDTO> insertAnswers(List<QuestionAnswerDTO> questionAnswerDTO) {
        List<QuestionAnswerDTO> insertedAnswers = new ArrayList<>();
        QuestionOptions questionOptions=new QuestionOptions();

        for (QuestionAnswerDTO questionAnswer : questionAnswerDTO) {
            AgentSurvey agentSurvey1 = agentSurveyRepository.findById(questionAnswer.getAgentSurveyID()).orElse(null);

            if (agentSurvey1 != null) {
                List<QuestionAnswer> existingAnswers = questionAnswerRepo.findByAgentSurveyID(agentSurvey1);

                boolean foundExistingAnswer = false;

                for (QuestionAnswer existingAnswer : existingAnswers) {
                    if (existingAnswer.getQuestionAnswerID().equals(questionAnswer.getQuestionAnswerID())) {
                        if(questionAnswer.getSelectedQuestionOptionID()!= null){
                            existingAnswer.setSelectedQuestionOptionID(questionAnswer.getSelectedQuestionOptionID());
                            questionOptions=questionOptionsRepository.findById(existingAnswer.getSelectedQuestionOptionID()).orElse(null);
                            existingAnswer.setQuestionAnswerText(questionOptions.getOptionText());
                        }
                        else {
                            existingAnswer.setQuestionAnswerText(questionAnswer.getQuestionAnswerText());
                        }
                        processPersonalAddress(existingAnswer.getQuestionID(), questionAnswer, agentSurvey1);
                        existingAnswer = questionAnswerRepo.save(existingAnswer);
                        QuestionAnswerDTO updatedAnswerDTO = mapEntityToQuestionAnswerDTO(existingAnswer, questionAnswer.getAgentSurveyID());
                        insertedAnswers.add(updatedAnswerDTO);
                        foundExistingAnswer = true;
                        break;
                    }
                }

                if (!foundExistingAnswer) {

                    QuestionAnswer answers = new QuestionAnswer();
                    mapQuestionAnswerDTOToEntity(questionAnswer, answers);
                    Questions questions = questionsRepository.findById(questionAnswer.getQuestionID()).orElse(null);
                    answers.setQuestionID(questions);
                    answers.setAgentSurveyID(agentSurvey1);

                    processPersonalAddress(questions, questionAnswer, agentSurvey1);

                    if (answers.getSelectedQuestionOptionID() != null) {
                         questionOptions = questionOptionsRepository.findById(answers.getSelectedQuestionOptionID()).orElse(null);
                        answers.setQuestionAnswerText(questionOptions.getOptionText());
                    }

                    answers = questionAnswerRepo.save(answers);

                    QuestionAnswerDTO insertedAnswerDTO = mapEntityToQuestionAnswerDTO(answers, questionAnswer.getAgentSurveyID());
                    insertedAnswers.add(insertedAnswerDTO);
                }
            }
        }

        return insertedAnswers;
    }

    private void mapQuestionAnswerDTOToEntity(QuestionAnswerDTO source, QuestionAnswer destination) {
        destination.setQuestionAnswerID(source.getQuestionAnswerID());
        destination.setQuestionAnswerText(source.getQuestionAnswerText());
        destination.setSelectedQuestionOptionID(source.getSelectedQuestionOptionID());
    }

    private void processPersonalAddress(Questions questions, QuestionAnswerDTO questionAnswer, AgentSurvey agentSurvey) {
        if (("personal_address").equalsIgnoreCase(questions.getShortText())) {
            String inputString = questionAnswer.getQuestionAnswerText();
            String cleanedString = inputString.substring(1, inputString.length() - 1);
            String[] parts = cleanedString.replaceAll("[{}]", "").split(",");
            String latitude = parts[0];
            String longitude = parts[1];

            if (latitude != null) {
                String url = "https://nominatim.openstreetmap.org/reverse?format=json&lat=" + latitude + "&lon=" + longitude;
                RestTemplate restTemplate = new RestTemplate();
                String response1 = restTemplate.getForObject(url, String.class);
                ObjectMapper objectMapper = new ObjectMapper();

                try {
                    JsonNode jsonNode = objectMapper.readTree(response1);
                    String locationName = jsonNode.path("display_name").asText();
                    agentSurvey.setLatitude(latitude);
                    agentSurvey.setLongitude(longitude);
                    agentSurvey.setFullAddress(locationName);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private QuestionAnswerDTO mapEntityToQuestionAnswerDTO(QuestionAnswer source, Long agentSurveyID) {
        QuestionAnswerDTO result = new QuestionAnswerDTO();
        result.setAgentSurveyID(agentSurveyID);
        result.setQuestionAnswerID(source.getQuestionAnswerID());
        result.setQuestionAnswerText(source.getQuestionAnswerText());
        result.setQuestionID(source.getQuestionID().getQuestionID());
        result.setSelectedQuestionOptionID(source.getSelectedQuestionOptionID());
        return result;
    }


    @Override
    public List<QuestionAnswerDTO> getAllAnswers() {
        List<QuestionAnswer> questionAnswers = questionAnswerRepo.findAll();
        List<QuestionAnswerDTO> questionAnswerDTOs = new ArrayList<>();

        for (QuestionAnswer questionAnswer : questionAnswers) {
            QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO();

            questionAnswerDTO.setQuestionAnswerID(questionAnswer.getQuestionAnswerID());
            questionAnswerDTO.setQuestionID(questionAnswer.getQuestionID().getQuestionID());
            questionAnswerDTO.setQuestionAnswerText(questionAnswer.getQuestionAnswerText());
            questionAnswerDTO.getQuestionAnswerText();
            questionAnswerDTO.setSelectedQuestionOptionID(questionAnswer.getSelectedQuestionOptionID());
            questionAnswerDTOs.add(questionAnswerDTO);
        }

        return questionAnswerDTOs;
    }

    @Override
    public List<QuestionAnswerDTO> getallQuestionsAnswerBySurveyID(Long agentsurveyID) {
        List<QuestionAnswerDTO> questionAnswerDTOList = new ArrayList<>();
        AgentSurvey agentSurvey = agentSurveyRepository.findById(agentsurveyID).orElse(null);

        if (agentSurvey != null) {
            List<QuestionAnswer> questionAnswersList = questionAnswerRepo.findByAgentSurveyID(agentSurvey);

            for (QuestionAnswer questionAnswer : questionAnswersList) {
                QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO(); // Create a new instance for each iteration

                questionAnswerDTO.setAgentSurveyID(questionAnswer.getAgentSurveyID().getAgentSurveyID());
                questionAnswerDTO.setQuestionAnswerText(questionAnswer.getQuestionAnswerText());
                questionAnswerDTO.setQuestionAnswerID(questionAnswer.getQuestionAnswerID());
                questionAnswerDTO.setQuestionID(questionAnswer.getQuestionID().getQuestionID()); // Fix typo here
                questionAnswerDTO.setSelectedQuestionOptionID(questionAnswer.getSelectedQuestionOptionID());

                questionAnswerDTOList.add(questionAnswerDTO);
            }
        }

        return questionAnswerDTOList;
    }

   @Override
   public List<ResponseQuestionAnswerDTO> getAllQuestionsAnswer(Long agentSurveyID) {
    AgentSurvey agentSurvey = agentSurveyRepository.findById(agentSurveyID).orElse(null);

    if (agentSurvey == null) {
        return List.of();
    }

    List<QuestionAnswer> questionAnswerList = questionAnswerRepo.findByAgentSurveyID(agentSurvey);

    ForkJoinPool forkJoinPool = new ForkJoinPool();
    List<ResponseQuestionAnswerDTO> listResponseQuestionAnswerDTOS = forkJoinPool.submit(() ->
            questionAnswerList.parallelStream()
                    .map(this::mapToResponseDTO)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList())
    ).join();

    return listResponseQuestionAnswerDTOS;
}

    private Optional<ResponseQuestionAnswerDTO> mapToResponseDTO(QuestionAnswer questionAnswer) {
        ResponseQuestionAnswerDTO responseQuestionAnswerDTO = new ResponseQuestionAnswerDTO();
        responseQuestionAnswerDTO.setQuestionAnswerId(questionAnswer.getQuestionAnswerID());
        responseQuestionAnswerDTO.setQuestionAnswerText(questionAnswer.getQuestionAnswerText());

        Questions questions = getCachedQuestions(questionAnswer.getQuestionID().getQuestionID());
        if (questions != null) {
            QuestionGroup questionGroup = getCachedQuestionGroup(questions.getQuestionGroupID().getQuestionGroupId());
            if (questionGroup != null) {
                responseQuestionAnswerDTO.setQuestionGroup(questionGroup.getQuestionGroupName());
            }

            responseQuestionAnswerDTO.setQuestionId(questionAnswer.getQuestionID().getQuestionID());
            responseQuestionAnswerDTO.setQuestionText(questions.getQuestionText());
            return Optional.of(responseQuestionAnswerDTO);
        }

        return Optional.empty();
    }

    private Questions getCachedQuestions(Long questionId) {
        Map<Long, Questions> questionsCache = new ConcurrentHashMap<>();
        return questionsCache.computeIfAbsent(questionId, id ->
                questionsRepository.findById(id).orElse(null));
    }

    private QuestionGroup getCachedQuestionGroup(Long questionGroupId) {
        Map<Long, QuestionGroup> questionGroupCache = new ConcurrentHashMap<>();
        return questionGroupCache.computeIfAbsent(questionGroupId, id ->
                questionGroupRepository.findById(id).orElse(null));
    }

}
