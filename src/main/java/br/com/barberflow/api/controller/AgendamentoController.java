package br.com.barberflow.api.controller;

import br.com.barberflow.api.Service.AgendamentoService;
import br.com.barberflow.api.dto.AgendamentoRequestDTO;
import br.com.barberflow.api.model.Agendamento;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;


    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @PostMapping
    public ResponseEntity<Agendamento> criar(@RequestBody AgendamentoRequestDTO dto) {

        Agendamento agendamentoCriado = agendamentoService.criarAgendamento(
                dto.clienteId(),
                dto.barbeiroId(),
                dto.servicoId(),
                dto.dataHora()
        );

        return ResponseEntity.status(201).body(agendamentoCriado);

    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> buscarPorData(@RequestParam("data")LocalDate data) {

        List<Agendamento> agendaDoDia = agendamentoService.buscarAgendaDoDia(data);

        return ResponseEntity.ok(agendaDoDia);
    }
}
