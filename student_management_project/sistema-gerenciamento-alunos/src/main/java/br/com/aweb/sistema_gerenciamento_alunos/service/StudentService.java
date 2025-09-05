package br.com.aweb.sistema_gerenciamento_alunos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.aweb.sistema_gerenciamento_alunos.model.Student;
import br.com.aweb.sistema_gerenciamento_alunos.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student.get();
        } else {
            throw new RuntimeException("Não foi possível encontrar o aluno com id: " + id);
        }
    }

    public List<Student> getStudentsByName(String name) {
        List<Student> students = studentRepository.findByNameLike("%" + name + "%");
        if (!students.isEmpty()) {
            return students;
        } else {
            throw new RuntimeException("Não foi possível encontrar o aluno com nome: " + name);
        }
    }

    public Student getStudentByEmail(String email) {
        Optional<Student> student = studentRepository.findByEmail(email);
        if (student.isPresent()) {
            return student.get();
        } else {
            throw new RuntimeException("Não foi possível encontrar o aluno com email: " + email);
        }
    }

    public List<Student> getStudentsByAge(Integer age) {
        List<Student> students = studentRepository.findByAge(age);
        if (!students.isEmpty()) {
            return students;
        } else {
            throw new RuntimeException("Não foi possível encontrar alunos com idade: " + age);
        }
    }

    public List<Student> getStudentsByCourse(String course) {
        List<Student> students = studentRepository.findByCourseLike("%" + course + "%");
        if (!students.isEmpty()) {
            return students;
        } else {
            throw new RuntimeException("Não foi possível encontrar alunos com curso: " + course);
        }
    }

    public List<Student> getSearchFilter(Long id, String name, String email, Integer age, String course) {
        List<Student> students = new ArrayList<>();

        if (id != null) {
            students.add(getStudentById(id));
            return students;
        }

        if (name != null && !name.isBlank()) {
            students.addAll(getStudentsByName(name));
        }

        if (email != null && !email.isBlank()) {
            students.add(getStudentByEmail(email));
        }

        if (age != null) {
            students.addAll(getStudentsByAge(age));
        }

        if (course != null && !course.isBlank()) {
            students.addAll(getStudentsByCourse(course));
        }

        if (students.isEmpty()) {
            students = studentRepository.findAll();
        }

        return students;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student student) {
        if (studentRepository.existsById(id)) {
            student.setId(id);
            return studentRepository.save(student);
        } else {
            throw new RuntimeException("Não foi possível encontrar o aluno com id: " + id);
        }
    }

    public void deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Não foi possível encontrar o aluno com id: " + id);
        }
    }
}
