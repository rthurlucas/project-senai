package br.com.cantina.senai.model.produto;

import br.com.cantina.senai.dto.DTOCadastroProduto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Produto(DTOCadastroProduto produto) {

    }
}
