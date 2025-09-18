package br.com.aweb.maintenance_system.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 100, message = "O mínimo de caracteres do campo é 3 e o máximo é 100.")
    @NotBlank(message = "Titulo é obrigatório.")
    @Column(nullable = false, length = 100)
    private String title;

    @NotBlank(message = "Nome do solicitante é obrigatório.")
    @Column(nullable = false, length = 50)
    private String nameApplicant;

    @NotBlank(message = "Descrição é obrigatória.")
    @Column(nullable = false, length = 250)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = true)
    private LocalDateTime finishedAt;
}
