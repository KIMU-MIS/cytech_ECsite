package jp.co.sss.cytech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.cytech.dto.CartItemDto;
import jp.co.sss.cytech.entity.Product;
import jp.co.sss.cytech.entity.User;
import jp.co.sss.cytech.repository.CartRepository;
import jp.co.sss.cytech.repository.ProductRepository;
import jp.co.sss.cytech.service.CartService;

@SessionAttributes("cart")
@Controller
@RequestMapping("/cart")
public class CartController {
	
	private final ProductRepository productRepository;
	private  CartRepository cartRepository;

    public CartController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Autowired(required = false)
    private CartService cartService;

  // セッションごとにカートを初期化➔ログインしていなくてもブラウザごとに独立したカート
    @ModelAttribute("cart")
          public Map<Integer, Integer> cart() {
            return new HashMap<>();
         }
    
  // 商品をカートに追加(カート追加画面)  
   @PostMapping("/add/{id}")
          public String addToCart(@PathVariable Integer id,
                                 @RequestParam("quantity") int quantity,
                                  HttpSession session,
                                              RedirectAttributes redirectAttributes) {

            Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
          
            if (cart == null) {
              cart = new HashMap<>();
            }

            // 数量を追加
             cart.put(id, quantity);
             session.setAttribute("cart", cart);

            // RedirectAttributes で商品IDを引き継ぐ
             redirectAttributes.addAttribute("id", id);

           
            // 必ずカート追加画面に遷移
              return "redirect:/cart/add/confirm?id=" + id;
     }


     
   // カート追加確認画面
    @GetMapping("/add/confirm")
          public String showCartAddPage(@RequestParam("id") Integer id,
                                         HttpSession session,
                                                      Model model) {

            Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
          
            if (cart == null || !cart.containsKey(id)) {
              throw new IllegalArgumentException("Cart does not contain this product.");
           }

          // 対象商品のみ取得
            Product product = productRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

            CartItemDto cartItem = new CartItemDto(product, cart.get(id));
            model.addAttribute("cartItem", cartItem);

          // カート全体の合計を計算（オプション）
            int total = cart.entrySet().stream()
               .mapToInt(entry -> {
            Product p = productRepository.findById(entry.getKey())
               .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + entry.getKey()));
         
             return p.getPrice() * entry.getValue();
            })
            .sum();
           model.addAttribute("total", total);

          // 必ず cartAdd.html を返す
             return "cart/cartAdd";
    }
    

 // カート全体表示（カート詳細画面）
  @GetMapping("/detail")
        public String cartDetail(@ModelAttribute("cart") Map<Integer, Integer> cart, Model model) {
             List<CartItemDto> cartItems = cart.entrySet().stream()
                .map(entry -> {
                  Product p = productRepository.findById(entry.getKey()).orElse(null);
                  return p != null ? new CartItemDto(p, entry.getValue()) : null;
              })
              .filter(Objects::nonNull)
              .toList();

            int totalQuantity = cartItems.stream().mapToInt(CartItemDto::getQuantity).sum();
            int totalPrice = cartItems.stream().mapToInt(CartItemDto::getTotalPrice).sum();
            int totalTaxPrice = (int)(totalPrice * 1.1);

            model.addAttribute("cartItems", cartItems);
            model.addAttribute("totalQuantity", totalQuantity);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalTaxPrice", totalTaxPrice);

            return "cart/cartDetail";
   }
  
  @PostMapping("/delete")
         public String deleteCartItem(@RequestParam("productId") Integer productId,
                                       HttpSession session) {
	  
            // ログイン中ユーザーのIDを取得
             User loginUser = (User) session.getAttribute("loginUser");
             Integer userId = (loginUser != null) ? loginUser.getUserId() : 1; // 仮IDなど

            // サービスを呼び出して削除処理
             cartService.deleteItemFromCart(userId, productId);

            // カート詳細画面を再読み込み
            return "cart/cartDetail";
   }
}
