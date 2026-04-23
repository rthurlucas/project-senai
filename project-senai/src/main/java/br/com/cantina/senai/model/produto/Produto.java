package br.com.cantina.senai.model.produto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "produto")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduto;
    private String descricaoProduto;

}
