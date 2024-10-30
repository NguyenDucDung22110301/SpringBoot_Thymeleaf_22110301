package vn.iostar.SpringBoot_st4.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.iostar.SpringBoot_st4.entity.Category;
import vn.iostar.SpringBoot_st4.model.CategoryModel;
import vn.iostar.SpringBoot_st4.service.CategoryService;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/categories")
public class categoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public String All(Model model){
//        return "admin/category-list";
        List<Category> categories = categoryService.findAll();
        model.addAttribute("list", categories);
        return"admin/category/list";
    }
    @GetMapping("/add")
    public String add(Model model){
        CategoryModel category = new CategoryModel();
        category.setIsEdit(false);
        model.addAttribute("category", category);
        return "admin/category/add";
    }
    @PostMapping("/save")
    public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("category") CategoryModel category,
                                BindingResult result ){
        if(result.hasErrors()){
            return new ModelAndView("admin/category/add");
        }
        Category entity = new Category();
        BeanUtils.copyProperties(category, entity);
        categoryService.save(entity);
//        String message = "";
//        if(category.getIsEdit() == true){
//            message = "Category is Edited!!!!!!";
//        }else {
//            message = "category is save !!!!!!";
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
}
