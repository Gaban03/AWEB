package br.com.aweb.sistema_vendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.aweb.sistema_vendas.model.Cliente;
import br.com.aweb.sistema_vendas.repository.ClienteRepository;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Transactional
    public Cliente add(Cliente customer) {
        return clienteRepository.save(customer);
    }

    public List<Cliente> getAllCustomer(){
        return clienteRepository.findAll();
    }

    public Optional<Cliente> getById(String id){
        return clienteRepository.findById(id);
    }

    public Cliente update(String id, Cliente updatedCustomer){
        var optionalCustomer = getById(id);
        if(!optionalCustomer.isPresent())
            throw new IllegalArgumentException("Cliente não encontrado.");

        var existsCustomer = optionalCustomer.get();

        existsCustomer.setFullName(updatedCustomer.getFullName());
        existsCustomer.setEmail(updatedCustomer.getEmail());
        existsCustomer.setCpf(updatedCustomer.getCpf());
        existsCustomer.setTelephone(updatedCustomer.getTelephone());
        existsCustomer.setPublicPlace(updatedCustomer.getPublicPlace());
        existsCustomer.setNumber(updatedCustomer.getNumber());
        existsCustomer.setComplement(updatedCustomer.getComplement());
        existsCustomer.setNeighborhood(updatedCustomer.getNeighborhood());
        existsCustomer.setCity(updatedCustomer.getCity());
        existsCustomer.setUf(updatedCustomer.getUf());
        existsCustomer.setCep(updatedCustomer.getCep());

        var saveCustomer = clienteRepository.save(existsCustomer);

        return saveCustomer;
    }

    @Transactional
    public void delete(String id){
        var optionalCustomer = getById(id);
        if (!optionalCustomer.isPresent())
            throw new IllegalArgumentException("Cliente não encontrado.");

        clienteRepository.deleteById(id);
    }

    
}
