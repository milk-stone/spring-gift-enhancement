package gift.domain.option;

import gift.domain.product.service.OptionService;
import gift.domain.product.Option;
import gift.domain.product.Product;
import gift.domain.product.dto.OptionRequest;
import gift.domain.product.dto.OptionResponse;
import gift.domain.product.repository.OptionRepository;
import gift.domain.product.repository.ProductRepository;
import gift.global.exception.ProductNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OptionServiceTest {

    @InjectMocks
    private OptionService optionService;

    @Mock
    private OptionRepository optionRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("특정 상품의 옵션 목록 조회 성공")
    void getProductOptions_Success() {
        // given
        Long productId = 1L;
        Product product = new Product("Test Product", 10000L, "test.jpg");
        List<Option> options = List.of(new Option("Option1", 10, product));

        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(optionRepository.findAllByProductId(productId)).willReturn(options);

        // when
        List<OptionResponse> result = optionService.getProductOptions(productId);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("Option1");
        verify(productRepository).findById(productId);
        verify(optionRepository).findAllByProductId(productId);
    }

    @Test
    @DisplayName("옵션 목록 조회 시 상품이 없으면 예외 발생")
    void getProductOptions_ProductNotFound() {
        // given
        Long productId = 99L;
        given(productRepository.findById(productId)).willReturn(Optional.empty());

        // when & then
        assertThrows(ProductNotFoundException.class, () -> {
            optionService.getProductOptions(productId);
        });
    }

    @Test
    @DisplayName("정상적인 이름으로 옵션 생성 성공")
    void createOption_Success() {
        // given
        Long productId = 1L;
        OptionRequest request = new OptionRequest("NewOption", 20);
        Product product = new Product("Test Product", 10000L, "test.jpg");

        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(optionRepository.existsByProductAndName(product, request.name())).willReturn(false);

        // when
        optionService.createOption(productId, request);

        // then
        verify(optionRepository).save(any(Option.class));
    }

    @Test
    @DisplayName("중복된 이름의 옵션 생성 시 예외 발생")
    void createOption_DuplicateName() {
        // given
        Long productId = 1L;
        OptionRequest request = new OptionRequest("ExistingOption", 20);
        Product product = new Product("Test Product", 10000L, "test.jpg");

        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(optionRepository.existsByProductAndName(product, request.name())).willReturn(true);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            optionService.createOption(productId, request);
        });
    }

    @Test
    @DisplayName("옵션 이름에 공백이 포함된 경우 예외 발생")
    void createOption_NameIsBlank() {
        // given
        Long productId = 1L;
        // 이름이 공백으로만 이루어진 요청
        OptionRequest request = new OptionRequest("공백 포함된 옵션", 30);
        Product product = new Product("Test Product", 10000L, "test.jpg");

        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        // 중복 검사는 통과했다고 가정
        given(optionRepository.existsByProductAndName(product, request.name())).willReturn(false);

        // when & then
        // Option 생성자 내부의 validateName에서 예외가 발생해야 함
        assertThrows(IllegalArgumentException.class, () -> {
            optionService.createOption(productId, request);
        }, "옵션 이름에 공백을 사용할 수 없습니다.");
    }
}