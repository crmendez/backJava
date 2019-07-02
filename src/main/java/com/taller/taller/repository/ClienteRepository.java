package com.taller.taller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taller.taller.Model.Cliente;



@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
