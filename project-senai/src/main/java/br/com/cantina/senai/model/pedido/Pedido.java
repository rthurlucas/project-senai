package br.com.cantina.senai.model.pedido;

import br.com.cantina.senai.dto.DTOCadastroPedido;
import br.com.cantina.senai.model.produto.Produto;
import br.com.cantina.senai.model.quantidadeProduto.QuantidadeProduto;
import br.com.cantina.senai.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<QuantidadeProduto> itens = new ArrayList<>();

    private LocalDateTime dataPedido;

    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    public Pedido(DTOCadastroPedido dados) {
        this.dataPedido = LocalDateTime.now();
    }
}
