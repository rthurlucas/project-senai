package br.com.cantina.senai.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DTOPedido(
        @NotNull
        @Min(1)
        Integer quantidadePedido
) {
}
