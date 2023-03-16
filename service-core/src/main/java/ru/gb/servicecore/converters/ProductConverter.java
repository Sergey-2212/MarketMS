package ru.gb.servicecore.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.api.ProductDto;
import ru.gb.servicecore.entities.Product;
import ru.gb.servicecore.exceptions.NotFoundException;
import ru.gb.servicecore.repositories.CategoryRepository;


@Component
@RequiredArgsConstructor
public class ProductConverter {

    private CategoryRepository categoryRepository;

    public Product dtoToEntityConverter (ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryRepository.findByCategory(productDto.getCategoryTitle()).orElseThrow(
                () -> new NotFoundException("Category is not found by title: " + productDto.getCategoryTitle())
        ));
        return product;
    }

    public ProductDto entityToDtoConverter(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice(), product.getCategory().getCategory());

    }

}
