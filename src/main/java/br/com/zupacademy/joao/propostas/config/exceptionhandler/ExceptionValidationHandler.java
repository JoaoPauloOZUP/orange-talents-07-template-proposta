package br.com.zupacademy.joao.propostas.config.exceptionhandler;

import br.com.zupacademy.joao.propostas.config.exception.ApiErroException;
import br.com.zupacademy.joao.propostas.config.exceptionhandler.dto.ErroPadronizado;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestControllerAdvice
public class ExceptionValidationHandler {

    @Autowired
    private MessageSource message;

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Collection<String> mensagens = new ArrayList<>();
        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(erro -> {
            String msg = String.format("Campo %s %s", erro.getField(), message.getMessage(erro, LocaleContextHolder.getLocale()));
            mensagens.add(msg);
        });

        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadronizado);
    }

    @ExceptionHandler(ApiErroException.class)
    public ResponseEntity<?> handleApiErroException(ApiErroException apiErroException) {
        Collection<String> mensagens = new ArrayList<>();
        mensagens.add(apiErroException.getReason());
        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);

        return ResponseEntity.status(apiErroException.getHttpStatus()).body(erroPadronizado);
    }
}
