package gift.domain.option;

import gift.domain.product.controller.OptionController;
import com.fasterxml.jackson.databind.ObjectMapper;
import gift.domain.product.dto.OptionRequest;
import gift.domain.product.dto.OptionResponse;
import gift.domain.product.service.OptionService;
import gift.domain.resolver.LoginMemberArgumentResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OptionController.class)
class OptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OptionService optionService;

    @MockitoBean
    private LoginMemberArgumentResolver loginMemberArgumentResolver;

    @Test
    @DisplayName("GET /api/products/{productId}/options - 옵션 목록 조회 성공")
    void getOptionList() throws Exception {
        // given
        Long productId = 1L;
        List<OptionResponse> response = List.of(new OptionResponse(1L, "Option 1", 10));
        given(optionService.getProductOptions(productId)).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/products/{productId}/options", productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.options").isArray())
                .andExpect(jsonPath("$.options[0].name").value("Option 1"));
    }

    @Test
    @DisplayName("POST /api/products/{productId}/options - 옵션 추가 성공")
    void addOptions() throws Exception {
        // given
        Long productId = 1L;
        OptionRequest request = new OptionRequest("New Option", 100);

        // when & then
        mockMvc.perform(post("/api/products/{productId}/options", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(optionService).createOption(eq(productId), any(OptionRequest.class));
    }
}
