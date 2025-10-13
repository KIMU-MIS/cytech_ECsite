package jp.co.sss.cytech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jp.co.sss.cytech.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	@Query("SELECT c FROM Category c")
    List<Category> findDistinctCategories();
}
