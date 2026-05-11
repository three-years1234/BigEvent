package com.example.bigevent.demos.web.Service.Impl;

import com.example.bigevent.demos.web.Mapper.ArticleMapper;
import com.example.bigevent.demos.web.Mapper.CategoryMapper;
import com.example.bigevent.demos.web.Service.ArticleService;
import com.example.bigevent.demos.web.entity.Article;
import com.example.bigevent.demos.web.entity.Category;
import com.example.bigevent.demos.web.entity.PageBean;
import com.example.bigevent.demos.web.entity.Result;
import com.example.bigevent.demos.web.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public void add(Article article) {
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");
        article.setCreateUser(userId);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state) {
        //创建pagebean对象
        PageBean<Article> pageBean=new PageBean<>();
        //开启分页查询：pagehelper插件
        PageHelper.startPage(pageNum,pageSize);
        //调用mapper
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userId=(Integer) map.get("id");
        List<Article> articles=articleMapper.list(userId,categoryId,state);
        Page<Article> p=(Page<Article>) articles;
        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Override
    public Article detail(Integer id) {
        Article article=articleMapper.detail(id);
        return article;
    }

    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }
}
