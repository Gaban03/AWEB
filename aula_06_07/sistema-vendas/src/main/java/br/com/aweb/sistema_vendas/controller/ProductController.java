package br.com.aweb.sistema_vendas.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.aweb.sistema_vendas.model.Product;
import br.com.aweb.sistema_vendas.service.ProductService;
import jakarta.validation.Valid;



@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    // Formulário de cadastro
    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("product/form", Map.of("product", new Product()));
    }

    // Salvar produto
    @PostMapping("/create")
    public String create(@Valid Product product, BindingResult result) {
        if (result.hasErrors())
            return "product/form";

        productService.save(product);
        return "redirect:/products";
    }

    // Formulário de edição
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        return new ModelAndView("product/form", Map.of("product", productService.productById(id).get()));
    }

    // Salvar produto
    @PostMapping("/edit/{id}")
    public String edit(@Valid Product product, BindingResult result) {
        if (result.hasErrors())
            return "product/form";

        productService.save(product);
        return "redirect:/products";
    }

    // Deletar produto
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products";
    }

    @GetMapping
    public ModelAndView home() {
        return new ModelAndView("product/home", Map.of("products", productService.list()));
    }
    
}
