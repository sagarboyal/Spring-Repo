package org.ecommerce.app.repository;

import org.ecommerce.app.model.Category;
import org.ecommerce.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
    List<Product> findByCategoryOrderByPriceAsc(Category category);
    List<Product> findByProductNameLikeIgnoreCase(String productName);
}
