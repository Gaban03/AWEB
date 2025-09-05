package br.com.aweb.sistema_gerenciamento_alunos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.aweb.sistema_gerenciamento_alunos.model.Student;
import br.com.aweb.sistema_gerenciamento_alunos.service.StudentService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String getAllStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "home";
    }

    @GetMapping("/search")
    public String searchStudents(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String course,
            Model model) {

        try {
            List<Student> students = studentService.getSearchFilter(id, name, email, age, course);
            model.addAttribute("students", students);

            if (students.isEmpty()) {
                model.addAttribute("error", "Nenhum aluno encontrado com os filtros informados.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Ocorreu um erro ao buscar os alunos. " + e.getMessage());
            model.addAttribute("students", studentService.getAllStudents());
        }

        return "home";
    }


    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("student", new Student());
        return "formCreate";
    }

    @PostMapping
    public String createStudent(@Valid Student student,
            BindingResult result,
            RedirectAttributes attributes) {
        if (result.hasErrors())
            return "formCreate";

        studentService.createStudent(student);
        attributes.addFlashAttribute("message", "Estudante criado com sucesso!");
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        try {
            model.addAttribute("student", studentService.getStudentById(id));
            return "formEdit";
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/students";
        }
    }

    @PostMapping("/edit/{id}")
    public String editStudent(@PathVariable Long id,
            @Valid Student student,
            BindingResult result,
            RedirectAttributes attributes) {

        if (result.hasErrors())
            return "formEdit";

        studentService.updateStudent(id, student);
        attributes.addFlashAttribute("message", "Estudante atualizado com sucesso!");
        return "redirect:/students";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            studentService.deleteStudent(id);
            attributes.addFlashAttribute("message", "Estudante removido com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/students";
    }

}
