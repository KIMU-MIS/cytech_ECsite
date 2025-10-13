package jp.co.sss.cytech.dto;

import jp.co.sss.cytech.entity.Product;

public class CartItemDto {

	private Product product;
    private Integer quantity;

    

	public CartItemDto(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public Integer getQuantity() { return quantity; }
    public Integer getTotalPrice() { return product.getPrice() * quantity; }
    
}
