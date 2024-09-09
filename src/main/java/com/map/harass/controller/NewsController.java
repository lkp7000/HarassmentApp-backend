package com.map.harass.controller;

import com.map.harass.service.NewsService;
import com.map.harass.dto.NewsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/News")
@CrossOrigin("*")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping("/add")
    public ResponseEntity<NewsDTO> addNews(@RequestBody NewsDTO news) {
        try {
            NewsDTO savedNews = newsService.saveNews(news);
            return new ResponseEntity<>(savedNews, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        try {
            List<NewsDTO> newsList = newsService.getAllNews();
            return new ResponseEntity<>(newsList, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
