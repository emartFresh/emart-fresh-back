package mart.fresh.com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mart.fresh.com.data.dao.CartDao;
import mart.fresh.com.data.dao.CartProductDao;
import mart.fresh.com.data.dto.CartInfoDto;
import mart.fresh.com.service.CartService;

@Service
public class CartServiceImpl implements CartService{
	private final CartDao cartDao;
	
	@Autowired
	public CartServiceImpl(CartDao cartDao) {
		this.cartDao = cartDao;
	}

	@Override
	public List<CartInfoDto> getCartInfo(String memberId) {
        return cartDao.getCartInfo(memberId);
	}

}
