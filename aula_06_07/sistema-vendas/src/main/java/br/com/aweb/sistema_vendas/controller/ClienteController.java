package br.com.aweb.sistema_vendas.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import br.com.aweb.sistema_vendas.model.Cliente;
import br.com.aweb.sistema_vendas.service.ClienteService;
import jakarta.validation.Valid;



@Controller
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ModelAndView getAll(){
        return new ModelAndView("cliente/list", Map.of("customers", clienteService.getAllCustomer()));
    }

    @GetMapping("/new")
    public ModelAndView create() {
        return new ModelAndView("cliente/form", Map.of("customer", new Cliente()));
    }

    @PostMapping("/new")
    public String create(@Valid Cliente customer, BindingResult result) {
        if(result.hasErrors()){
            return "cliente/form";
        }        
        clienteService.add(customer);
        return "redirect:/clientes";
    }

     @GetMapping("/edit/{id}")
    public ModelAndView update(@PathVariable String id) {
        var optionalCustomer = clienteService.getById(id);
        if (optionalCustomer.isPresent()) {
            return new ModelAndView("cliente/form", Map.of("customer", optionalCustomer.get()));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid Cliente customer, BindingResult result) {
        if (result.hasErrors()) {
            return "cliente/form";
        }

        clienteService.update(customer.getCpf(), customer);

        return "redirect:/clientes";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable String id) {
        var optionalCustomer = clienteService.getById(id);
        if (optionalCustomer.isPresent()) {
            return new ModelAndView("cliente/delete", Map.of("customer", optionalCustomer.get()));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/delete/{id}")
    public String delete(Cliente customer) {
        clienteService.delete(customer.getCpf());
        return "redirect:/clientes";
    }
    
    

}
