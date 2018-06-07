package by.runets.travelagency.repository;

import by.runets.travelagency.entity.Country;
import by.runets.travelagency.entity.Hotel;
import by.runets.travelagency.repository.impl.HotelRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HotelRepositoryTest {
	private final IRepository<Hotel, Integer> repository = new HotelRepository();
	
	@Test
	public void readAllTest () {
		List<Optional<Hotel>> actual = new ArrayList<>(Arrays.asList(
				Optional.of(new Hotel<Integer>(1, "Marriot", "123 23 23", 5, new Country<Integer>())),
				Optional.of(new Hotel<Integer>(2, "DoubleTree by Hilton", "232 12 12", 5, new Country<Integer>())),
				Optional.of(new Hotel<Integer>(3, "Prezident-Otel", "111 11 11", 4, new Country<Integer>())),
				Optional.of(new Hotel<Integer>(4, "Aqua-Minsk", "123 11 11", 2, new Country<Integer>())),
				Optional.of(new Hotel<Integer>(5, "Trump International Hotel Washington DC", "101 10 01", 5, new Country<Integer>()))));
		
		List<Optional<Hotel>> expected = repository.readAll();
		Assert.assertEquals(actual, expected);
	}
	
	@Test
	public void readByIdTest () {
		Optional<Hotel> actual = Optional.of(new Hotel<Integer>(1, "Marriot", "123 23 23", 5, new Country<Integer>()));
		Optional<Hotel> expected = repository.read(1);
		
		Assert.assertEquals(actual, expected);
	}
	
	@Test
	public void testDelete () {
		List<Optional<Hotel>> actual = new ArrayList<>(Arrays.asList(
				Optional.of(new Hotel<Integer>(1, "Marriot", "123 23 23", 5, new Country<Integer>())),
				Optional.of(new Hotel<Integer>(2, "DoubleTree by Hilton", "232 12 12", 5, new Country<Integer>())),
				Optional.of(new Hotel<Integer>(3, "Prezident-Otel", "111 11 11", 4, new Country<Integer>())),
				Optional.of(new Hotel<Integer>(5, "Trump International Hotel Washington DC", "101 10 01", 5, new Country<Integer>()))));
		
		Hotel entityToDelete = new Hotel<Integer>(4, "Aqua-Minsk", "123 11 11", 2, new Country<Integer>());
		repository.delete(entityToDelete);
		
		List<Optional<Hotel>> expected = repository.readAll();
		Assert.assertEquals(actual, expected);
	}
	
	@Test
	public void testUpdate() {
		List<Optional<Hotel>> actual = new ArrayList<>(Arrays.asList(
				Optional.of(new Hotel<Integer>(1, "Marriot1", "123 24 23", 5, new Country<Integer>())),
				Optional.of(new Hotel<Integer>(2, "DoubleTree by Hilton", "232 12 12", 5, new Country<Integer>())),
				Optional.of(new Hotel<Integer>(3, "Prezident-Otel", "111 11 11", 4, new Country<Integer>())),
				Optional.of(new Hotel<Integer>(5, "Trump International Hotel Washington DC", "101 10 01", 5, new Country<Integer>()))));
		Hotel entityToUpdate = new Hotel<Integer>(1, "Marriot1", "123 24 23", 5, new Country<Integer>());
		repository.update(entityToUpdate);
		
		List<Optional<Hotel>> expected = repository.readAll();
		Assert.assertEquals(actual, expected);
	}
}