package by.runets.travelagency.service;

import by.runets.travelagency.dto.TourDTO;
import by.runets.travelagency.entity.Tour;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public interface ITourService<T, K> extends IService<T, K> {
	List<Tour> findTourByCountryAndDateAndDuration(final String countryName, final LocalDate startTourDate, final Duration tourDuration);
}