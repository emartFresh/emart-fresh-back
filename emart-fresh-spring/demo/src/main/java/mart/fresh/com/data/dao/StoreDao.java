package mart.fresh.com.data.dao;

import java.util.List;

import mart.fresh.com.data.dto.GetStoreInDisDto;
import mart.fresh.com.data.dto.GetStoreInDisMapDto;
import mart.fresh.com.data.dto.StoreDto;
import mart.fresh.com.data.dto.StoreDtoWithId;
import mart.fresh.com.data.entity.Store;

public interface StoreDao {
	List<StoreDto> getStoreWitnNByProductName(GetStoreInDisDto dto);
	List<StoreDto> getStoreWitnNByProductNameMap(GetStoreInDisMapDto dto);
	Store getStoreInfo(int storeId);
	int addStore(StoreDtoWithId dto);
	public Store findByStoreId(int storeId);

	Store findByMemberMemberId(String memberId);

	Store findStoreByMemberId(String memberId);
	List<Store> getStoreWitnNByProductName(double userLatitude, double userLongitude, int n, String partOfStoreName);
	int getStoreProductStock(int storeId, String productTitle);

}
