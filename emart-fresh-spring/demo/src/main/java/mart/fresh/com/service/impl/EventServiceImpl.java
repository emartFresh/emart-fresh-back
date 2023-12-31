package mart.fresh.com.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import mart.fresh.com.data.dao.EventDao;
import mart.fresh.com.data.dto.EventDto;
import mart.fresh.com.data.entity.Event;
import mart.fresh.com.data.repository.EventRepository;
import mart.fresh.com.service.EventService;

@Service
public class EventServiceImpl implements EventService {
	private final EventDao eventDao;
	private final EventRepository eventRepository;
	private final Cloudinary cloudinary;

	@Autowired
	public EventServiceImpl(EventDao eventDao, EventRepository eventRepository, Cloudinary cloudinary) {
		this.eventDao = eventDao;
		this.eventRepository = eventRepository;
		this.cloudinary = cloudinary;
	}

	@Override
	public boolean eventUpdate(EventDto dto) {
		return eventDao.eventUpdate(dto);
	}

	private EventDto convertEventToDto(Event eventEntity) {
		EventDto dto = new EventDto();
		dto.setEventId(eventEntity.getEventId());
		dto.setEventTitle(eventEntity.getEventTitle());
		dto.setEventBannerImage(eventEntity.getEventBannerImage());
		dto.setEventDetailImage(eventEntity.getEventDetailImage());
		dto.setEventStartDate(eventEntity.getEventStartDate());
		dto.setEventEndDate(eventEntity.getEventEndDate());

		int eventListCount = eventRepository.eventListCount();
		dto.setEventListCount(eventListCount);

		return dto;
	}

	private EventDto convertEventListToDto(Event eventEntity) {
		EventDto dto = new EventDto();
		dto.setEventId(eventEntity.getEventId());
		dto.setEventTitle(eventEntity.getEventTitle());
		dto.setEventBannerImage(eventEntity.getEventBannerImage());
		dto.setEventDetailImage(eventEntity.getEventDetailImage());
		dto.setEventStartDate(eventEntity.getEventStartDate());
		dto.setEventEndDate(eventEntity.getEventEndDate());
		return dto;
	}
	@Override
	public Page<EventDto> eventList(int page, int size) {

		Page<Event> eventEntityList = eventDao.eventList(page, size);

		Page<EventDto> eventDtoPage = eventEntityList.map(this::convertEventToDto);

		return eventDtoPage;
	}

	@Override
	public EventDto eventDetail(int eventId) {
		Event event = eventDao.eventDetail(eventId);

		return convertEventToDto(event);
	}

	 public String uploadImage(MultipartFile imageFile) throws Exception {
	        try {
	            // 이미지 업로드 요청에 format 및 quality 옵션 추가
	            Map<?, ?> uploadResult = cloudinary.uploader().upload(imageFile.getBytes(),
	                    ObjectUtils.asMap("format", "webp", "quality", "auto")); // WebP 형식으로 저장

	            return uploadResult.get("secure_url").toString();

	        } catch (Exception e) {
	            e.printStackTrace();
	            throw e;
	        }

	}

	@Override
	public List<EventDto> nowEventList() {
		List<Event> eventEntityList = eventDao.nowEventList();

	    List<EventDto> eventDtoList = new ArrayList<>();
	    for (Event event : eventEntityList) {
	        eventDtoList.add(convertEventListToDto(event));
	    }

		return eventDtoList;
	}

}