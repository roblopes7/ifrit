package com.mensalidade.ifrit.repositories;

import com.mensalidade.ifrit.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Usuario findByLogin(String login);
}
