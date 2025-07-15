package gift.domain.product.service;

import gift.domain.product.Product;
import gift.domain.product.dto.ProductRequest;
import gift.domain.product.dto.ProductResponse;
import gift.domain.product.dto.ProductUpdateRequest;
import gift.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse addProduct(ProductRequest req) {
        Product product = new Product(req.name(), req.price(), req.imageUrl());
        return ProductResponse.from(productRepository.save(product));
    }

    public ProductResponse getProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("ProductService : getProduct() failed - 404 Not Found Error");
        }
        Product product = optionalProduct.get();
        return ProductResponse.from(product);
    }

    @Transactional
    public void updateProduct(Long id, ProductUpdateRequest req) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("ProductService : updateProduct() failed - 404 Not Found Error");
        }
        Product product = optionalProduct.get();
        product.update(req.name(), req.price(), req.imageUrl());
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("ProductService : deleteProduct() failed - 404 Not Found Error");
        }
        productRepository.delete(optionalProduct.get());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return null;
        }
        return products
                .stream()
                .map(ProductResponse::from)
                .toList();
    }
}
