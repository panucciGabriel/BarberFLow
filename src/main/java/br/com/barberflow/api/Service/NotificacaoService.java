package br.com.barberflow.api.Service;

import br.com.barberflow.api.model.Agendamento;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class NotificacaoService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.whatsapp.number.from}")
    private String fromNumber;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid , authToken);
    }

    public void enviarConfirmacaoAgendamento(Agendamento agendamento) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
        String dataHoraFormatada = agendamento.getDataHora().format(formatter);

        String mensagem = String.format(
                "Olá %s! Seu agendamento na BarberFlow foi confirmado! ✅\n\n" +
                        "Servico: *%s*\n" +
                        "Barbeiro: *%s*\n" +
                        "Data: *%s*\n\n" +
                        "Até breve!",

                agendamento.getCliente().getNome(),
                agendamento.getServico().getNome(),
                agendamento.getBarbeiro().getNome(),
                dataHoraFormatada
        );

        String toNumber = "whatsapp:" + agendamento.getCliente().getTelefone().trim();

        try {
            Message.creator(
                    new PhoneNumber(toNumber),
                    new PhoneNumber(fromNumber),
                    mensagem
            ).create();

            System.out.println("Mensagem de confirmação enviada para: " + toNumber);

        } catch (Exception e) {
            System.err.println("Erro ao enviar a  mensagem via Twilio: " + e.getMessage());
        }
    }
}
