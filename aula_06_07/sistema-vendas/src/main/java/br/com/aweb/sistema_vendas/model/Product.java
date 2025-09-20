package br.com.aweb.sistema_vendas.model;

import java.math.BigDecimal;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Descrição é obrigatório")
    @Column(nullable = false, length = 100)
    private String description;

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull(message = "Quantidade é obrigatória")
    @PositiveOrZero(message = "O valor deve ser maior ou igual a zero")
    @Column(nullable = false)
    private Integer quantityStock;
}
