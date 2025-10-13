package jp.co.sss.cytech.controller;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.sss.cytech.entity.Review;
import jp.co.sss.cytech.entity.User;
import jp.co.sss.cytech.form.ReviewForm;
import jp.co.sss.cytech.repository.ReviewRepository;

@Controller
public class ReviewController {
	
	@Autowired
    private ReviewRepository reviewRepository;

	@GetMapping("/products/{productId}/review/new")
        public String reviewForm(@PathVariable Integer productId,
        		                 Model model) {
		
		    model.addAttribute("productId", productId);
            model.addAttribute("reviewForm", new ReviewForm());
        
          return "shop/reviewForm";
    }

	
    @PostMapping("/products/{productId}/review/new")
         public String submitReview(@PathVariable Integer productId,
    		                        @ModelAttribute ReviewForm form,
                                    HttpSession session) {
    	
    	      Review review = new Review();
    	
    	     // ログインしている場合は userId をセット
              User loginUser = (User) session.getAttribute("loginUser");
              if (loginUser != null) {
                 review.setUserId(loginUser.getUserId());
                 review.setDummyUserName(null); // ログインユーザー名は dummyUserName ではなく userId で管理
             } else {
        	     review.setUserId(null); // 非会員用の仮ID
                 review.setDummyUserName(form.getUserName()); // 任意入力の名前
             }

             review.setProductId(productId);
             review.setRating(form.getRating());
             review.setComment(form.getComment());
             review.setCreatedAt(LocalDateTime.now());
             review.setUpdatedAt(LocalDateTime.now());
       
             reviewRepository.save(review);

             System.out.println("ユーザー名: " + form.getUserName());
             System.out.println("評価: " + form.getRating());
             System.out.println("メール: " + form.getEmail());
             System.out.println("コメント: " + form.getComment());
        
        
            return "redirect:/products" ; //商品一覧画面
    }
    
}
