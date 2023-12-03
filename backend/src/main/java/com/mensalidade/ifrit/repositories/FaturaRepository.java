package com.mensalidade.ifrit.repositories;

import com.mensalidade.ifrit.models.Fatura;
import com.mensalidade.ifrit.models.enums.StatusFatura;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, String> {

    @Transactional
    @Modifying(clearAutomatically=true)
    @Query(value = "UPDATE fatura f SET f.status = :statusFatura WHERE f.id = :id", nativeQuery = false)
    void updateStatusFaturaById(StatusFatura statusFatura, String id);

    @Override
    @Transactional
    @Modifying(clearAutomatically=true)
    <S extends Fatura> S save(S entity);
}
