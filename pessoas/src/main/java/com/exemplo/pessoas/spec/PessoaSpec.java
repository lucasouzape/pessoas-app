package com.exemplo.pessoas.spec;

import com.exemplo.pessoas.entity.Pessoa;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class PessoaSpec {

    public static Specification<Pessoa> nomeLike(String nome) {
        return (root, query, cb) -> StringUtils.hasText(nome)
                ? cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%")
                : null;
    }

    public static Specification<Pessoa> cpfEquals(String cpf) {
        return (root, query, cb) -> StringUtils.hasText(cpf)
                ? cb.equal(root.get("cpf"), cpf)
                : null;
    }

    public static Specification<Pessoa> emailLike(String email) {
        return (root, query, cb) -> StringUtils.hasText(email)
                ? cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%")
                : null;
    }

    public static Specification<Pessoa> dataNascimentoBetween(LocalDate dataInicio, LocalDate dataFim) {
        return (root, query, cb) -> {
            if (dataInicio != null && dataFim != null) {
                return cb.between(root.get("dataNascimento"), dataInicio, dataFim);
            } else if (dataInicio != null) {
                return cb.greaterThanOrEqualTo(root.get("dataNascimento"), dataInicio);
            } else if (dataFim != null) {
                return cb.lessThanOrEqualTo(root.get("dataNascimento"), dataFim);
            }
            return null;
        };
    }
}
