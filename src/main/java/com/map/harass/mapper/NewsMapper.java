package com.map.harass.mapper;

import com.map.harass.entity.News;
import com.map.harass.dto.NewsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    NewsMapper questionMapper = Mappers.getMapper(NewsMapper.class);

    NewsDTO newsToNewsDTO(News news);
    News newsDtoToNews(NewsDTO newsDTO);
    List<NewsDTO> newsListToNewsDTOList(List<News> newsList);

}
