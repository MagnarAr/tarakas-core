package ee.tarakas.core.goals;

import ee.tarakas.core.goals.dto.GoalMapper;
import ee.tarakas.core.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GoalController.class)
class GoalControllerIntegrationTest {

	@MockBean
	private UserService userService;

	@MockBean
	private GoalMapper goalMapper;

	@MockBean
	private GoalService goalService;

	@Resource
	private MockMvc mockMvc;

	@Test
	void testGet_withoutAuthentication_returnsUnauthorized() throws Exception {
		mockMvc.perform(get("/api/goals")).andExpect(status().isUnauthorized());
	}

	@Test
	void testPost_withoutAuthentication_returnsUnauthorized() throws Exception {
		mockMvc.perform(post("/api/goals")).andExpect(status().isUnauthorized());
	}

	@Test
	void testDelete_withoutAuthentication_returnsUnauthorized() throws Exception {
		mockMvc.perform(delete("/api/goals/123")).andExpect(status().isUnauthorized());
	}

	@WithMockUser("1")
	@Test
	void test() throws Exception {
		// TODO
		mockMvc.perform(post("/api/goals", content().json("{}")))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

}
