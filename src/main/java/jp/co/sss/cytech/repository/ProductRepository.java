package jp.co.sss.cytech.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.sss.cytech.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	@Query("SELECT p FROM Product p WHERE "
		     + "(:keyword IS NULL OR p.productName LIKE %:keyword%) AND "
		     + "(:categoryId IS NULL OR p.categoryId = :categoryId)")
		List<Product> searchProducts(@Param("keyword") String keyword, @Param("categoryId") Integer categoryId);
}
