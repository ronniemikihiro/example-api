package com.example.api.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import com.example.api.event.RecursoCriadoEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Cria o recurso de resposta.
 */
@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

	/**
	 * Recupera o response e o código do Recurso Criado para adicionar um Location no Header de resposta.
	 *
	 * @param recursoCriadoEvent
	 */
	@Override
	public void onApplicationEvent(final RecursoCriadoEvent recursoCriadoEvent) {
		final HttpServletResponse response = recursoCriadoEvent.getResponse();
		final Long codigo = recursoCriadoEvent.getCodigo();
		adicionarHeaderLocation(response, codigo);
	}

	/**
	 * Adiciona um Location no Header de resposta.
	 *
	 * @param response
	 * @param codigo
	 */
	private void adicionarHeaderLocation(final HttpServletResponse response, final Long codigo) {
		final URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(codigo).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}

}
