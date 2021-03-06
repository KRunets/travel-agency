package by.runets.travelagency.service.impl;

import by.runets.travelagency.entity.Hotel;
import by.runets.travelagency.hibernate.IDatabaseRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelService extends AbstractService<Hotel, Long> {
	
	public HotelService (Class<Hotel> classType, IDatabaseRepository<Hotel, Long> abstractRepository) {
		super(classType, abstractRepository);
	}
}
