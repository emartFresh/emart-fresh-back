package mart.fresh.com.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mart.fresh.com.data.dto.CartInfoDto;
import mart.fresh.com.service.CartProductService;
import mart.fresh.com.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	private final CartService cartService;
    private final CartProductService cartProductService;
	
	@Autowired
	public CartController(CartService cartService, CartProductService cartProductService) {
		this.cartService = cartService;
        this.cartProductService = cartProductService;
	}
	
	@GetMapping("/getCartInfo")
    public ResponseEntity<List<CartInfoDto>> getCartInfoByMember(Authentication authentication) {
        String memberId = authentication.getName();
        System.out.println("CartController "+ memberId+ "의 장바구니 확인 " + new Date());
		List<CartInfoDto> cartInfoList = cartService.getCartInfo(memberId);
        if (cartInfoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(cartInfoList);
        }
    }
	
	@DeleteMapping("/removeProduct")
    public ResponseEntity<String> removeProductFromCart(Authentication authentication, @RequestParam int cartProductId) {
		String memberId = authentication.getName();
        System.out.println("CartController "+ memberId+ "의 장바구니 삭제 " + new Date());

        cartProductService.removeProductFromCart(memberId, cartProductId);
        return ResponseEntity.status(HttpStatus.OK).build();

    }
	
	

}