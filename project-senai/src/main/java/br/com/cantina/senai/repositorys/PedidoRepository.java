package br.com.cantina.senai.repositorys;

import br.com.cantina.senai.model.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
