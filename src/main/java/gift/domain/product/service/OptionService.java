package gift.domain.product.service;

import gift.domain.product.Option;
import gift.domain.product.Product;
import gift.domain.product.dto.OptionRequest;
import gift.domain.product.dto.OptionResponse;
import gift.domain.product.repository.OptionRepository;
import gift.domain.product.repository.ProductRepository;
import gift.global.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public List<OptionResponse> getProductOptions(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("ProductService : getProductOptions() failed", productId));
        return optionRepository.findAllByProductId(productId)
                .stream()
                .map(OptionResponse::from)
                .toList();
    }

    public void createOption(Long productId, OptionRequest optionRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("ProductService : createOptions() failed", productId));
        if (optionRepository.existsByProductAndName(product, optionRequest.name())){
            throw new IllegalArgumentException("이미 존재하는 옵션 이름입니다.");
        }
        Option option = new Option(optionRequest.name(), optionRequest.quantity(), product);
        optionRepository.save(option);
        product.getOptions().add(option);
    }
}
