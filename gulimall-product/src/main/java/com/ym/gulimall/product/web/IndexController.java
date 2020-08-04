package com.ym.gulimall.product.web;

import com.ym.gulimall.product.entity.CategoryEntity;
import com.ym.gulimall.product.service.CategoryService;
import com.ym.gulimall.product.vo.Catelog2Vo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Resource
    private CategoryService categoryService;

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {
        // TODO 1 查出所有的1级分类
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categories();
        // 视图解析器进行拼串
        // classpath:/templates/ + result + .html
        model.addAttribute("categories", categoryEntities);
        return "index";
    }

    // index/catalog.json
    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatalogJson() throws InterruptedException {
        return categoryService.getCatalogJson();
    }

}
