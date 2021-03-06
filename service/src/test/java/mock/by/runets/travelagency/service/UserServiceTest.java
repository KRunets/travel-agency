package mock.by.runets.travelagency.service;

import by.runets.travelagency.entity.User;
import by.runets.travelagency.hibernate.impl.UserRepository;
import by.runets.travelagency.service.impl.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTest {
	@Mock
	private UserRepository repository;
	@InjectMocks
	private UserService service;
	
	@Test
	public void testReadAll () {
		when(repository.readAll(User.class)).thenReturn(new ArrayList<Optional<User>>());
		
		assertThat(service.readAll(), is(notNullValue()));
	}
	
	@Test
	public void testCreate () {
		User user = new User(5, "admin", "admin", null, null);
		
		service.create(user);
		verify(repository, times(1)).create(user);
	}
	
	@Test
	public void testUpdate () {
		User user = new User(2, "admin1", "admin1", null, null);
		
		service.update(user);
		verify(repository, times(1)).update(user);
	}
	
	@Test
	public void testDelete () {
		User user = new User(2, "admin", "admin", null, null);
		
		service.delete(user);
		verify(repository, times(1)).delete(user);
	}
}
