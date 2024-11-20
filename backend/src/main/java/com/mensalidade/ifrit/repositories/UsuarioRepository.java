package com.mensalidade.ifrit.repositories;

import com.mensalidade.ifrit.models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>, JpaSpecificationExecutor<Usuario> {

    UserDetails findByLogin(String login);

    @Query("SELECT u FROM usuarios u WHERE u.ativo = true")
    Page<Usuario> findAllAtivos(Pageable pageable);

}

