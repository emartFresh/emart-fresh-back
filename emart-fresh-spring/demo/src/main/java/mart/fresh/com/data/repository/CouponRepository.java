package mart.fresh.com.data.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.transaction.Transactional;
import mart.fresh.com.data.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	@Query("SELECT  c " + "FROM Coupon c " + " WHERE c.member.memberId = :memberId " + " AND c.couponDel = false "
			+ " ORDER BY c.couponExpirationDate ASC")
	Page<Coupon> myCouponList(@Param("memberId") String memberId, Pageable pageable);

	@Transactional
	List<Coupon> findByCouponExpirationDateBefore(LocalDateTime currentTime);

	Coupon findByCouponId(int couponId);

	@Query("SELECT  c " + "FROM Coupon c " + " WHERE c.couponDel = false " + " AND c.member.memberAuth = :memberAuth "
			+ " ORDER BY c.couponExpirationDate ASC")
	Page<Coupon> exceptCouponList(Pageable pageable, int memberAuth);

	List<Coupon> findByMemberMemberId(String memberId);

	int countByCouponTypeAndCouponTitleAndCouponExpirationDateAndMemberMemberId(int couponType, String couponTitle, Timestamp couponExpirationDate, String memberId);

	void deleteByMemberMemberIdAndCouponId(@Param("memberId") String memberId, @Param("couponId") int couponId);
}