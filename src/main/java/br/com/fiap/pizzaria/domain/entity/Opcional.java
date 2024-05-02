package br.com.fiap.pizzaria.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name = "TB_OPCIONAL",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_SABOR_OPCIONAL",
                        columnNames = {"NM_OPCIONAL", "SABOR"}
                )
        }
)
public class Opcional {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_OPCIONAL")
    @SequenceGenerator(name = "SQ_OPCIONAL", sequenceName = "SQ_OPCIONAL", allocationSize = 1)
    @Column(name = "ID_OPCIONAL")
    private Long id;


    @Column(name = "NM_OPCIONAL", nullable = false, length = 100)
    private String nome;


    private BigDecimal preco;


    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "SABOR",
            referencedColumnName = "ID_SABOR",
            foreignKey = @ForeignKey(name = "FK_OPCIONAL_SABOR")
    )
    private Sabor sabor;


}
