package com.atticket.user.controller;

import com.atticket.common.jwtmanager.JwtManager;
import com.atticket.common.jwtmanager.UserInfo;
import com.atticket.user.client.dto.response.GetAccessTokenResDto;
import com.atticket.user.dto.request.LoginUserReqDto;
import com.atticket.user.dto.request.RegisterUserReqDto;
import com.atticket.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;

	@MockBean
	private JwtManager jwtManager;

	@Test
	@DisplayName("회원가입")
	void registerUser() throws Exception {

		String username = "kimjunbo";
		String password = "atticket";
		String email = "kimjunbo@test.com";
		String name = "김준영";
		String phone = "010-1234-1234";

		RegisterUserReqDto registerUserReqDto = new RegisterUserReqDto(
			username, password, email, name, phone
		);

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.post("/users")
				.content(objectMapper.writeValueAsString(registerUserReqDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		);

		result.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andDo(document("registerUser",
					//Json 포매팅
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					requestFields(
						fieldWithPath("username").description("유저 아이디"),
						fieldWithPath("password").description("비밀번호"),
						fieldWithPath("email").description("이메일"),
						fieldWithPath("name").description("이름"),
						fieldWithPath("phone").description("전화번호")
					),
					responseFields(
						fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
						fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지")
					)
				)
			);
	}

	@Test
	@DisplayName("로그인")
	void loginUser() throws Exception {

		String username = "kimjunbo";
		String password = "atticket";

		LoginUserReqDto reqDto = new LoginUserReqDto(username, password);

		GetAccessTokenResDto resDto = new GetAccessTokenResDto();
		resDto.setAccess_token("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lk...");
		resDto.setExpires_in(3600);
		resDto.setRefresh_expires_in(604800);
		resDto.setRefresh_token("eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJh...");
		resDto.setToken_type("Bearer");

		given(userService.login(any())).willReturn(resDto);

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.post("/users/token")
				.content(objectMapper.writeValueAsString(reqDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		);

		result.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andDo(document("loginUser",
					//Json 포매팅
					preprocessRequest(prettyPrint()),
					preprocessResponse(prettyPrint()),
					requestFields(
						fieldWithPath("username").description("유저 아이디").optional(),
						fieldWithPath("password").description("비밀번호").optional()
					),
					responseFields(
						fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
						fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
						fieldWithPath("data.access_token")
							.type(JsonFieldType.STRING)
							.description("access 토큰").optional(),
						fieldWithPath("data.expires_in")
							.type(JsonFieldType.NUMBER)
							.description("access 토큰 만료시간").optional(),
						fieldWithPath("data.refresh_expires_in")
							.type(JsonFieldType.NUMBER)
							.description("refresh 토큰 만료시간").optional(),
						fieldWithPath("data.refresh_token")
							.type(JsonFieldType.STRING)
							.description("refresh 토큰").optional(),
						fieldWithPath("data.token_type")
							.type(JsonFieldType.STRING)
							.description("토큰 타입").optional()
					)
				)
			);
	}


	@Test
	@DisplayName("유저 정보 조회")
	void getUser() throws Exception {

		String accessToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lk...";

		Map<String, String> userInfoMap = new HashMap<>();

		userInfoMap.put("sub", "keycloak-uuid");
		userInfoMap.put("email", "keycloak-email");
		userInfoMap.put("name", "keycloak-name");
		userInfoMap.put("phone_number", "keycloak-phone_number");

		given(userService.getUserInfo()).willReturn(new UserInfo(userInfoMap));

		ResultActions result = this.mockMvc.perform(
			RestDocumentationRequestBuilders.get("/users")
				.header("Authorization", accessToken)
		);


		result.andExpect(status().isOk()).andDo(document("getUser",
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			requestHeaders(
				headerWithName("Authorization").description("Basic auth credentials")),
			responseFields(
				fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
				fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
				fieldWithPath("data.userId")
					.type(JsonFieldType.STRING)
					.description("유저 아이디"),
				fieldWithPath("data.email")
					.type(JsonFieldType.STRING)
					.description("이메일"),
				fieldWithPath("data.name")
					.type(JsonFieldType.STRING)
					.description("이름"),
				fieldWithPath("data.phone")
					.type(JsonFieldType.STRING)
					.description("전화번호")
			)


		));


	}
}
