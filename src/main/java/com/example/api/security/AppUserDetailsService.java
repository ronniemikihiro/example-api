package com.example.api.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.example.api.model.Usuario;
import com.example.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Trata os detalhes do usuário.
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	/**
	 * Verifica se o usuário e senha são válidos.
	 *
	 * @param email
	 * @return UserDetails
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		final Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
		final Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos!"));
		return new UsuarioSistema(usuario, getPermissoes(usuario));
	}

	/**
	 * Monta as permissões com suas autoridades.
	 *
	 * @param usuario
	 * @return Collection<? extends GrantedAuthority>
	 */
	private Collection<? extends GrantedAuthority> getPermissoes(final Usuario usuario) {
		final Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		usuario.getPermissoes().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
		return authorities;
	}

}
