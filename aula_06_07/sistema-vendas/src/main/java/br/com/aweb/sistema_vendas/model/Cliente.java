package br.com.aweb.sistema_vendas.model;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @NotBlank(message = "Nome é obrigatório.")
    @Column(nullable = false)
    private String fullName;

    @NotBlank(message = "Email é obrigatório.")
    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Id
    @CPF(message = "CPF inválido")
    @NotBlank(message = "CPF é obrigatório.")
    private String cpf;

    @NotBlank(message = "Telefone é obrigatório.")
    @Column(nullable = false)
    private String telephone;

    @NotBlank(message = "Logradouro é obrigatório.")
    @Column(nullable = false)
    private String publicPlace;

    @Column(nullable = true)
    private Integer number;

    @Column(nullable = true)
    private String complement;

    @NotBlank(message = "Bairro é obrigatório.")
    @Column(nullable = false)
    private String neighborhood;

    @NotBlank(message = "Cidade é obrigatório.")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "Distrito é obrigatório.")
    @Column(nullable = false)
    private String uf;

    @NotBlank(message = "CEP é obrigatório.")
    @Column(nullable = false)
    private String cep;
}
