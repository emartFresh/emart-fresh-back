package mart.fresh.com.data.dao.impl;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import mart.fresh.com.data.dao.MypageDao;
import mart.fresh.com.data.dto.MypageDto;
import mart.fresh.com.data.entity.ApplyManager;
import mart.fresh.com.data.entity.Member;
import mart.fresh.com.data.repository.MypageRepository;

@Component
public class MypageDaoImpl implements MypageDao {

private final MypageRepository mypageRepository;
	
	@Autowired
	public MypageDaoImpl(MypageRepository mypageRepository) {
		this.mypageRepository = mypageRepository;
	}
	
	@Override
	public Member getMemberAndIsAppliedByMemberId(String memberId) {
		System.out.println("MypageDaoImpl getMemberAndIsAppliedByMemberId");
		
		Member memberInfo = mypageRepository.findMemberByMemberId(memberId);
		
		System.out.println("MypageDaoImpl getMemberAndIsAppliedByMemberId : " + memberInfo.toString());
		
		return memberInfo;
	}

	@Override
	public int changePassword(String memberId, String memberPw, String newPw) {
		System.out.println("MypageDaoImpl changePassword");
		
			int count = mypageRepository.changePassword(memberId, memberPw, newPw);
		return count;
	}

	@Override
	public int checkEmail(String newEmail) {
		return mypageRepository.checkEmail(newEmail);
	}
	


	@Override
	public int saveVerificationCode(String memberId, String verificationCode, LocalDateTime expiryTime) {
	    // Find the member by memberId
	    Member member = mypageRepository.findMemberByMemberId(memberId);

	    if (member != null) {
	        member.setVerifyCode(verificationCode);
	        member.setVerifyCodeExpiry(expiryTime);
	        
	        mypageRepository.save(member);
	        
	        return 1; 
	    } else {
	        return 0; 
	    }
	}


	
	@Override
	@Transactional
	public int changeEmail(String memberId, String newEmail, String verificationCode) {
	    // memberId로 데이터베이스에서 회원 정보 조회
	    Member member = mypageRepository.findByMemberIdAndVerifyCode(memberId, verificationCode);

	    if (member != null) {
	        // 현재 시간 가져오기
	    	 LocalDateTime currentTime = LocalDateTime.now();

	        // 데이터베이스에 저장된 유효시간과 현재 시간 비교
	        if (member.getVerifyCodeExpiry() != null && member.getVerifyCodeExpiry().isAfter(currentTime)) {

	            member.setMemberEmail(newEmail);

	            mypageRepository.save(member);

	            return 1;
	        } else {
	            // 유효시간 초과한 경우
	            return -1;
	        }
	    } else {
	        return 0; 
	    }
	}

}