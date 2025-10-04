package br.com.aweb.sistema_vendas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.aweb.sistema_vendas.enums.StatusPedido;
import br.com.aweb.sistema_vendas.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByStatus(StatusPedido status);

}
