package br.com.cantina.senai.model.estoque;

import br.com.cantina.senai.model.produto.Produto;
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

    @JoinColumn(name = "id_produto")
    @ManyToOne
    private Produto idProduto;

    private Integer quantidade;

    public Estoque(Estoque estoque){

    }
}
