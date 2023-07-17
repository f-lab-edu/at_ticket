package com.atticket.common.jwtmanager;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.atticket.common.response.BaseException;
import com.atticket.common.response.BaseStatus;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JwtManager {

	private static final String JWT_SECRET_KEY = "RnsOGLI8iBkiS2NT7aoWa10INYGllfWgtt_gUhnyzXfEUuKAjgLguHp_2G9vFvBe5Rf2WV49J7HzDjbaZOtsiw";

	public static Object parseToClaims() {
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
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		return request.getHeader("Authorization");
	}
}
