package br.com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAutorDto(
        String name,
        @JsonProperty("birth_year") Integer anoNascimento,
        @JsonProperty("death_year") Integer anoFalecimento
) {
}