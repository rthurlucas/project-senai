package br.com.cantina.senai.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estoque")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstoque;

    private Long idProduto;

    private Integer quantidade;
}
