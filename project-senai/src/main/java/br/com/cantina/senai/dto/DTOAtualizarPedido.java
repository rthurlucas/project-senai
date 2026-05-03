package br.com.cantina.senai.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTOAtualizarPedido(
        @NotNull
        Long produto,
        @NotNull
        @Min(0)
        Integer quantidade
) {
}
