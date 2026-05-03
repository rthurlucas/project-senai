package br.com.cantina.senai.model.pedido;

import br.com.cantina.senai.dto.DTOCadastroPedido;
import br.com.cantina.senai.model.produto.Produto;
import br.com.cantina.senai.model.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto")
    private Produto produto;
    @Column(name = "quantidade")
    private Integer quantidade;
    @Column(name = "data_pedido")
    private LocalDateTime dataPedido;

    public Pedido(DTOCadastroPedido dados) {
        this.quantidade = quantidade;
        this.dataPedido = dataPedido;
    }
}
