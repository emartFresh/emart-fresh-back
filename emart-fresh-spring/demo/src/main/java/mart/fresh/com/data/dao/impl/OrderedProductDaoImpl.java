package mart.fresh.com.data.dao.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import mart.fresh.com.data.dao.OrderedProductDao;
import mart.fresh.com.data.entity.OrderedProduct;
import mart.fresh.com.data.entity.OrderedProductProduct;
import mart.fresh.com.data.entity.Store;
import mart.fresh.com.data.repository.OrderedProductRepository;
import mart.fresh.com.data.repository.StoreRepository;
import reactor.core.publisher.Flux;



@Component
public class OrderedProductDaoImpl implements OrderedProductDao {

private final OrderedProductRepository orderedProductRepository;
private final StoreRepository storeRepository;


	@Autowired
	public OrderedProductDaoImpl(OrderedProductRepository orderedProductRepository,
									StoreRepository storeRepository) {
		this.orderedProductRepository = orderedProductRepository;
		this.storeRepository = storeRepository;
	}

	@Override
	public Page<OrderedProductProduct> getOrderedProductByMemberId(String memberId, int page, int size) {
		System.out.println("OrderedProductDaoImpl getOrderedProductByorderedProductId");
		
		Pageable pageable = PageRequest.of(page, size);
		boolean orderedDel = false;
		
		System.out.println("OrderedProductDaoImpl OrderedProductDaoImpl " + "memberId : " + memberId + " page : " + page + " size : " + size + " pageable : " + pageable);
		
		Page<OrderedProductProduct> results = orderedProductRepository.getOrderedProductProductByMemberId(memberId, orderedDel, pageable);

		System.out.println("OrderedProductDaoImpl OrderedProductDaoImpl : " + results.toString());
		
	    return results;
	}

	
	@Override
	public Flux<OrderedProduct> getOrderedListByStoreId(String memberId) {
	    boolean isPickup = false;
	    
//		System.out.println("OrderedProductDaoImpl storeInfo memberId : " + memberId);
	    
		Store store = storeRepository.findByMemberMemberId(memberId);
		
//		System.out.println("OrderedProductDaoImpl storeInfo : ");
		
	    List<OrderedProduct> orderedProduct = orderedProductRepository.findByIsPickupAndStoreStoreId(isPickup, store.getStoreId());
	    
	    return Flux.fromIterable(orderedProduct);
	}

	@Override
	public OrderedProduct findByIsPickupAndMemberMemberId(String memberId) {
		boolean isPickup = false;
		
		OrderedProduct orderedProduct = orderedProductRepository.findByIsPickupAndMemberMemberId(isPickup, memberId);
		return orderedProduct;
	}

	@Override
	public List<OrderedProduct> findByMemberMemberId(String memberId) {

		System.out.println("getOrderedProductProductEntitiesByMemberId memberId : " + memberId);
		
		Store store = storeRepository.findByMemberMemberId(memberId);
		boolean isPickup = false;
		 
		System.out.println("getOrderedProductProductEntitiesByMemberId store : " + store.toString());
		
		List<OrderedProduct> orderedProduct  = orderedProductRepository.findByIsPickupAndStoreStoreId(isPickup, store.getStoreId());
		System.out.println("getOrderedProductProductEntitiesByMemberId : " + orderedProduct.toString() );
		return orderedProduct;
	}


	@Override
	public void saveOrderedProduct(OrderedProduct orderedProduct) {
		orderedProductRepository.save(orderedProduct);
	}

	@Override
	public OrderedProduct findByOrderedProductId(int orderedProductId) {
		return orderedProductRepository.findByOrderedProductId(orderedProductId);
	}

//	@Override
//	public List<OrderedProduct> findByMemberMemberId(String memberId) {
//		return orderedProductRepository.findByMemberMemberId(memberId);
//	}

}
