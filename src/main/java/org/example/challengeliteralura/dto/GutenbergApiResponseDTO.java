package org.example.challengeliteralura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutenbergApiResponseDTO(Integer count,
                                      String next,
                                      String previous,
                                      List<LivroDTO> results) {
}
