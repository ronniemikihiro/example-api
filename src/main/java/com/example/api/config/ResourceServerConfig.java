package com.example.api.config;

import com.example.api.model.Usuario;
import com.example.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Realiza a configuração do oauth.
 */
@Profile("oauth-security")
@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Seta os detalhes do usuário e senha encodada.
	 *
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
	}

	/**
	 * Configura as permissões http.
	 *
	 * @param http
	 * @throws Exception
	 */
	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/categorias*").permitAll()
				.anyRequest().authenticated()
				.and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
					.csrf().disable();
	}

	/**
	 * Seta sessão como stateless.
	 *
	 * @param resources
	 * @throws Exception
	 */
	@Override
	public void configure(final ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(true);
	}

	/**
	 * Retorna o password encodado.
	 *
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new MyPasswordEncoder();
	}

	/**
	 * Cria a expressão handler.
	 *
	 * @return MethodSecurityExpressionHandler
	 */
	@Bean
	public MethodSecurityExpressionHandler createExpressionHandler() {
		return new OAuth2MethodSecurityExpressionHandler();
	}

	private class MyPasswordEncoder implements PasswordEncoder {

		@Autowired
		private UsuarioRepository usuarioRepository;

		@Override
		public String encode(CharSequence charSequence) {
			return null;
		}

		@Override
		public boolean matches(CharSequence senhaUsuario, String senhaBanco) {
			Optional<Usuario> usuario = usuarioRepository.findByEmail("admin@example.com");
			return false;
		}
	}

}
