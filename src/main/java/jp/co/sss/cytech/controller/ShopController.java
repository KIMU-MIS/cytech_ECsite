package jp.co.sss.cytech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.cytech.entity.Category;
import jp.co.sss.cytech.entity.Product;
import jp.co.sss.cytech.entity.Review;
import jp.co.sss.cytech.repository.CategoryRepository;
import jp.co.sss.cytech.repository.ProductRepository;
import jp.co.sss.cytech.repository.ReviewRepository;

@Controller
public class ShopController {

	
	@Autowired
    private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
    private ReviewRepository reviewRepository;
	
	//検索
	@GetMapping("/productsearch")
        public String listProducts(@RequestParam(name = "keyword", required = false) String keyword,
                                    @RequestParam(name = "categoryId", required = false) String categoryIdStr,
                                    Model model) {

             List<Product> products;

             Integer categoryId = null;
             if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                 categoryId = Integer.valueOf(categoryIdStr);
             }
        
             if ((keyword == null || keyword.isEmpty()) && categoryId == null) {
                  products = productRepository.findAll();
            } else {
                  products = productRepository.searchProducts(keyword, categoryId);
             }

            List<Category> categories = categoryRepository.findDistinctCategories();
            System.out.println("categories size: " + categories.size());
            categories.forEach(c -> System.out.println(c.getCategoryName()));
            
            model.addAttribute("products", products);
            model.addAttribute("categories", categories);
            model.addAttribute("keyword", keyword);
            model.addAttribute("selectedCategory", categoryId);

           return "shop/productList"; // 既存の一覧ビューに合わせて
    }
	
	
	//トップページ
	@RequestMapping(path = "/toppage", method = RequestMethod.GET)
	     public String toppage(Model model) {
		
		    // DBから商品を全件取得
             List<Product> products = productRepository.findAll();
           //viewに渡す
	         model.addAttribute("products", products);
		
		   return "shop/shopTop";
		
	}
	
	// 商品詳細ページ
    @GetMapping("/product/detail/{id}")
        public String productDetails(@PathVariable("id") Integer id, 
        		                     Model model) {
    	
    	   return productRepository.findById(id).map(product -> {
    	            model.addAttribute("product", product);
    	            
    	           // レビューを取得
    	            List<Review> reviews = reviewRepository.findByProductId(id);
    	            model.addAttribute("reviews", reviews);
    	            
    	          return "shop/productDetails";
    	   })
    	    .orElse("error/404");  // 404エラーページへ
    }
	
    
	//商品一覧ページ
    @GetMapping("/products")
        public String listProducts(Model model) {
    	
          List<Product> products = productRepository.findAll();
          model.addAttribute("products", products);
        
         return "shop/productList"; // 表示用テンプレート
    } 
 
}
