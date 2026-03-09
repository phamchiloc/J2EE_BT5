package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String listProducts(@RequestParam(required = false) String search, Model model) {
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("products", productService.searchProducts(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute("products", productService.getAllProducts());
        }
        return "products/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", Category.values());
        return "products/form";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute Product product,
                           BindingResult bindingResult,
                           @RequestParam(required = false) MultipartFile imageFile,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        // Validate image filename length
        if (imageFile != null && !imageFile.isEmpty()) {
            String originalFilename = imageFile.getOriginalFilename();
            if (originalFilename != null && originalFilename.length() > 200) {
                bindingResult.rejectValue("image", "error.product", "Tên hình ảnh không quá 200 kí tự");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", Category.values());
            return "products/form";
        }

        try {
            productService.saveProduct(product, imageFile);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm sản phẩm thành công!");
            return "redirect:/products";
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Lỗi khi upload hình ảnh: " + e.getMessage());
            model.addAttribute("categories", Category.values());
            return "products/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return productService.getProductById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    model.addAttribute("categories", Category.values());
                    return "products/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sản phẩm!");
                    return "redirect:/products";
                });
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id,
                              @Valid @ModelAttribute Product product,
                              BindingResult bindingResult,
                              @RequestParam(required = false) MultipartFile imageFile,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        product.setId(id);

        // Validate image filename length
        if (imageFile != null && !imageFile.isEmpty()) {
            String originalFilename = imageFile.getOriginalFilename();
            if (originalFilename != null && originalFilename.length() > 200) {
                bindingResult.rejectValue("image", "error.product", "Tên hình ảnh không quá 200 kí tự");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", Category.values());
            return "products/form";
        }

        try {
            productService.saveProduct(product, imageFile);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sản phẩm thành công!");
            return "redirect:/products";
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Lỗi khi upload hình ảnh: " + e.getMessage());
            model.addAttribute("categories", Category.values());
            return "products/form";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa sản phẩm thành công!");
        return "redirect:/products";
    }
}
