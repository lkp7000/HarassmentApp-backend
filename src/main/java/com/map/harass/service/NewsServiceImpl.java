package com.map.harass.service;

import com.map.harass.entity.News;
import com.map.harass.repository.NewsRepository;
import com.map.harass.dto.NewsDTO;
import com.map.harass.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService{

    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private NewsRepository newsRepository;

    @Override
    public NewsDTO saveNews(NewsDTO newsDTO) {
        News news = new News();
        news.setImage(newsDTO.getImage());
        news.setTitle(newsDTO.getTitle());
        news.setDescription(newsDTO.getDescription());
        news = newsRepository.save(news);
        newsDTO=newsMapper.newsToNewsDTO(news);
        return newsDTO;
    }

    @Override
    public List<NewsDTO> getAllNews() {
        List<News> listNews=newsRepository.findAll();
        List<NewsDTO> newsDTOList=newsMapper.newsListToNewsDTOList(listNews);
        return newsDTOList;
    }
}
