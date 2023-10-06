package mart.fresh.com.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mart.fresh.com.data.dto.ReviewDto;
import mart.fresh.com.data.entity.Member;
import mart.fresh.com.data.entity.Review;
import mart.fresh.com.service.MemberService;
import mart.fresh.com.service.ProductService;
import mart.fresh.com.data.dto.ReviewDto;
import mart.fresh.com.service.ReviewService;

@RequestMapping("/review")
@RestController
public class ReviewController {

	private final ReviewService reviewService;
	private final MemberService memberService;

	@Autowired
	public ReviewController(ReviewService reviewService, MemberService memberService) {
		this.reviewService = reviewService;
		this.memberService = memberService;
	}
	
	@GetMapping("/review-list")
	public Page<ReviewDto> myReviewList(Authentication authentication,
										@RequestParam int page, @RequestParam int size) {
		
		Page<ReviewDto> reviewList = reviewService.myReviewList(authentication.getName(), page-1, size);
		return reviewList;
	}
	

	@PostMapping("/review-delete")
	public String myReviewDelete(@RequestParam int reviewId) {
		
		boolean isS = reviewService.myReviewDelete(reviewId);
		
		if(isS) { return "삭제"; }
		else { return "삭제실패"; }
	}
	

	@GetMapping("/product-review")
	//select : 정렬순서 0 최신순 1 평점 높은순 2 평점 낮은 순 
	public List<ReviewDto> getProductReviewByProductTitle(String productTitle, @RequestParam(defaultValue = "0") int select){
		
		return reviewService.getProductReviewByProductTitle(productTitle, select);
	}
	
	@PostMapping("/add")
    public ResponseEntity<String> addReview(Authentication authentication, @RequestBody Review review) {
		System.out.println("reviewController 리뷰 저장 ~ " + new Date());
        try {
        	String memberId = authentication.getName();
            Member member = memberService.findByMemberId(memberId);

            if (member == null) {
                System.out.println("리뷰 저장 실패: 해당 멤버를 찾을 수 없음");
                return ResponseEntity.badRequest().body("리뷰 저장 실패: 해당 멤버를 찾을 수 없음");
            }
            
            if (review.getProductTitle() == null || review.getProductTitle().isEmpty()
                    || review.getReviewContent() == null || review.getReviewContent().isEmpty()
                    || review.getReviewDate() == null) {
                System.out.println("리뷰 저장 실패: 필수 정보가 누락됨");
                return ResponseEntity.badRequest().body("리뷰 저장 실패: 필수 정보가 누락됨");
            }
            
            review.setMember(member);
            review.setProductTitle(review.getProductTitle());
            review.setReviewContent(review.getReviewContent());
            
            int reviewScore = review.getReviewScore();
            if (reviewScore < 1 || reviewScore > 5) {
                System.out.println("리뷰 저장 실패: 리뷰 점수가 유효 범위를 벗어남");
                return ResponseEntity.badRequest().body("리뷰 저장 실패: 리뷰 점수가 유효 범위를 벗어남");
            }
            review.setReviewScore(review.getReviewScore());
            review.setReviewDate(new Timestamp(System.currentTimeMillis()));

            reviewService.save(review);
            System.out.println("리뷰 저장 성공");
            return ResponseEntity.ok("리뷰 저장 성공");
        } catch (Exception e) {
        	System.out.println("리뷰 저장 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 저장 실패, 에러 메시지 : " + e.getMessage());
        }
    }

}