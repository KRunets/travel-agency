package mock.by.runets.travelagency.service;

import by.runets.travelagency.entity.Hotel;
import by.runets.travelagency.hibernate.impl.HotelRepository;
import by.runets.travelagency.service.impl.HotelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static integration.by.runets.travelagency.config.DevelopmentDatabaseBeanConfig.DEFAULT_PAGINATION_SIZE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class HotelServiceTest {
	@Mock
	private HotelRepository repository;
	@InjectMocks
	private HotelService service;
	
	
	@Test
	public void testReadAll () {
		when(repository.readAll(Hotel.class, DEFAULT_PAGINATION_SIZE)).thenReturn(new ArrayList<>());
		
		assertThat(service.readAll(DEFAULT_PAGINATION_SIZE), is(notNullValue()));
	}
	
	@Test
	public void testUpdate () {
		Hotel hotel = new Hotel(1, "Marriot1", "123 24 23", 5, 0.0, 0.0);
		
		service.update(hotel);
		
		verify(repository, times(1)).update(hotel);
	}
	
	@Test
	public void testDelete () {
		Hotel hotel = new Hotel(1, "Marriot1", "123 24 23", 5, 0.0, 0.0);
		
		service.delete(hotel);
		
		verify(repository, times(1)).delete(hotel);
	}
	
	@Test
	public void testCreate () {
		Hotel hotel = new Hotel(7, "Marriot2", "123 24 23", 5, 0.0, 0.0);
		
		service.create(hotel);
		
		verify(repository, times(1)).create(hotel);
	}
	
}
