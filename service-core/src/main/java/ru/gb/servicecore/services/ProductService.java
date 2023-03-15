package ru.gb.servicecore.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.gb.api.ProductDto;
import ru.gb.servicecore.converters.ProductConverter;
import ru.gb.servicecore.entities.Product;
import ru.gb.servicecore.repositories.ProductRepository;
import ru.gb.servicecore.repositories.specifications.ProductSpecifications;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter converter;

    public List<Product> findAll () {
        return productRepository.findAll();
    }

    public Page<Product> findAll(Integer minPrice, Integer maxPrice, Integer pageNumber, Integer pageSize, String titleLike, String sortProp) {

        //Создаю объект Sort для определения порядка сортировки.
        Sort sort = Sort.by(Sort.Direction.ASC, sortProp);

        return productRepository.findAll(createSpecByFilters(minPrice, maxPrice, titleLike), PageRequest.of(pageNumber, pageSize, sort));
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createNewProduct(ProductDto productDto) {
      return productRepository.save(converter.dtoToEntityConverter(productDto));
    }
    //Lesson9 00:51
    public Specification<Product> createSpecByFilters(Integer minPrice, Integer maxPrice, String title) {
        Specification<Product> spec = Specification.where(null);
        if(minPrice != null) {
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualTo(minPrice));
        }

        if(maxPrice != null) {
            spec = spec.and(ProductSpecifications.priceLessOrEqualTo(maxPrice));
        }

        if(title != null) {
            spec = spec.and(ProductSpecifications.titleLike(title));
        }

        return spec;
    }
}
