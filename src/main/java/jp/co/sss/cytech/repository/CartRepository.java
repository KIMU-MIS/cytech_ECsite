package jp.co.sss.cytech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.cytech.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{
	void deleteByUserIdAndProductId(Integer userId, Integer productId);
}
