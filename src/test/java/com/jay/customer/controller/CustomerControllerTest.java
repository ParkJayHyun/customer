package com.jay.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class CustomerControllerTest {

    @Autowired
    MockMvc client;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Transactional
    @DisplayName("회원 정상 등록")
    void save() throws Exception {
        //given
        Map<String, String> data = new HashMap<>();
        data.put("userId", "customer");
        data.put("password", "customer1");
        data.put("email", "customer@naver.com");
        data.put("phoneNumber", "010-1111-1111");

        //when
        ResultActions perform = client.perform(
                        post("/customers/signUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(objectMapper.writeValueAsString(data)))
                .andDo(print());

        //then
        perform.andExpectAll(
                status().isCreated(),
                jsonPath("$.result").value("SUCCESS"),
                jsonPath("$.code").value("201")
        );
    }

    /**
     * UserId Verify
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @DisplayName("userId 검증")
    void verifyUserIdField() throws Exception {
        //given
        Map<String, String> data = new HashMap<>();
        data.put("userId", "111");
        data.put("password", "customer1");
        data.put("email", "customer@naver.com");
        data.put("phoneNumber", "010-1111-1111");

        //when
        ResultActions perform = client.perform(
                        post("/customers/signUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(objectMapper.writeValueAsString(data)))
                .andDo(print());

        //then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.errors.fields[0].name").value(equalTo("userId")),
                jsonPath("$.errors.fields[0].reason").value(equalTo("아이디는 최소5자, 최대 20자내에 입력해 주세요."))
        );
    }

    /**
     * Password Verify
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @DisplayName("password 검증")
    void verifyPasswordField() throws Exception {
        //given
        Map<String, String> data = new HashMap<>();
        data.put("userId", "customer");
        data.put("password", "customer");
        data.put("email", "customer@naver.com");
        data.put("phoneNumber", "010-1111-1111");

        //when
        ResultActions perform = client.perform(
                        post("/customers/signUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(objectMapper.writeValueAsString(data)))
                .andDo(print());

        //then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.errors.fields[0].name").value(equalTo("password")),
                //jsonPath("$.errors.fields[0].reason").value(equalTo("비밀번호는 최소8자 ~ 최대 15자내 입력해 주세요."))                                  //비밀번호 길이 체크
                jsonPath("$.errors.fields[0].reason").value(equalTo("영문대소문자, 숫자, 특수문자 2가지 이상 포함되게 입력해 주세요."))      //비밀번호 조합 체크
        );
    }

    /**
     * Email Verify
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @DisplayName("email 검증")
    void verifyEmailField() throws Exception {
        //given
        Map<String, String> data = new HashMap<>();
        data.put("userId", "customer");
        data.put("password", "customer1");
        data.put("email", "customer1");
        data.put("phoneNumber", "010-1111-1111");

        //when
        ResultActions perform = client.perform(
                        post("/customers/signUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(objectMapper.writeValueAsString(data)))
                .andDo(print());

        //then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.errors.fields[0].name").value(equalTo("email")),
                //jsonPath("$.errors.fields[0].reason").value(equalTo("이메일을 입력해 주세요."))                             //이메일 공백 체크
                jsonPath("$.errors.fields[0].reason").value(equalTo("이메일 형식에 맞게 입력해 주세요."))       //이메일 형식 체크
        );
    }

    /**
     * PhoneNumber Verify
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @DisplayName("휴대폰번호 검증")
    void verifyPhoneNumberField() throws Exception {
        //given
        Map<String, String> data = new HashMap<>();
        data.put("userId", "customer");
        data.put("password", "customer1");
        data.put("email", "customer@naver.com");
        data.put("phoneNumber", "0101111-1111");

        //when
        ResultActions perform = client.perform(
                        post("/customers/signUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(objectMapper.writeValueAsString(data)))
                .andDo(print());

        //then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.errors.fields[0].name").value(equalTo("phoneNumber")),
                jsonPath("$.errors.fields[0].reason").value(equalTo("휴대폰 형식에 맞춰서 입력해 주세요."))
        );
    }

    /**
     * All Field Verify
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @DisplayName("모든 필드 검증")
    void verifyAllField() throws Exception {
        //given
        Map<String, String> data = new HashMap<>();
        data.put("userId", "cus");
        data.put("password", "customer");
        data.put("email", "customer");
        data.put("phoneNumber", "0101111-1111");

        //when
        ResultActions perform = client.perform(
                        post("/customers/signUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(objectMapper.writeValueAsString(data)))
                .andDo(print());

        List<Map<String, String>> errorFields = JsonPath.parse(perform.andReturn().getResponse().getContentAsString()).read("$.errors.fields");
        String userId = "";
        String password = "";
        String email = "";
        String phoneNumber = "";
        for (Map<String, String> stringStringMap : errorFields) {
            if (stringStringMap.get("name").equals("userId")) {
                userId = stringStringMap.get("reason");
            }
            if (stringStringMap.get("name").equals("password")) {
                password = stringStringMap.get("reason");
            }
            if (stringStringMap.get("name").equals("email")) {
                email = stringStringMap.get("reason");
            }
            if (stringStringMap.get("name").equals("phoneNumber")) {
                phoneNumber = stringStringMap.get("reason");
            }
        }

        //then
        perform.andExpect(status().isBadRequest());
        Assertions.assertThat(userId).isEqualTo("아이디는 최소5자, 최대 20자내에 입력해 주세요.");
        Assertions.assertThat(password).isEqualTo("영문대소문자, 숫자, 특수문자 2가지 이상 포함되게 입력해 주세요.");
        Assertions.assertThat(email).isEqualTo("이메일 형식에 맞게 입력해 주세요.");
        Assertions.assertThat(phoneNumber).isEqualTo("휴대폰 형식에 맞춰서 입력해 주세요.");
    }

    /**
     * 회원 조회
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @DisplayName("회원가입 동시성 검증")
    void findCustomer() throws Exception {
        //given
        Map<String, String> data = new HashMap<>();
        data.put("userId", "customer");
        data.put("password", "customer1");
        data.put("email", "customer@naver.com");
        data.put("phoneNumber", "010-1111-1111");

        ResultActions signUpPerform = client.perform(
                        post("/customers/signUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(objectMapper.writeValueAsString(data)))
                .andDo(print());

        //when
        signUpPerform.andExpectAll(
                status().isCreated(),
                jsonPath("$.result").value("SUCCESS"),
                jsonPath("$.code").value("201")
        );

        int findId = JsonPath.parse(signUpPerform.andReturn().getResponse().getContentAsString()).read("$.id");

        ResultActions findPerform = client.perform(
                        get("/customers/" + findId))
                .andDo(print());

        //then
        findPerform.andExpectAll(
                jsonPath("$.customer.userId").value(equalTo(data.get("userId"))),
                jsonPath("$.customer.email").value(equalTo(data.get("email"))),
                jsonPath("$.customer.phoneNumber").value(equalTo(data.get("phoneNumber")))
        );
    }

    /**
     * 동시 회원가입 시 "이미 등록된 아이디"라는 응답값을 받는다.
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @DisplayName("currentSignUp")
    void currentSignUp() throws Exception {
        //given
        Map<String, String> data = new HashMap<>();
        data.put("userId", "customer");
        data.put("password", "customer1");
        data.put("email", "customer@naver.com");
        data.put("phoneNumber", "010-1111-1111");

        //when
        int requestCount = 2;
        String duplicateUserIdMessage = "";
        String signUpData = objectMapper.writeValueAsString(data);
        for (int i = 0; i < requestCount; i++) {
            ResultActions perform = client.perform(
                            post("/customers/signUp")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .characterEncoding("utf-8")
                                    .content(signUpData))
                    .andDo(print());
            MockHttpServletResponse response = perform.andReturn().getResponse();
            if (response.getStatus() == 400) {
                duplicateUserIdMessage = JsonPath.parse(perform.andReturn().getResponse().getContentAsString()).read("$.errors.message");
            }
        }
        Assertions.assertThat(duplicateUserIdMessage).isEqualTo("Already Exist Customer");
    }

}