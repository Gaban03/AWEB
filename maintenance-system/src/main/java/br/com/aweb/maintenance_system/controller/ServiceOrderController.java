package br.com.aweb.maintenance_system.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.aweb.maintenance_system.model.ServiceOrder;
import br.com.aweb.maintenance_system.repository.ServiceOrderRepository;
import jakarta.validation.Valid;



@Controller
@RequestMapping("/service-order")
public class ServiceOrderController {
    
    @Autowired
    ServiceOrderRepository serviceOrderRepository;

    @GetMapping
    public ModelAndView home() {
        return new ModelAndView("home", Map.of("serviceOrders", serviceOrderRepository.findAll(Sort.by("createdAt"))));
    }
    
    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("form", Map.of("serviceOrder", new ServiceOrder()));
    }

        @PostMapping("/create")
    public String create(@Valid ServiceOrder serviceOrder, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors())
            return "form";

        serviceOrderRepository.save(serviceOrder);

        return "redirect:/service-order";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        Optional<ServiceOrder> serviceOrder = serviceOrderRepository.findById(id);

        if (serviceOrder.isPresent() && serviceOrder.get().getFinishedAt() == null)
            return new ModelAndView("form", Map.of("serviceOrder", serviceOrder.get()));

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/edit/{id}")
    public String edit(@Valid ServiceOrder serviceOrder, BindingResult result) {
        if (result.hasErrors())
            return "form";

        serviceOrderRepository.save(serviceOrder);

        return "redirect:/service-order";
    }

    @PostMapping("/delete/{id}")
    public String delete(ServiceOrder serviceOrder) {
        serviceOrderRepository.delete(serviceOrder);
        return "redirect:/service-order";
    }

    @PostMapping("/finish/{id}")
    public String finish(@PathVariable Long id) {
        var optionalServiceOrder = serviceOrderRepository.findById(id);

        if (optionalServiceOrder.isPresent()) {
            var serviceOrder = optionalServiceOrder.get();
            serviceOrder.setFinishedAt(LocalDateTime.now());
            serviceOrderRepository.save(serviceOrder);
            return "redirect:/service-order";
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
