package com.map.harass.service;

import com.map.harass.dto.NewsDTO;

import java.util.List;

public interface NewsService {
    NewsDTO saveNews(NewsDTO news);

    List<NewsDTO> getAllNews();
}
