package com.exemplo.pessoas.mapper;

import com.exemplo.pessoas.dto.PessoaRequestDTO;
import com.exemplo.pessoas.dto.PessoaResponseDTO;
import com.exemplo.pessoas.entity.Pessoa;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    Pessoa toEntity(PessoaRequestDTO dto);

    PessoaResponseDTO toDTO(Pessoa entity);

    List<PessoaResponseDTO> toDTOList(List<Pessoa> pessoas);
}
