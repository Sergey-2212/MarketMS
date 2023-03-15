package ru.gb.servicecore.converters;

import org.springframework.stereotype.Component;
import ru.gb.api.ProductDto;
import ru.gb.servicecore.entities.Product;


@Component
public class ProductConverter {

    public Product dtoToEntityConverter (ProductDto productDto) {

        Product product = new Product();

        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());

        return product;
    }

    public ProductDto entityToDtoConverter(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());

    }

}
