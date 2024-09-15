package com.mesafacil.infra.exception;

import com.mesafacil.infra.util.MessageService;
import jakarta.el.MethodNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.LazyInitializationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.NoSuchElementException;

@AllArgsConstructor
@RestControllerAdvice
public class MesaFacilExceptionHandler {

    private final MessageService messageService;

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity tratarErro404(EntityNotFoundException ex) {
        return ResponseEntity.badRequest().body((new DadosErro(
                ex, messageService.getMessage("recurso.nao-encontrado"))));
    }

    @ExceptionHandler({NoResourceFoundException.class})
    public ResponseEntity tratarErro404(NoResourceFoundException ex) {
        return ResponseEntity.badRequest().body((new DadosErro(
                ex, messageService.getMessage("recurso.nao-encontrado"))));
    }

    @ExceptionHandler({MethodNotFoundException.class,})
    public ResponseEntity tratarErro404(MethodNotFoundException ex) {
        return ResponseEntity.badRequest().body((new DadosErro(
                ex, messageService.getMessage("recurso.nao-encontrado"))));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(erro ->
                new DadosErroValidacao(erro, messageService.getMessage("methodArgumentNotValidMessage"))).toList());
    }

    @ExceptionHandler({UnexpectedTypeException.class})
    public ResponseEntity<Object> handleUnexpectedTypeExceptionException(UnexpectedTypeException ex) {
        return ResponseEntity.badRequest().body((new DadosErro(
                ex, messageService.getMessage("unexpectedTypeMessageMessage"))));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ResponseEntity.badRequest().body((new DadosErro(
                ex, messageService.getMessage("dataIntegrityViolationMessage"))));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleHttpRequestMethodNotSupportedExceptionException(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.badRequest().body((new DadosErro(
                ex, messageService.getMessage("httpRequestMethodNotSupportedExceptionMessage"))));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body((new DadosErro(
                ex, messageService.getMessage("constraintViolationMessage"))));
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        return ResponseEntity.badRequest().body((new DadosErro(
                ex, messageService.getMessage("constraintViolationMessage"))));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
        return ResponseEntity.badRequest().body((new DadosErro(
                ex, messageService.getMessage("nullPointerExceptionMessage"))));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body((new DadosErro(
                ex, messageService.getMessage("httpMessageNotReadableExceptionMessage"))));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(NoSuchElementException ex) {
        return ResponseEntity.badRequest().body((new DadosErro(
                ex, messageService.getMessage("noSuchElementExceptionMessage"))));
    }
    @ExceptionHandler(LazyInitializationException.class)
    public ResponseEntity<Object> handleLazyInitializationExceptionException(LazyInitializationException ex) {
        return ResponseEntity.badRequest().body((new DadosErro(
                ex, messageService.getMessage("lazyInitializationExceptionMessage"))));
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<Object> handleRegraDeNegocioException(RegraDeNegocioException ex) {
        return ResponseEntity.badRequest().body((new DadosErro(
                ex, ex.getMessage())));
    }

    private record DadosErroValidacao(String campo, String mensagemUsuario, String mensagemDesenvolvedor) {
        public DadosErroValidacao(FieldError erro, String mensagemUsuario) {
            this(erro.getField(), mensagemUsuario, erro.toString());
        }
    }

    private record DadosErro(String mensagemUsuario, String mensagemDesenvolvedor) {
        public DadosErro(Exception erro, String mensagemUsuario) {
            //ExceptionUtils - pega a exception para exibir mensagem
            this(mensagemUsuario, ExceptionUtils.getRootCauseMessage(erro));
        }

    }

}
