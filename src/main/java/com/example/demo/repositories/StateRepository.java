package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

	

}
