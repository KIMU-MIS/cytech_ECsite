package jp.co.sss.cytech.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.cytech.entity.User;
import jp.co.sss.cytech.form.LoginForm;
import jp.co.sss.cytech.repository.UserRepository;

@Controller
public class LoginController {

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	       public String loginForm(Model model) {
		   model.addAttribute("loginForm", new LoginForm());
		      return "login/loginForm";
		
	}
	
	
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	       public String doLoginForm(@Validated LoginForm form,
	    		                      BindingResult bindingResult,
                                      HttpSession session, 
                                      RedirectAttributes redirectAttributes,
                                      Model model) {
		
             System.out.println("メールアドレス:" + form.getEmail());
	         System.out.println("パスワード:" + form.getPasswords());
		
	         if (bindingResult.hasErrors()) {
	              // 入力エラー時の処理（フォームに戻すなど）
	        	 bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));//サーバー表示
	        	 return "login/loginForm";
	          }
	         
		    // DBからメールアドレスでユーザーを検索
	          User user = userRepository.findByEmail(form.getEmail());
          
	        if (user != null && user.getPasswords().equals(form.getPasswords())) {
	            // ログイン成功
	             session.setAttribute("loginUser", user);  // セッションにユーザー情報を保存
	             session.setAttribute("userName", user.getUserName()); // ヘッダー表示用
	           return "shop/shopTop";  // ログイン後のトップページ
	          
	      } else {
	    	
	            // ログイン失敗
	             model.addAttribute("errorMessage", "メールアドレスまたはパスワードが正しくありません");
	             return "login/loginForm";
	      }
	}
	
	@RequestMapping(path = "/register", method = RequestMethod.GET)
	       public String registerForm(Model model) {
		      model.addAttribute("newUserForm", new LoginForm());
		      return "login/newUserForm";
		
	}
	
	@RequestMapping(path = "/register", method = RequestMethod.POST)
	       public String doRegisterForm(@Validated @ModelAttribute("newUserForm") LoginForm form,
	    		                         BindingResult bindingResult) {
		
		    if (bindingResult.hasErrors()) {
              // 入力エラー時の処理（フォームに戻すなど）
		    	bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));//サーバー表示
              return "login/newUserForm";
            }
	     	 System.out.println("ユーザー名:" + form.getUserName());
	         System.out.println("メールアドレス:" + form.getEmail());
	         System.out.println("パスワード:" + form.getPasswords());
		
		     User user = new User();
	         user.setUserName(form.getUserName());
	         user.setEmail(form.getEmail());
	         user.setPasswords(form.getPasswords());
	         // 仮データ
	           user.setPhone("000-0000-0000");
	           user.setUserAddress("東京都テスト区");
	           user.setUserNameKana("テスト");
	    
	        userRepository.save(user); // ←ここでDB登録
	       
		     return "login/loginForm";
	}
	
	@GetMapping("/mypage")
	      public String mypage() {
	    	return "mypage";
	}
}
