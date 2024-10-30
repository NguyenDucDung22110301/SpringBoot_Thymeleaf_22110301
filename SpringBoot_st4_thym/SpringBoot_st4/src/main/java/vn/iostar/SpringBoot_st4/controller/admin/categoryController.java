package vn.iostar.SpringBoot_st4.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.iostar.SpringBoot_st4.entity.Category;
import vn.iostar.SpringBoot_st4.model.CategoryModel;
import vn.iostar.SpringBoot_st4.service.CategoryService;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/categories")
public class categoryController {
    @Autowired
    CategoryService categoryService;

//    @GetMapping
//    public String All(Model model){
////        return "admin/category-list";
//        List<Category> categories = categoryService.findAll();
//        model.addAttribute("list", categories);
//        return"admin/category/list1";
//    }
    @GetMapping("/add")
    public String add(Model model){
        CategoryModel category = new CategoryModel();
        category.setIsEdit(false);
        model.addAttribute("category", category);
        return "admin/category/add";
    }
    @PostMapping("/save")
    public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("category") CategoryModel cateModel,
                                     BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("admin/category/add");
        }
        Category entity = new Category();
        BeanUtils.copyProperties(cateModel, entity);
        categoryService.save(entity);
//        String message = "";
//        if (cateModel.getIsEdit() == true) {
//            message = "Category is Edited !!!!!!";
//        } else {
//            message = "Category is Saved !!!!";
//        }
//        model.addAttribute("message", message);
        return new ModelAndView("redirect:/admin/categories");
    }
    @GetMapping("/edit/{id}")
    public ModelAndView edit(ModelMap model, @PathVariable("id") Long categoryID){
        Optional<Category> optionalCategory = categoryService.findById(categoryID);
        CategoryModel categoryModel = new CategoryModel();
        if(optionalCategory.isPresent()){
            Category entity = optionalCategory.get();
            BeanUtils.copyProperties(entity, categoryModel);
            categoryModel.setIsEdit(true);
            model.addAttribute("category",categoryModel);
            return new ModelAndView("admin/category/add", model);
        }
        return new ModelAndView("redirect:/admin/categories");
    }
    @GetMapping("delete/{id}")
    public ModelAndView delete (ModelMap model, @PathVariable("id") Long categoryID){
        categoryService.deleteById(categoryID);
        model.addAttribute("message","category is deleted!! ");
        return new ModelAndView("forward:/admin/categories", model);
    }
    @GetMapping
    public String search(ModelMap model,
                         @RequestParam(name="name",required = false) String name,
                         @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size) {
        int count = (int) categoryService.count();
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);
        Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.by("name"));
        Page<Category> resultPage = null;
        if(StringUtils.hasText(name)) {
            resultPage = categoryService.findByNameContaining(name,pageable);
            model.addAttribute("name",name);
        }else {
            resultPage = categoryService.findAll(pageable);
        }
        int totalPages = resultPage.getTotalPages();
        if(totalPages > 0) {
            int start = Math.max(1, currentPage-2);
            int end = Math.min(currentPage + 2, totalPages);
            if(totalPages > count) {
                if(end == totalPages) start = end - count;
                else if (start == 1) end = start + count;
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers",pageNumbers);
        }
        model.addAttribute("categoryPage",resultPage);
        return "admin/category/list1";
    }
}
