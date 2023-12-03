package com.mensalidade.ifrit.repositories;

import com.mensalidade.ifrit.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {


}
