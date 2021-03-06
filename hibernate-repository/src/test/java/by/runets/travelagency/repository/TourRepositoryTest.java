package by.runets.travelagency.repository;

import by.runets.travelagency.entity.Tour;
import by.runets.travelagency.entity.TourType;
import by.runets.travelagency.hibernate.IDatabaseRepository;
import by.runets.travelagency.util.config.DevelopmentDatabaseBeanConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "development")
@ContextConfiguration(classes = DevelopmentDatabaseBeanConfig.class)
@SqlGroup(
		@Sql(
				executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
				scripts = {"classpath:db/init-data.sql"}
		))
public class TourRepositoryTest {
	@Autowired
	private IDatabaseRepository<Tour, Long> tourRepository;
	
	@Test
	public void testCreate () {
		final Tour expected =
				new Tour(
						12,
						"Photo1",
						LocalDate.parse("2018-07-17"),
						Duration.ofDays(10),
						"description1",
						new BigDecimal(100.00),
						TourType.ADVENTURE,
						null,
						null);
		final long id = tourRepository.create(expected);
		final Optional<Tour> actual = tourRepository.read(Tour.class, id);
		
		Assert.assertEquals(expected, actual.get());
	}
	
	@Test
	public void testReadById () {
		final long id = 1;
		final Optional<Tour> expected =
				Optional.of(
						new Tour(
								id,
								"photo/img1.png",
								LocalDate.parse("2018-07-17"),
								Duration.ofDays(10),
								"description1",
								new BigDecimal(100),
								TourType.ADVENTURE,
								null,
								null));
		final Optional<Tour> actual = tourRepository.read(Tour.class, id);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testReadAll () {
		List<Optional<Tour>> expected =
				new ArrayList(
						Arrays.asList(
								Optional.of(
										new Tour(
												1,
												"photo/img1.png",
												LocalDate.parse("2018-07-17"),
												Duration.ofDays(10),
												"description1",
												new BigDecimal(100),
												TourType.ADVENTURE,
												null,
												null)),
								Optional.of(
										new Tour(
												2,
												"photo/img2.png",
												LocalDate.parse("2018-07-20"),
												Duration.ofDays(20),
												"description2",
												new BigDecimal(200),
												TourType.ATOMIC,
												null,
												null)),
								Optional.of(
										new Tour(
												3,
												"photo/img3.png",
												LocalDate.parse("2018-07-25"),
												Duration.ofDays(30),
												"description3",
												new BigDecimal(300),
												TourType.BICYCLE,
												null,
												null)),
								Optional.of(
										new Tour(
												4,
												"photo/img4.png",
												LocalDate.parse("2018-07-30"),
												Duration.ofDays(40),
												"description4",
												new BigDecimal(400),
												TourType.CULTURAL,
												null,
												null)),
								Optional.of(
										new Tour(
												5,
												"photo/img5.png",
												LocalDate.parse("2018-08-05"),
												Duration.ofDays(50),
												"description5",
												new BigDecimal(500),
												TourType.ECO,
												null,
												null))));
		
		List<Optional<Tour>> actual = tourRepository.readAll(Tour.class);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testDelete () {
		final long id = 1;
		final Optional<Tour> entityToDelete = tourRepository.read(Tour.class, id);
		tourRepository.delete(entityToDelete.get());
		
		final Optional<Tour> actual = tourRepository.read(Tour.class, id);
		Assert.assertEquals(Optional.empty(), actual);
	}
	
	@Test
	public void testUpdate () {
		final long id = 1;
		final Tour expected = tourRepository.read(Tour.class, id).get();
		
		expected.setPhoto("newPhoto");
		expected.setDate(LocalDate.now());
		
		tourRepository.update(expected);
		
		Tour actual = tourRepository.read(Tour.class, id).get();
		
		Assert.assertEquals(expected, actual);
	}
}
