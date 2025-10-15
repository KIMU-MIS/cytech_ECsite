package jp.co.sss.cytech.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class OrderForm {

	@NotBlank(message = "送り先氏名を入力してください")
	private String name;
	
    @NotBlank(message = "住所を入力してください")
    private String address;
	 
    @NotBlank(message = "電話番号を入力してください")
    private String phone;
    
    private String paymentMethod;
    
    @NotBlank(message = "カード番号を入力してください")
    @Pattern(regexp="^[0-9]{15,16}$", 
            message="カード番号は15～16桁の数字で入力してください")
    private String cardNumber;
    
    @NotBlank(message = "有効期限を入力してください")
    @Pattern(regexp="^(0[1-9]|1[0-2])/[0-9]{2}$", 
            message="有効期限はMM/YY形式で入力してください")
    private String cardExpiryDate;
    private String apartment;
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardExpiryDate() {
		return cardExpiryDate;
	}
	public void setCardExpiryDate(String cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}
	public String getApartment() {
		return apartment;
	}
	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

  
}


