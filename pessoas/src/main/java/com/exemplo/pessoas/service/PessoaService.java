package com.exemplo.pessoas.service;

import com.exemplo.pessoas.dto.PessoaRequestDTO;
import com.exemplo.pessoas.dto.PessoaResponseDTO;
import com.exemplo.pessoas.entity.Pessoa;
import com.exemplo.pessoas.exception.BusinessException;
import com.exemplo.pessoas.exception.NotFoundException;
import com.exemplo.pessoas.mapper.PessoaMapper;
import com.exemplo.pessoas.repository.PessoaRepository;
import com.exemplo.pessoas.spec.PessoaSpec;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository repository;
    private final PessoaMapper mapper;

    public PessoaResponseDTO criar(PessoaRequestDTO dto) {
        if (repository.existsByCpf(dto.getCpf())) {
            throw new BusinessException("CPF já cadastrado.");
        }
        Pessoa pessoa = mapper.toEntity(dto);
        Pessoa saved = repository.save(pessoa);
        return mapper.toDTO(saved);
    }

    public Page<PessoaResponseDTO> listar(String nome, String cpf, LocalDate dataInicio, LocalDate dataFim, String email, Pageable pageable) {
        Specification<Pessoa> spec = Specification.where(PessoaSpec.nomeLike(nome))
                .and(PessoaSpec.cpfEquals(cpf))
                .and(PessoaSpec.dataNascimentoBetween(dataInicio, dataFim))
                .and(PessoaSpec.emailLike(email));

        return repository.findAll(spec, pageable).map(mapper::toDTO);
    }

    public PessoaResponseDTO atualizar(Long id, PessoaRequestDTO dto) {
        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));

        pessoa.setNome(dto.getNome());
        pessoa.setCpf(dto.getCpf());
        pessoa.setDataNascimento(dto.getDataNascimento());
        pessoa.setEmail(dto.getEmail());
        pessoa.setTelefone(dto.getTelefone());

        return mapper.toDTO(repository.save(pessoa));
    }

    public void excluir(Long id) {
        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
        repository.delete(pessoa);
    }

    public PessoaResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
    }
}
