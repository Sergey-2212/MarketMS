package ru.gb.servicecore.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.ProductDto;
import ru.gb.servicecore.entities.Product;
import ru.gb.servicecore.exceptions.NotFoundException;
import ru.gb.servicecore.services.ProductService;
import ru.gb.servicecore.converters.ProductConverter;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProductConverter productConverter;

//    @GetMapping
//    public List<ProductDto> findAllProducts() {
//        return productService.findAll().stream().map(p -> productConverter.entityToDtoConverter(p)).collect(Collectors.toList());
//
//    }

    @GetMapping
    public Page<ProductDto> getAllProductsBetween(@RequestParam(defaultValue = "0") Integer min,
                                                  @RequestParam(required = false) Integer max,
                                                  @RequestParam(required = false) Integer pageNumber,
                                                  @RequestParam(required = false, defaultValue = "50") Integer pageSize,
                                                  @RequestParam(required = false) String titleLike,
                                                  @RequestParam(required = false, defaultValue = "id") String sortProp) {
       log.info("getAllProductsBetween");
        if(max == null) { max = Integer.MAX_VALUE;}

//        if(sortProp == null) { sortProp = "id";}

        if(pageNumber == null || pageNumber < 1) {pageNumber = 1;}

//        if(pageSize == null) {pageSize = 50;}

        return productService.findAll(
                        min, max, pageNumber - 1, pageSize, titleLike, sortProp)
                .map( s -> productConverter.entityToDtoConverter(s));
    }
    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
      return productConverter.entityToDtoConverter(
               productService.findProductById(id)
                       .orElseThrow(() -> new NotFoundException(String.format("Product not found by id = %s",id))));
    }

    @PostMapping
    public ProductDto createNewProduct(@RequestBody ProductDto productDto) {
        Product product = productService.createNewProduct(productDto);
        return productConverter.entityToDtoConverter(product);
    }
}
