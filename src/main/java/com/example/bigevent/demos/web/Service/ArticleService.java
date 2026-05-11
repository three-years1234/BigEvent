package com.example.bigevent.demos.web.Service;

import com.example.bigevent.demos.web.entity.Article;
import com.example.bigevent.demos.web.entity.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state);

    Article detail(Integer id);

    void update(Article  article);

    void delete(Integer id);
}
