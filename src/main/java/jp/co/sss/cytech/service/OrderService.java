package jp.co.sss.cytech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.cytech.dto.CartItemDto;
import jp.co.sss.cytech.entity.Order;
import jp.co.sss.cytech.entity.OrderItem;
import jp.co.sss.cytech.form.OrderForm;
import jp.co.sss.cytech.repository.OrderItemRepository;
import jp.co.sss.cytech.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    

    /**
     * 注文を保存する
     * @param orderForm 入力フォーム
     * @param cartItems カート内商品
     * @param total 合計金額
     */
    // 注文保存処理
	
	 public void saveOrder(OrderForm orderForm, List<CartItemDto> cartItems,int total, Integer userId) {
		        
		// DTO → Entity への変換
		    Order order = new Order();
		    order.setUserId(userId);
		    order.setTotalAmopunt(total); // 合計金額
		    order.setStatus("注文確定");  // ステータス（任意の固定文字列など）

		 // 注文データ保存
		    orderRepository.save(order);
		    
		 // さらにカートの中身を保存
		    for (CartItemDto item : cartItems) {
		        OrderItem orderItem = new OrderItem();
		        orderItem.setOrderId(order.getOrderId()); // orders.id と紐付け
		        orderItem.setProductId(item.getProduct().getProductId());
		        orderItem.setQuantity(item.getQuantity());
		        orderItem.setPrice(item.getProduct().getPrice());
		        orderItemRepository.save(orderItem);
		        
		    }
	 }
}
