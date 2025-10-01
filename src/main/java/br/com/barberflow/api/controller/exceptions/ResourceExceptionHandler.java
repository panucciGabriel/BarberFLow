package br.com.barberflow.api.controller.exceptions;

import br.com.barberflow.api.Service.exceptions.HorarioConflitanteException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(HorarioConflitanteException.class)
    public ResponseEntity<StandardError> handlerHorarioConflitante(HorarioConflitanteException e, HttpServletRequest request) {

        String error = "Conflito de agendamento";
        HttpStatus status = HttpStatus.CONFLICT;

        StandardError err = new StandardError(
                Instant.now(),
                status.value(),
                error,
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(err);
    }

}
