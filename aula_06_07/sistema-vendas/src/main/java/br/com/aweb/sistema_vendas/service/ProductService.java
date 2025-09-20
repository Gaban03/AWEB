package br.com.aweb.sistema_vendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.aweb.sistema_vendas.model.Product;
import br.com.aweb.sistema_vendas.repository.ProductRepository;
import jakarta.transaction.Transactional;

@Service
public class ProductService {
    
    @Autowired
    ProductRepository productRepository;

    @Transactional
    public Product save(Product product){
       return productRepository.save(product);
    }

    public List<Product> list(){
        return productRepository.findAll();
    }

    public void delete(Long id){
        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()){
            productRepository.delete(product.get());
            return;
        }

        throw new RuntimeException("Erro ao deletar produto: Produto não encontrado na base de dados.");
    }

    public Optional<Product> productById(Long id){
        return productRepository.findById(id);
    }

}
