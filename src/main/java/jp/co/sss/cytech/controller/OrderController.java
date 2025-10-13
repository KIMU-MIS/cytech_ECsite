package jp.co.sss.cytech.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.sss.cytech.dto.CartItemDto;
import jp.co.sss.cytech.entity.Product;
import jp.co.sss.cytech.entity.User;
import jp.co.sss.cytech.form.OrderForm;
import jp.co.sss.cytech.repository.ProductRepository;
import jp.co.sss.cytech.service.OrderService;


@Controller
@RequestMapping("/order")
@SessionAttributes("cart") 
public class OrderController {
	
	@Autowired
    private ProductRepository productRepository; 
	
	@Autowired(required = false)
    private OrderService orderService;
	
	public OrderController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

	// 入力画面
	@GetMapping("/purchase")
	     public String purchaseForm(@SessionAttribute(value="cart", required=false) Map<Integer,Integer> cart,
                                    Model model) {

              List<CartItemDto> cartItems = new ArrayList<>();

              if (cart != null && !cart.isEmpty()) {
                 for (Map.Entry<Integer,Integer> entry : cart.entrySet()) {
                   Product p = productRepository.findById(entry.getKey()).orElse(null);
                  if (p != null) cartItems.add(new CartItemDto(p, entry.getValue()));
                 }
               }

              if (cartItems.isEmpty()) {
                  // 空カートならカート詳細へ戻す
                 return "redirect:/cart/detail";
               }

           model.addAttribute("cartItems", cartItems);
           model.addAttribute("orderForm", new OrderForm());
           
           return "order/purchaseDetail";
      }

	 
	// 確認画面へ遷移
	@PostMapping("/confirm")
          public String confirm(@ModelAttribute OrderForm orderForm,
                          @SessionAttribute("cart") Map<Integer,Integer> cart,
                          Model model) {

               List<CartItemDto> cartItems = new ArrayList<>();
               for (Map.Entry<Integer,Integer> entry : cart.entrySet()) {
                   Product p = productRepository.findById(entry.getKey()).orElse(null);
                   if (p != null) cartItems.add(new CartItemDto(p, entry.getValue()));
               }

               System.out.println(cartItems.size());
               cartItems.forEach(i -> System.out.println(i.getProduct().getProductName()));
        
               int total = cartItems.stream().mapToInt(CartItemDto::getTotalPrice).sum();
               
            model.addAttribute("mode", "single");
            model.addAttribute("mode", "cart");
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("orderForm", orderForm);
            model.addAttribute("total", total);

            return "order/purchaseConfirm";
        }
	    
	    
	 // 完了画面
	@PostMapping("/complete")
	     public String complete(@ModelAttribute OrderForm orderForm,
                           HttpSession session,
                           Model model) {
		
		     @SuppressWarnings("unchecked")
	           Map<Integer,Integer> cart = (Map<Integer,Integer>) session.getAttribute("cart");

	          if (cart == null || cart.isEmpty()) {
	               return "redirect:/cart/detail";
	         }

	         // ログインユーザー取得
	          User loginUser = (User) session.getAttribute("loginUser");
	          Integer userId = (loginUser != null) ? loginUser.getUserId() : 1; // 非会員は仮ID
	          
	          List<CartItemDto> cartItems = new ArrayList<>();
	          for (Map.Entry<Integer,Integer> entry : cart.entrySet()) {
	            Product p = productRepository.findById(entry.getKey()).orElse(null);
	            if (p != null) cartItems.add(new CartItemDto(p, entry.getValue()));
	          }

	         // 合計計算
	          int total = cartItems.stream()
	            .mapToInt(CartItemDto::getTotalPrice).sum();
	            
	
	         // DB保存
	           orderService.saveOrder(orderForm, cartItems, total, userId);

	         // 表示用データ
	          model.addAttribute("mode", "single");
	          model.addAttribute("mode", "cart");
	          model.addAttribute("cartItems", cartItems);
	          model.addAttribute("total", total);
	          model.addAttribute("orderForm", orderForm);

	         //  カートをクリア
	          session.removeAttribute("cart"); 

	          return "order/purchaseComplete"; // 完了画面へ
         }   
	             
 }
