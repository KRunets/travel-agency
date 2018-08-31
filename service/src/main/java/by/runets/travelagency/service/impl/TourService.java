package by.runets.travelagency.service.impl;

import by.runets.travelagency.entity.Tour;
import by.runets.travelagency.hibernate.IDatabaseRepository;
import by.runets.travelagency.hibernate.ITourRepository;
import by.runets.travelagency.service.ITourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourService extends AbstractService<Tour, Long> implements ITourService<Tour, Long> {
	@Autowired
	private ITourRepository<Tour, Long> tourRepository;
	
	public TourService (Class<Tour> classType, IDatabaseRepository<Tour, Long> abstractRepository) {
		super(classType, abstractRepository);
		this.tourRepository = (ITourRepository<Tour, Long>) abstractRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Tour> findTourByCountryAndDateAndDuration (String countryName, LocalDate startTourDate, Duration tourDuration) {
		List<Optional<Tour>> tours = tourRepository
				.findTourByCountryAndDateAndDuration(countryName, startTourDate, tourDuration);
		return tours.stream()
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}
}
