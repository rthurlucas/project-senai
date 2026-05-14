package br.com.cantina.senai.model.produto;

import br.com.cantina.senai.dto.DTOCadastroProduto;
import br.com.cantina.senai.model.quantidadeProduto.QuantidadeProduto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduto;
    @Column(name = "nome_produto")
    private String nomeProduto;
    @Column(name = "descricao_produto")
    private String descricaoProduto;
    @OneToMany(mappedBy = "produto")
    private List<QuantidadeProduto> pedidos = new ArrayList<>();

    private boolean produtoAtivo = true;

    public Produto(DTOCadastroProduto produto) {
        this.nomeProduto = produto.nomeProduto();
        this.descricaoProduto = produto.descricaoProduto();
    }
}
