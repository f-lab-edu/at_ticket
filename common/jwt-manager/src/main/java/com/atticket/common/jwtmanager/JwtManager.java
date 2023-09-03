package com.atticket.common.jwtmanager;

import java.util.Base64;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtManager {

	private static String JWT_SECRET_KEY;

	private static ObjectMapper objectMapper = new ObjectMapper();

	@Value("${jwt-secret-key}")
	public void setKey(String value) {
		this.JWT_SECRET_KEY = value;
	}

	public static UserInfo getUserInfo() {
		Object claims = parseToClaims();
		return new UserInfo(objectMapper.convertValue(claims, Map.class));
	}

	private static Object parseToClaims() {
		String token = getJwt();
		if (!StringUtils.hasText(token)) {
			throw new BaseException(BaseStatus.NO_TOKEN);
		}
		final byte[] keyBytes = Base64.getUrlDecoder().decode(JWT_SECRET_KEY);
		try {
			return Jwts.parser().setSigningKey(keyBytes).parseClaimsJws(token).getBody();
		} catch (SignatureException e) {
			throw new BaseException(BaseStatus.INVALID_TOKEN);
		} catch (ExpiredJwtException e) {
			throw new BaseException(BaseStatus.EXPIRED_TOKEN);
		}
	}

	private static String getJwt() {
		HttpServletRequest request =
			((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		return request.getHeader("Authorization");
	}
}
