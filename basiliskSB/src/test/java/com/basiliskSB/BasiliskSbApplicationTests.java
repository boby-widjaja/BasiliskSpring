package com.basiliskSB;

import com.basiliskSB.rest.CategoryRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BasiliskSbApplicationTests {

	@Autowired
	private CategoryRestController categoryRestController;

	@Autowired
	private MockMvc mockMvc;

	private final String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUcnlvdXQiLCJ1c2VybmFtZSI6ImJvYnkud2lkamFqYSIsInJvbGUiOiJBZG1pbmlzdHJhdG9yIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo3MDcwIiwiYXVkIjoiQmFzaWxpc2tXZWJVSSJ9.rZOpJy96sLCd4FvYzpQ9cwCJsEO_WjdPxGaOgNhEQg4";

	@Test
	void assertGetCategory() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/category").header("Authorization", token)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void assertPostProduct() throws Exception{
		var uploadDirectory = "src/main/resources/static/resources/image/product/simple.jpg";
		var path = Paths.get(uploadDirectory);
		String name = "image";
		String originalFileName = "simple.jpg";
		String contentType = "image/jpg";
		byte[] content = null;
		try {
			content = Files.readAllBytes(path);
		} catch (Exception exception) {
		}
		var attachment = new MockMultipartFile(name, originalFileName, contentType, content);

		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/product/alternate").file(attachment)
			.header("Authorization", token).contentType(MediaType.MULTIPART_FORM_DATA)
					.param("name", "Bearded Dragon")
					.param("supplierId", "46")
					.param("categoryId", "268")
					.param("description", "Kadal yang berasal dari Australia.")
					.param("price", "750000")
					.param("stock", "9")
					.param("onOrder", "3")
					.param("discontinue", "false")
			)
			.andDo(print()).andExpect(status().isCreated());
	}

}
