package jp.co.sss.cytech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.cytech.repository.CartRepository;

@Service
public class CartService {
	
	@Autowired
    private CartRepository cartRepository;

    public void deleteItemFromCart(Integer userId, Integer productId) {
        cartRepository.deleteByUserIdAndProductId(userId, productId);
    }
}
