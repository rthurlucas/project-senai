package br.com.cantina.senai.model.quantidadeProduto;

import br.com.cantina.senai.model.pedido.Pedido;
import br.com.cantina.senai.model.produto.Produto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedido_produto")
@Data
@NoArgsConstructor
public class QuantidadeProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idQuantidadeProduto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto")
    private Produto produto;

    private Integer quantidade;
}
