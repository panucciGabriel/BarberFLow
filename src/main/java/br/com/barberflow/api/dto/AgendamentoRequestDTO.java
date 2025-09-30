package br.com.barberflow.api.dto;

import java.time.LocalDateTime;

public record AgendamentoRequestDTO(
        Long clienteId,
        Long barbeiroId,
        Long servicoId,
        LocalDateTime dataHora
) {
}
