package gift.domain.product.controller;

import gift.domain.product.dto.OptionListResponse;
import gift.domain.product.dto.OptionRequest;
import gift.domain.product.service.OptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/products/{productId}/options")
public class OptionController {
    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping
    public ResponseEntity<OptionListResponse> getOptionList(
            @PathVariable(name = "productId") Long productId
    ) {
        var options = optionService.getProductOptions(productId);
        var responseBody = new OptionListResponse(options);
        return new ResponseEntity<>(responseBody, OK);
    }

    @PostMapping
    public ResponseEntity<Void> addOptions(
            @PathVariable(name = "productId") Long productId,
            @RequestBody OptionRequest optionRequest
    ) {
        optionService.createOption(productId, optionRequest);
        return new ResponseEntity<>(CREATED);
    }
}
