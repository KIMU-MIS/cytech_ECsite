package jp.co.sss.cytech.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ReviewForm {

	@NotBlank(message = "任意のユーザー名を入力してください")
	private String userName;
	
    private Integer rating;
    
    @NotBlank(message = "メールアドレスを入力してください")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
    		 message = "メールアドレスの形式が正しくありません")
    private String email;
    
    @NotBlank(message = "コメントを入力してください")
    private String comment;
    
    private String imagePath;
    
    
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}
