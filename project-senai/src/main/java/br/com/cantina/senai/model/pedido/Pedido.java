package br.com.cantina.senai.model.pedido;

import br.com.cantina.senai.model.produto.Produto;
import br.com.cantina.senai.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    public Pedido(Pedido pedido){

    }

    public void atualizarInformacoesPedido(Pedido pedido){

    }
}
