package com.mensalidade.ifrit.repositories.specifications;

import com.mensalidade.ifrit.models.Cidade;
import org.springframework.data.jpa.domain.Specification;

public class CidadeSpecification {

    public static Specification<Cidade> isIdEqualsTo(String id) {
        if (id == null|| id.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("id")), "%" + id.toUpperCase() + "%");
    }

    public static Specification<Cidade> isNomeEqualsTo(String nome) {
        if (nome == null|| nome.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }

    public static Specification<Cidade> isUFEqualsTo(String uf) {
        if (uf == null|| uf.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("uf")), "%" + uf.toUpperCase() + "%");
    }

    public static Specification<Cidade> isPaisEqualsTo(String pais) {
        if (pais == null|| pais.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("pais")), "%" + pais.toUpperCase() + "%");

    }
}
