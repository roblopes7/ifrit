package com.mensalidade.ifrit.repositories.specifications;

import com.mensalidade.ifrit.models.Usuario;
import org.springframework.data.jpa.domain.Specification;

public class UsuarioSpecification {

    public static Specification<Usuario> isIdEqualsTo(String id) {
        if (id == null|| id.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("id")), "%" + id.toUpperCase() + "%");
    }

    public static Specification<Usuario> isNomeEqualsTo(String nome) {
        if (nome == null|| nome.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }

    public static Specification<Usuario> isLoginEqualsTo(String login) {
        if (login == null|| login.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("login")), "%" + login.toUpperCase() + "%");
    }

    public static Specification<Usuario> isEmailEqualsTo(String email) {
        if (email == null || email.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("email")), "%" + email.toUpperCase() + "%");

    }

    public static Specification<Usuario> isCelularEqualsTo(String celular) {
        if (celular == null || celular.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("celular")), "%" + celular.toUpperCase() + "%");

    }

    public static Specification<Usuario> isTelefoneEqualsTo(String telefone) {
        if (telefone == null || telefone.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("pais")), "%" + telefone.toUpperCase() + "%");

    }

    public static Specification<Usuario> isAtivoEqualsTo(Boolean inativo) {
        if (inativo == null || !inativo) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), true);

    }
}
