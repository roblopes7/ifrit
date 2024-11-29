package com.mensalidade.ifrit.repositories.specifications;

import com.mensalidade.ifrit.models.Empresa;
import com.mensalidade.ifrit.models.Usuario;
import org.springframework.data.jpa.domain.Specification;

public class EmpresaSpecification {

    public static Specification<Empresa> isIdEqualsTo(String id) {
        if (id == null|| id.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("id")), "%" + id.toUpperCase() + "%");
    }

    public static Specification<Empresa> isRazaoSocialEqualsTo(String razaoSocial) {
        if (razaoSocial == null|| razaoSocial.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("razaoSocial")), "%" + razaoSocial.toUpperCase() + "%");
    }

    public static Specification<Empresa> isNomeFantasiaEqualsTo(String nomeFantasia) {
        if (nomeFantasia == null|| nomeFantasia.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("nomeFantasia")), "%" + nomeFantasia.toUpperCase() + "%");
    }

    public static Specification<Empresa> isCnpjCpfEqualsTo(String cnpjCpf) {
        if (cnpjCpf == null|| cnpjCpf.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("cnpjCpf")), "%" + cnpjCpf.toUpperCase() + "%");
    }

    public static Specification<Empresa> isResponsavelEqualsTo(String responsavel) {
        if (responsavel == null|| responsavel.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("responsavel")), "%" + responsavel.toUpperCase() + "%");
    }


}
