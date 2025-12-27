package br.com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivroDto(
        String title,
        List<String> languages,
        List<DadosAutorDto> authors,
        @JsonProperty("download_count") Integer downloadCount
) {
}