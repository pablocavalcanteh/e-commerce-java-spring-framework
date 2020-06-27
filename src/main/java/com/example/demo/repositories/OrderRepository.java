package com.example.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Client;
import com.example.demo.domain.Pedido;

@Repository
public interface OrderRepository extends JpaRepository<Pedido, Integer> {
	
	@Transactional(readOnly=true)
	Page<Pedido> findByClient(Client cli, Pageable pageRequest);
}
