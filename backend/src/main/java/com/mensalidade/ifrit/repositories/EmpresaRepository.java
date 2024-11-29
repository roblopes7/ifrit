package com.mensalidade.ifrit.repositories;

import com.mensalidade.ifrit.models.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, String>, JpaSpecificationExecutor<Empresa> {
}
