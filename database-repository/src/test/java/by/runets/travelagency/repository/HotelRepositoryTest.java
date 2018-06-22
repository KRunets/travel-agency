package by.runets.travelagency.repository;

import by.runets.travelagency.config.DevelopmentDatabaseBeanConfig;
import by.runets.travelagency.entity.Country;
import by.runets.travelagency.entity.Hotel;
import by.runets.travelagency.repository.impl.CountryRepository;
import by.runets.travelagency.repository.impl.HotelRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HotelRepositoryTest {
  @Autowired
  private AnnotationConfigApplicationContext context;
  @Autowired
  private IDatabaseRepository<Hotel, Integer> repository;
  
  @Before
  public void setup() {
    context = new AnnotationConfigApplicationContext();
    context.getEnvironment().setActiveProfiles("development");
    context.register(DevelopmentDatabaseBeanConfig.class);
    context.refresh();
    repository = context.getBean(HotelRepository.class);
  }

  @Test
  public void testCreate() {
    Hotel<Integer> expected =
        new Hotel<>(10, "testName", "+375 29 123 123 123", 5, new Country<>(1, null, null, null));
    repository.create(expected);
    Hotel<Integer> actual = repository.read(10).get();

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testReadById() {
    Hotel<Integer> expected =
        new Hotel<>(1, "Marriot", "123 23 23", 5, new Country<>(1, null, null, null));
    Hotel<Integer> actual = repository.read(1).get();
    Assert.assertEquals(actual, expected);
  }

  @Test
  public void testReadAll() {
    List<Optional<Hotel>> expected =
        new ArrayList<>(
            Arrays.asList(
                Optional.of(
                    new Hotel<Integer>(1, "Marriot", "123 23 23", 5, new Country<Integer>())),
                Optional.of(
                    new Hotel<Integer>(
                        2, "DoubleTree by Hilton", "232 12 12", 5, new Country<Integer>())),
                Optional.of(
                    new Hotel<Integer>(
                        3, "Prezident-Otel", "111 11 11", 4, new Country<Integer>())),
                Optional.of(
                    new Hotel<Integer>(4, "Aqua-Minsk", "123 11 11", 2, new Country<Integer>())),
                Optional.of(
                    new Hotel<Integer>(
                        5,
                        "Trump International Hotel Washington DC",
                        "101 10 01",
                        5,
                        new Country<Integer>()))));
    List<Optional<Hotel>> actual = repository.readAll();

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testUpdate() {
    Hotel<Integer> expected = repository.read(1).get();

    expected.setName("newName");
    expected.setStars(10);
    expected.setPhone("111 111 11");

    repository.update(expected);

    Hotel<Integer> actual = repository.read(1).get();

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testDelete() {
    Optional<Hotel> expected = repository.read(1);
    Assert.assertNotNull(expected);
    repository.delete(expected.get());

    Optional<Hotel> actual = repository.read((Integer) expected.get().getId());
    Assert.assertEquals(Optional.empty(), actual);
  }

  @After
  public void tearDown() {
    context.close();
  }
}
