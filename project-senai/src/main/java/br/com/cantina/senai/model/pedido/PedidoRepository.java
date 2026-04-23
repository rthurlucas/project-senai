package br.com.cantina.senai.model.pedido;

import br.com.cantina.senai.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Usuario findPedidoByIdPedido(Long idPedido);
}
