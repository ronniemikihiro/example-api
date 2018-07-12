package com.example.api.config;

import com.example.api.config.token.CustomTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

/**
 * Configuração das autorizações dos clientes.
 */
@Profile("oauth-security")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * Configura os clientes de acesso.
	 *
	 * @param clients
	 * @throws Exception
	 */
	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("angular")
				.secret("angular")
				.scopes("read", "write")
				.authorizedGrantTypes("password", "refresh_token")
				.accessTokenValiditySeconds(1800)
				.refreshTokenValiditySeconds(3600 * 24)
			.and()
				.withClient("mobile")
				.secret("mobile")
				.scopes("read")
				.authorizedGrantTypes("password", "refresh_token")
				.accessTokenValiditySeconds(1800)
				.refreshTokenValiditySeconds(3600 * 24);
	}

	/**
	 * Configura o token.
	 *
	 * @param endpoints
	 * @throws Exception
	 */
	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(getTokenEnhancer(), getAccessTokenConverter()));

        endpoints
			.tokenStore(getTokenStore())
			.tokenEnhancer(tokenEnhancerChain)
			.reuseRefreshTokens(false)
			.authenticationManager(authenticationManager);
	}

	/**
	 * Cria o token em JWT (Json Web Token)
	 *
	 * @return JwtAccessTokenConverter
	 */
	@Bean
	public JwtAccessTokenConverter getAccessTokenConverter() {
		final JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("example");
		return accessTokenConverter;
	}

	/**
	 * Retorna o Token Store.
	 *
	 * @return TokenStore
	 */
	@Bean
	public TokenStore getTokenStore() {
		return new JwtTokenStore(getAccessTokenConverter());
	}

	/**
	 * Retorna o Token Enhancer.
	 *
	 * @return TokenEnhancer
	 */
	@Bean
	public TokenEnhancer getTokenEnhancer() {
	    return new CustomTokenEnhancer();
	}

}
