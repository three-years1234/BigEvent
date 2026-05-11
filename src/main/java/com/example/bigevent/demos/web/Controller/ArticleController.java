package com.example.bigevent.demos.web.Controller;

import com.example.bigevent.demos.web.Mapper.ArticleMapper;
import com.example.bigevent.demos.web.Service.ArticleService;
import com.example.bigevent.demos.web.entity.Article;
import com.example.bigevent.demos.web.entity.PageBean;
import com.example.bigevent.demos.web.entity.Result;
import com.example.bigevent.demos.web.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping
    public Result add(@RequestBody @Validated Article  article){
        articleService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum, Integer pageSize,
                                          @RequestParam(required = false) String categoryId,
                                          @RequestParam(required = false) String state){
            PageBean<Article> pageBean=articleService.list(pageNum,pageSize,categoryId,state);
            return Result.success(pageBean);
    }

    @GetMapping("/detail")
    public Result<Article> detail(@RequestParam Integer id){
        Article article=articleService.detail(id);
        return Result.success(article);
    }

    @PutMapping
    public Result update(@RequestBody Article article){
        articleService.update(article);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam Integer id){
        if(articleService.detail(id)==null) return Result.error("未找到该文章");
        articleService.delete(id);
        return Result.success();
    }

}
