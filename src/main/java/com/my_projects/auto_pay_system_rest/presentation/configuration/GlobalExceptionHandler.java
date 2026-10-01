package com.my_projects.auto_pay_system_rest.presentation.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((erro) -> {
            String fieldName = ((FieldError) erro).getField();
            String mensagemErro = erro.getDefaultMessage();
            erros.put(fieldName, mensagemErro);
        });
        return erros;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("erro_negocio", ex.getMessage());
        return erro;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public Map<String,String> handleIllegalStateException(IllegalStateException ex){
        Map<String,String> erro = new HashMap<>();
        erro.put("erro_estado", ex.getMessage());
        return erro;
    }

}
