package com.mensalidade.ifrit.repositories;

import com.mensalidade.ifrit.models.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, String> {
}
