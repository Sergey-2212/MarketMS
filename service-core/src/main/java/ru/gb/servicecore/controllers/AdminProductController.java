package ru.gb.servicecore.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.ProductDto;
import ru.gb.servicecore.converters.ProductConverter;
import ru.gb.servicecore.entities.Product;
import ru.gb.servicecore.exceptions.UserIsNotAdminException;
import ru.gb.servicecore.services.ProductService;
import ru.gb.servicecore.validators.EntityValidator;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/v1/products")
public class AdminProductController {

    private final ProductService productService;
    private final ProductConverter productConverter;
    private final EntityValidator validator;
    private static final String ROLE_ADMIN = "[ROLE_ADMIN]";


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createNewProduct (@RequestBody ProductDto productDto, @RequestHeader String username, @RequestHeader String role) {
        log.info("Username - " + username + ", role - " + role);
        if(!role.equals(ROLE_ADMIN)) {
            throw new UserIsNotAdminException(String.format("User - %s has no admin role", username));
        }
        validator.checkProductDto(productDto);
        Product product = productService.createNewProduct(productDto);
        return productConverter.entityToDtoConverter(product);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct (@RequestBody ProductDto productDto, @RequestHeader String username, @RequestHeader String role) {
        if(!role.equals(ROLE_ADMIN)) {
            throw new UserIsNotAdminException(String.format("User - %s has no admin role", username));
        }
        validator.checkProductDto(productDto);
        productService.updateProduct(productConverter.dtoToEntityConverter(productDto));
    }

    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct (@RequestParam Long id, @RequestHeader String username, @RequestHeader String role) {
        if(!role.equals(ROLE_ADMIN)) {
            throw new UserIsNotAdminException(String.format("User - %s has no admin role", username));
        }
        productService.remove(id);
    }
}
