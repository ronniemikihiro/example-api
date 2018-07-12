package com.example.api.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.api.exception.NegocioException;

/**
 * Intercepta os exceptions e lançá-los para o cliente.
 */
@ControllerAdvice
public class ExampleExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	/**
	 * Trata as exceptions lançadas.
	 *
	 * @param ex
	 * @return ResponseEntity<Object>
	 */
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleNegocioException(final Exception ex) {
		final boolean isNegocioException = ex instanceof NegocioException;
		final String msg = ex instanceof NegocioException ? messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()) : ex.getMessage();
		final Erro erro = new Erro(ex.getMessage(), StringUtils.isEmpty(msg) ? ex.toString() : msg);
		return ResponseEntity.status(isNegocioException ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
	}
	
}
