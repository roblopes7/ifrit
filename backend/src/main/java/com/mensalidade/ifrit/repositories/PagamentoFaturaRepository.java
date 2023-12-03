package com.mensalidade.ifrit.repositories;

import com.mensalidade.ifrit.models.PagamentoFatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoFaturaRepository extends JpaRepository<PagamentoFatura, String> {
}
