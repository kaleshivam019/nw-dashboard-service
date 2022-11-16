package com.ey.nwdashboard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${clientId}")
	private String clientId;

	@Value("${clientSecret}")
	private String clientSecret;

	@Value("${grantType}")
	private String grantType;

	@Value("${authorizationCode}")
	private String authorizationCode;

	@Value("${refreshToken}")
	private String refreshToken;

	@Value("${implicitValue}")
	private String implicitValue;

	@Value("${scopeRead}")
	private String scopeRead;

	@Value("${scopeWrite}")
	private String scopeWrite;

	@Value("${trustValue}")
	private String trustValue;

	@Value("${accessTokenValidity}")
	private int accessTokenValidity;

	@Value("${refreshTokenValidity}")
	private int refreshTokenValidity;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {

		configurer
				.inMemory()
				.withClient(clientId)
				.secret(clientSecret)
				.authorizedGrantTypes(grantType, authorizationCode, refreshToken, implicitValue )
				.scopes(scopeRead, scopeWrite, trustValue)
				.accessTokenValiditySeconds(accessTokenValidity).
				refreshTokenValiditySeconds(refreshTokenValidity);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore)
				.authenticationManager(authenticationManager);
	}

}
