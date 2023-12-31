package mart.fresh.com.data.dao;

import java.util.List;

import mart.fresh.com.data.entity.Cart;
import mart.fresh.com.data.entity.CartProduct;

public interface CartProductDao {
    boolean removeProductFromCart(String memberId, int cartProductId);

	boolean updateCartProductQuantity(String memberId, int cartProductId, int cartProductQuantity);

	CartProduct findByCart(Cart cart);

	void removeAllProductsFromCart(int cartId);

	List<CartProduct> findListByCart(Cart cart);

}
