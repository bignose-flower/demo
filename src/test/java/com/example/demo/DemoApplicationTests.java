package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class DemoApplicationTests {
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;

	@BeforeEach
	public void before() {
			mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.build();
	}
	
	@Test
	public void controllerTest() throws Exception {
		// MockMultipartFile作成
		var multipartFile = createMockMultipartFile("uploadFile", "uploadTest.csv");
		
		//テスト実施・検証
		mockMvc.perform(multipartRequest(multipartFile))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").value("aaaa,bbbb,cccc"));
	}
	
	//MockMultipartFile作成
	private MockMultipartFile createMockMultipartFile(String key, String testFileName) {
		try {
			return new MockMultipartFile(
					key, //ファイルの名前
					new FileInputStream("src/test/resources/" + testFileName)); //ファイルの内容
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	// multipartRequest
	private RequestBuilder multipartRequest(Object multipartFile) {
		return multipart("/students/upload") //controllerのURL
				.file((MockMultipartFile) multipartFile) //作成したMockMultipartFile
				.contentType(MediaType.MULTIPART_FORM_DATA) //リクエスト時のcontentType:multipart/.form-data
				.accept(MediaType.APPLICATION_JSON); //返却はKJSON
				
	}

}
