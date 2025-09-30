package br.com.barberflow.api.repository;

import br.com.barberflow.api.model.Agendamento;
import br.com.barberflow.api.model.Barbeiro;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // ===== MÉTODO CORRIGIDO AQUI =====
    // Trocamos "Barber" por "Barbeiro" e "DatHora" por "DataHora"
    List<Agendamento> findByBarbeiroAndDataHoraBetween(Barbeiro barbeiro, LocalDateTime inicio, LocalDateTime fim);

    List<Agendamento> findAllByDataHoraBetween(LocalDateTime inicioDoDia, LocalDateTime fimDoDia);

}