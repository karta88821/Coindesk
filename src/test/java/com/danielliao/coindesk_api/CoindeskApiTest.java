package com.danielliao.coindesk_api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.danielliao.coindesk_api.coindesk.CoindeskService;

@SpringBootTest(classes = {CoindeskApiApplication.class})
@AutoConfigureMockMvc
class CoindeskApiApplicationTests {

	public static Logger log = LoggerFactory.getLogger(CoindeskApiApplicationTests.class);

	@Autowired
	private CoindeskService coindeskService;

	@Autowired
    private MockMvc mockMvc;

	@Test
	void testCallCoindeskApi() throws IOException {
		String jsonString = coindeskService.getJsonString();
		log.info(jsonString);
	}

	@Test
	void testCallConvertedApi() throws Exception {
		mockMvc.perform(get("/api/callCoindesk"))
        .andExpect(status().isOk())
		.andExpect(jsonPath("$.message").value("Calling coindesk api success!"))
        .andDo(print());
	}

}
