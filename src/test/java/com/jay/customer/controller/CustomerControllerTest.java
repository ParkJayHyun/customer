package com.jay.customer.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Slf4j
class CustomerControllerTest {

    @Autowired
    CustomerApiController controller;

    MockMvc mock;

    @BeforeEach
    public void init() {
        mock = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("회원 등록")
    void save() throws Exception {
        //given
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("userId", "jay");
        data.add("password", "qkrwogus");
        data.add("email", "kokikiko@naver.com");
        data.add("phoneNumber", "010-2214-1242");
        //when
        ResultActions perform = mock.perform(
                        post("/customers/signUp")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(String.valueOf(data)))
                .andDo(print());
        //then
        perform.andExpect(status().is3xxRedirection());

    }


}