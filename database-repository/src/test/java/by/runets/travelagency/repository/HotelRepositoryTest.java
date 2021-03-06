package by.runets.travelagency.repository;

import by.runets.travelagency.entity.Country;
import by.runets.travelagency.entity.Hotel;
import by.runets.travelagency.repository.impl.HotelRepository;
import by.runets.travelagency.util.config.DevelopmentDatabaseBeanConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "development")
@ContextConfiguration(classes = DevelopmentDatabaseBeanConfig.class)
@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/schema.sql", "classpath:db/init-data.sql"}),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:db/drop.sql")
})
public class HotelRepositoryTest {
  @Autowired
  @Qualifier("HotelRepository")
  private HotelRepository repository;
  

  @Test
  public void testCreate() {
    Hotel expected =
        new Hotel(10, "testName", "+375 29 123 123 123", 5, new Country(1, null, null, null));
    repository.create(expected);
    Hotel actual = repository.read(10).get();

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testReadById() {
    Hotel expected =
        new Hotel(1, "Marriot", "123 23 23", 5, new Country(1, null, null, null));
    Hotel actual = repository.read(1).get();
    Assert.assertEquals(actual, expected);
  }

  @Test
  public void testReadAll() {
    List<Optional<Hotel>> expected =
        new ArrayList(
            Arrays.asList(
                Optional.of(
                    new Hotel(1, "Marriot", "123 23 23", 5, new Country())),
                Optional.of(
                    new Hotel(
                        2, "DoubleTree by Hilton", "232 12 12", 5, new Country())),
                Optional.of(
                    new Hotel(
                        3, "Prezident-Otel", "111 11 11", 4, new Country())),
                Optional.of(
                    new Hotel(4, "Aqua-Minsk", "123 11 11", 2, new Country())),
                Optional.of(
                    new Hotel(
                        5,
                        "Trump International Hotel Washington DC",
                        "101 10 01",
                        5,
                        new Country()))));
    List<Optional<Hotel>> actual = repository.readAll();

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testUpdate() {
    Hotel expected = repository.read(1).get();

    expected.setName("newName");
    expected.setStars(10);
    expected.setPhone("111 111 11");

    repository.update(expected);

    Hotel actual = repository.read(1).get();

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testDelete() {
    Optional<Hotel> expected = repository.read(1);
    Assert.assertNotNull(expected);
    repository.delete(expected.get());

    Optional<Hotel> actual = repository.read((int) expected.get().getId());
    Assert.assertEquals(Optional.empty(), actual);
  }
}
