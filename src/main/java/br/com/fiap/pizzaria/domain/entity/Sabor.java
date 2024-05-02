package br.com.fiap.pizzaria.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(
        name = "TB_SABOR",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_SABOR_NOME", columnNames = "NM_SABOR")
        }
)

public class Sabor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_SABOR")
    @SequenceGenerator(name = "SQ_SABOR", sequenceName = "SQ_SABOR", allocationSize = 1)
    @Column(name = "ID_SABOR")
    private Long id;

    @Column(name = "NM_SABOR", nullable = false, length = 100)
    private String nome;

    @Column(name = "DS_SABOR", length = 255)
    private String descricao;
}