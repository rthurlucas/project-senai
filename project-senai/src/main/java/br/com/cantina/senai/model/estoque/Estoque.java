package br.com.cantina.senai.model.estoque;

import br.com.cantina.senai.dto.DTOCadastroEstoque;
import br.com.cantina.senai.model.produto.Produto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estoque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstoque;

    @JoinColumn(name = "id_produto")
    @ManyToOne
    private Produto idProduto;

    private Integer quantidade;

    public Estoque(DTOCadastroEstoque dados) {
        this.idEstoque =  idEstoque;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }
}
