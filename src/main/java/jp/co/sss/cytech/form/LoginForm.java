package jp.co.sss.cytech.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LoginForm {
    private Integer userId;
    
    @NotBlank(message = "パスワードを入力してください")
    private String passwords;
    
    @NotBlank(message = "メールアドレスを入力してください")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
	     message = "メールアドレスの形式が正しくありません")
    private String email;
    
    @NotBlank(message = "ユーザー名を入力してください")
    private String userName;
    private String phone;
    private String userAddress;
    private String userNameKana;
    
    @NotBlank(message = "パスワードを入力してください")
    private String confirmPasswords;
    
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getPasswords() {
		return passwords;
	}
	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getUserNameKana() {
		return userNameKana;
	}
	public void setUserNameKana(String userNameKana) {
		this.userNameKana = userNameKana;
	}
	public String getConfirmPasswords() {
		return confirmPasswords;
	}
	public void setConfirmPasswords(String confirmPasswords) {
		this.confirmPasswords = confirmPasswords;
	}
    
}	