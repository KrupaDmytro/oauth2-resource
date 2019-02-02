package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;


@Configuration
@PropertySource({ "classpath:persistence.properties" })
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEowIBAAKCAQEAyHZsp7JTQy/Ts9/k2IyqHn1hEcxAK/Y/28LjaiOcRhpv1aH8\n" +
            "6qhw3ZyzGAHPE5qmcTljnrK+i+eXADU/pNZ1IBF1CyWCxo2whb1Q+hdxARLeBXPG\n" +
            "oYe9qChjEq7JBxxQ1T9RHcbn36wHTPVUvn61zzaFcndq93uqlAkao5lFUR+HuaFe\n" +
            "56wPEdUjz9/UkyMnIP+wlB6025nr/OH8dpLrJde60xyT8lxZlcDmiBscP0+CRcQL\n" +
            "sj4nXqxSz3nodD8Nd17GmwDGdSOh2Gkm3wz7igCn6s9ZmsZadryIrjBINLY/HVPf\n" +
            "v3SefCbmT2q9RAoswTZUsZidFnbVKIXwv4R+BwIDAQABAoIBAQDHPGxhf+shK7fN\n" +
            "Xwmj8KpkA1kmx0pAZ06wrNI5+4qmYkAkpAsrbp4+pC+b/LBDW8FxZwiMAjs/8b0y\n" +
            "h7npqvxeEvuxSGbh0JFRWwfQiNvXpVjlMlyIztDBAOL2/qDuYEY2q+eDIN30gJYA\n" +
            "9xRzAJzShe28BtRZhJ/U4feIAh3SsTB/osBWaqHSN17Y/cMcH8H4h5V+DY/0bnd9\n" +
            "hycTSrOTnI/RPYvrqEDiUZWabOrDkH9DlwiWPuc0Z9lT20P2w5Df4n3bfchZiElp\n" +
            "V3LVs2XvTAEnj02qiJpEAq9gf0OH0Lu91PTjDmy7M8GAd/86aNWE9g6B5CaYmUcL\n" +
            "ekLMteWBAoGBAPHWr8Y/jfbT4mPu6bSw9gTWnVKsVAwuzq6hUTF44MplqDdbksUE\n" +
            "/kTQpevQfjHzuQ5G92abx9UGrDjmRTgdmjqigCdmsp3GdwS2KAxCp9E0URp1rHd6\n" +
            "ZLux70xKsg0055lXS5U8cpyrUHhQ83C9hf/GhGBDtOOJ2qOiUNJck0jbAoGBANQz\n" +
            "fDE9XFE6I5snN0mZeKo2ebRZONbS6F70EG3Rq4+fG7EQ/DSC44i/wCTddYeQ12al\n" +
            "21/tHjtAnICN4WZhDY7XLt7ZxQ6eousKWua+M8F/LnNIrPSjeC02wlf7bPwGVJN6\n" +
            "4iw7r0C5gXyRAsbDGshftDR6BAODbfmqLAHfawFFAoGAJJooqmmuE+CH0DY1uvpZ\n" +
            "kevljcC1S869y2JxBnrUEu4F4rMCaL1TupiVtDYvE2Je7NFC2o3TVeOXp1j6uv6H\n" +
            "/D3iBZSejhCerODg+NIR0jEH4WhT/RVSL7JDlJltj2AleWjj8KdgdWPcXwlpfvPA\n" +
            "VzD1khhNRW7033VOpDgMtqcCgYAIxXwLuQKY5PNV1YAmja58MrZDKzD7SEqMqkAH\n" +
            "mhQiYqxNXGtgbEyleW+i1nimOul8d7yisXV/c4NEmRjJF5fs4J4yXgQUP6ByYMIz\n" +
            "KYeQJwavg0CK+Dree5X69wyhOk3CruCsfWcYINLmaEQaHTR9Nd+ID8ccshEYsd0R\n" +
            "0r9klQKBgGsdpIF7JjMlCud0bUV5nHrw6noLA3ptsgyKMmUAwbju/NHbIC7SO9Pa\n" +
            "4T7elWDV6biBrcHoUTAyKFbq4SG17qBvyN9T8rvpezy0o92mEI9XSelUQ7hL2zDv\n" +
            "dOxl43wjs6Lb1iS+TZtSPqmxF9poIt1DjLNiWw4uAjFBsmFNRxP/\n" +
            "-----END RSA PRIVATE KEY-----";
    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
}