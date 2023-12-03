package com.mensalidade.ifrit.repositories;

import com.mensalidade.ifrit.models.Mensalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensalidadeRepository  extends JpaRepository<Mensalidade, String> {
}
