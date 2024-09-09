package com.map.harass.controller;

import com.map.harass.service.FilterService;
import com.map.harass.service.QuestionAnswerService;
import com.map.harass.service.QuestionService;
import com.map.harass.service.SurveyService;
import com.map.harass.dto.QuestionAnswerDTO;
import com.map.harass.dto.QuestionsDTO;
import com.map.harass.dto.ResponseQuestionAnswerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/survey")
@CrossOrigin("*")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionAnswerService questionAnswerService;
    @Autowired
    private FilterService filterService;
    

    @PostMapping("/addquestions")
    public ResponseEntity<QuestionsDTO> addQuestionsForSurvey(@RequestBody QuestionsDTO questionsDTO) {
        List<QuestionsDTO> questions = questionService.addQuestionsForSurvey(questionsDTO);
        return ResponseEntity.ok(questionsDTO);
    }


    @GetMapping("/getallquestions/{agentID}")
    public ResponseEntity<List<QuestionsDTO>> getAllQuestionsForSurvey(@PathVariable Long agentID) {
        List<QuestionsDTO> questionsDto = questionService.getallQuestions(agentID);
        return ResponseEntity.ok(questionsDto);
    }

    @GetMapping("/getanswer/{agentsurveyID}")
    public ResponseEntity<List<QuestionAnswerDTO>> getAllAnswersFormSurveyId(@PathVariable Long agentsurveyID) {
        List<QuestionAnswerDTO> questionsAnswerBySurveyIDto = questionAnswerService.getallQuestionsAnswerBySurveyID(agentsurveyID);
        return ResponseEntity.ok(questionsAnswerBySurveyIDto);
    }

    @PostMapping("/submitanswers")
    public ResponseEntity<List<QuestionAnswerDTO>>addAnswer(@RequestBody List<QuestionAnswerDTO> questionAnswerDTO){

        List<QuestionAnswerDTO>questionAnswerDTOList= questionAnswerService.insertAnswers(questionAnswerDTO);
        return ResponseEntity.ok(questionAnswerDTOList);
    }

    @GetMapping("/getallanswers")
    public ResponseEntity<List<QuestionAnswerDTO>>getAllAnswers(){
        List<QuestionAnswerDTO>answerDTOS=questionAnswerService.getAllAnswers();
        return ResponseEntity.ok(answerDTOS);
    }

    @GetMapping("/getAllQuestionsAnswer/{agentsurveyID}")
    public ResponseEntity<List<ResponseQuestionAnswerDTO>>getAllQuestionsAnswer(@PathVariable Long agentsurveyID){
        List<ResponseQuestionAnswerDTO> listOfAllQuestionAnswerListDTO =questionAnswerService.getAllQuestionsAnswer(agentsurveyID);
        return ResponseEntity.ok(listOfAllQuestionAnswerListDTO);
    }
}
