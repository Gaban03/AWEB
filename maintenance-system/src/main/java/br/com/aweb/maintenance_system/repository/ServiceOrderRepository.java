package br.com.aweb.maintenance_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.aweb.maintenance_system.model.ServiceOrder;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long>{
    
}
