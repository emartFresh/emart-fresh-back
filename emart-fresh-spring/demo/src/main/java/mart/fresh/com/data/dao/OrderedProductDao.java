package mart.fresh.com.data.dao;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.domain.Page;
import mart.fresh.com.data.entity.OrderedProduct;
import mart.fresh.com.data.entity.OrderedProductProduct;
import reactor.core.publisher.Flux;


public interface OrderedProductDao {
	Page<OrderedProductProduct> getOrderedProductByMemberId(String memberId, int page, int size);
	List<OrderedProduct> findByMemberMemberId(String memberId);
	void saveOrderedProduct(OrderedProduct orderedProduct);
	Flux<OrderedProduct> getOrderedListByStoreId(String memberId);
	void completePickup(int orderedProductId);
	OrderedProduct findByOrderedProductId(int orderedProductId);
	
//	List<OrderedProduct> findByStoreStoreIdAndOrderedDateBetween(int storeId, Timestamp startDate, Timestamp endDate);
	List<OrderedProduct> findByStoreStoreIdAndOrderedDateBetween(int storeId, Timestamp searchDate, String period);
	List<OrderedProductProduct> findByOrderedProductStoreStoreIdAndOrderedProductOrderedDateBetweenOrderByOrderedDateAsc(int storeId, Timestamp searchDate, String period);
}