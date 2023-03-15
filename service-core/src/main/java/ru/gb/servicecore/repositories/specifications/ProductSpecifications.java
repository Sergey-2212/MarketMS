package ru.gb.servicecore.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.gb.servicecore.entities.Product;

public class ProductSpecifications {

    public static Specification<Product> priceGreaterOrEqualTo(Integer price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> priceLessOrEqualTo(Integer price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> titleLike(String title) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", title));

    }
}
