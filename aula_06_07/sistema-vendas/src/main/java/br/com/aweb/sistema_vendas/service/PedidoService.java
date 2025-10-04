package br.com.aweb.sistema_vendas.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.aweb.sistema_vendas.enums.StatusPedido;
import br.com.aweb.sistema_vendas.model.Cliente;
import br.com.aweb.sistema_vendas.model.ItemPedido;
import br.com.aweb.sistema_vendas.model.Pedido;
import br.com.aweb.sistema_vendas.model.Produto;
import br.com.aweb.sistema_vendas.repository.PedidoRepository;
import br.com.aweb.sistema_vendas.repository.ProdutoRepository;
import jakarta.transaction.Transactional;

@Service
public class PedidoService {
    
    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Transactional
    public Pedido criarPedido(Cliente cliente){
        return pedidoRepository.save(new Pedido(cliente));
    }

    public void adicionarItem(Long pedidoId, Long produtoId, Integer quantidade){

        Optional<Pedido> pedido = pedidoRepository.findById(pedidoId);
        Optional<Produto> produto = produtoRepository.findById(produtoId);

        if(!pedido.isPresent())
            throw new IllegalArgumentException("Pedido não existe no sistema.");

        if(!produto.isPresent())
            throw new IllegalArgumentException("Produto não existe no sistema.");

        if(pedido.get().getStatus() == StatusPedido.CANCELADO)
            throw new IllegalArgumentException("Não é possível alterar pedido cancelado.");

        if(produto.get().getQuantidadeEmEstoque() < quantidade)
            throw new IllegalArgumentException("Estoque insuficiente para o produto.");

        ItemPedido item = new ItemPedido(pedido.get(), produto.get(), quantidade);

        produto.get().setQuantidadeEmEstoque(produto.get().getQuantidadeEmEstoque() - quantidade);

        pedido.get().getItens().add(item);
        produto.get().getItens().add(item);

        calcularValorTotal(pedido.get());

        produtoRepository.save(produto.get());
        pedidoRepository.save(pedido.get());
    }

    private void calcularValorTotal(Pedido pedido){
        BigDecimal total = BigDecimal.ZERO;

        for(ItemPedido item : pedido.getItens()){
            BigDecimal valorItem = item.getPrecoUnitario()
                .multiply(BigDecimal.valueOf(item.getQuantidade()));
            total = total.add(valorItem);
        }

        pedido.setValorTotal(total);
    }

    public void removerItem(Long pedidoId, Long itemId){

        Optional<Pedido> pedido = pedidoRepository.findById(pedidoId);

        if(!pedido.isPresent())
            throw new IllegalArgumentException("Pedido não existe no sistema.");

        if(pedido.get().getStatus() == StatusPedido.CANCELADO)
            throw new IllegalArgumentException("Não é possível alterar pedido cancelado.");

        for (ItemPedido item : pedido.get().getItens()) {
            
            if(item.getId() != itemId)
                continue;

            pedido.get().getItens().remove(item);
            
            Optional<Produto> produto = produtoRepository.findById(item.getProduto().getId());

            produto.get().setQuantidadeEmEstoque(produto.get().getQuantidadeEmEstoque() + item.getQuantidade());

            calcularValorTotal(pedido.get());

            produtoRepository.save(produto.get());
        }

        pedidoRepository.save(pedido.get());
    }

}
