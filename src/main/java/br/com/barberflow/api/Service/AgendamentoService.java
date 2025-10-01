package br.com.barberflow.api.Service; // Pacote renomeado para 'service' (minúsculo)

import br.com.barberflow.api.Service.exceptions.HorarioConflitanteException;
import br.com.barberflow.api.model.Agendamento;
import br.com.barberflow.api.model.Barbeiro; // Padronizado
import br.com.barberflow.api.model.Cliente;  // Padronizado
import br.com.barberflow.api.model.Servico;
import br.com.barberflow.api.repository.AgendamentoRepository;
import br.com.barberflow.api.repository.BarbeiroRepository; // Padronizado
import br.com.barberflow.api.repository.ClienteRepository;  // Padronizado
import br.com.barberflow.api.repository.ServicoRepository;  // Padronizado
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final BarbeiroRepository barbeiroRepository;
    private final ServicoRepository servicoRepository;
    private final NotificacaoService notificacaoService;

    // Construtor corrigido para usar os nomes padronizados
    public AgendamentoService (AgendamentoRepository agendamentoRepository,
                               ClienteRepository clienteRepository,
                               BarbeiroRepository barbeiroRepository,
                               ServicoRepository servicoRepository,
                               NotificacaoService notificacaoService) {
        this.agendamentoRepository = agendamentoRepository;
        this.clienteRepository = clienteRepository;
        this.barbeiroRepository = barbeiroRepository;
        this.servicoRepository = servicoRepository;
        this.notificacaoService = notificacaoService;
    }


    public Agendamento criarAgendamento (Long clienteId, Long barbeiroId, Long servicoId, LocalDateTime dataHora) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
        Barbeiro barbeiro = barbeiroRepository.findById(barbeiroId)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado!"));
        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado!"));

        LocalDateTime dataHoraFim = dataHora.plusMinutes(servico.getDuracaoEmMinutos());

        List<Agendamento> conflitos = agendamentoRepository
                .findByBarbeiroAndDataHoraBetween(barbeiro, dataHora, dataHoraFim);

        if (!conflitos.isEmpty()) {
            throw  new HorarioConflitanteException("Horário indisponível. Já existe um agendamento para este barbeiro nesse horário.");
        }

        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setCliente(cliente);
        novoAgendamento.setBarbeiro(barbeiro);
        novoAgendamento.setServico(servico);
        novoAgendamento.setDataHora(dataHora);
        novoAgendamento.setStatus("MARCADO");

        Agendamento agendamentoSalvo = agendamentoRepository.save(novoAgendamento);

        try {
            notificacaoService.enviarConfirmacaoAgendamento(agendamentoSalvo);
        } catch (Exception e) {
            System.err.println("Agendamento criado, mas falha ao enviar notificação" + e.getMessage());
        }
        return agendamentoSalvo;
    }

    public List<Agendamento> buscarAgendaDoDia(LocalDate data) {
        LocalDateTime inicioDoDia = data.atStartOfDay();

        LocalDateTime fimDoDIa = data.atTime(23, 59, 59);

        return agendamentoRepository.findAllByDataHoraBetween(inicioDoDia, fimDoDIa);
    }

}