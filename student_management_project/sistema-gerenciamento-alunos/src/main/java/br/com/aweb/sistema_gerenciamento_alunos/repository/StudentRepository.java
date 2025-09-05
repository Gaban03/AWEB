package br.com.aweb.sistema_gerenciamento_alunos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.aweb.sistema_gerenciamento_alunos.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByNameLike(String name);
    Optional<Student> findByEmail(String email);
    List<Student> findByAge(Integer age);
    List<Student> findByCourseLike(String course);
}
