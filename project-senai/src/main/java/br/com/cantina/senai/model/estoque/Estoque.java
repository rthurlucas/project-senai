package br.com.cantina.senai.model.estoque;

import br.com.cantina.senai.model.produto.Produto;
import jakarta.persistence.*;

@Entity
@Table(name = "estoque")
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstoque;

    @JoinColumn(name = "id_produto")
    @ManyToOne
    private Produto idProduto;

    private Integer quantidade;

    public Estoque(){

    }

    public Estoque(Long idEstoque, Produto idProduto, Integer quantidade) {
        this.idEstoque = idEstoque;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }


    public Estoque(Estoque estoque) {
    }
}
