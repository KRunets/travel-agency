package by.runets.travelagency.repository.impl;

import by.runets.travelagency.constant.UserQuery;
import by.runets.travelagency.entity.Review;
import by.runets.travelagency.entity.Tour;
import by.runets.travelagency.entity.TourType;
import by.runets.travelagency.entity.User;
import by.runets.travelagency.joiner.Joiner;
import by.runets.travelagency.repository.IRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@AllArgsConstructor
public class UserRepository implements IRepository<User, Integer> {
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final Joiner<User> joiner;
	
	@Override
	public void create (User entity) {
		namedParameterJdbcTemplate.update(UserQuery.INSERT_INTO_USER, new BeanPropertySqlParameterSource(entity));
	}
	
	@Override
	public List<Optional<User>> readAll () {
		try {
			List<User> users = namedParameterJdbcTemplate.query(UserQuery.READ_ALL_USER, new UserRowMapper());
			return joiner.join(users).stream()
					.map(Optional::ofNullable)
					.collect(Collectors.toList());
		} catch (EmptyResultDataAccessException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public Optional<User> read (Integer id) {
		try {
			List<User> users = namedParameterJdbcTemplate.query(UserQuery.READ_USER_BY_ID, new MapSqlParameterSource("id", id), new UserRowMapper());
			List<User> result = joiner.join(users);
			return !result.isEmpty()
					? Optional.ofNullable(result.get(0))
					: Optional.empty();
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
	
	@Override
	public void update (User entity) {
		namedParameterJdbcTemplate.update(UserQuery.UPDATE_USER_BY_ID, new BeanPropertySqlParameterSource(entity));
	}
	
	@Override
	public void delete (User entity) {
		namedParameterJdbcTemplate.update(UserQuery.DELETE_USER_CONSTRAINT, new BeanPropertySqlParameterSource(entity));
		namedParameterJdbcTemplate.update(UserQuery.DELETE_USER_BY_ID, new BeanPropertySqlParameterSource(entity));
	}
	
	private final static class UserRowMapper implements RowMapper<User> {
		@Override
		public User mapRow (ResultSet resultSet, int i) throws SQLException {
			User<Integer> user = new User<>();
			List<Tour<Integer>> tours = new ArrayList<>();
			List<Review<Integer>> reviews = new ArrayList<>();
			
			user.setId(resultSet.getInt("u_id"));
			user.setLogin(resultSet.getString("login"));
			user.setPassword(resultSet.getString("password"));
			
			Tour<Integer> tour = new Tour<>();
			Review<Integer> review = new Review<>();
			
			tour.setId(resultSet.getInt("t_id"));
			tour.setPhoto(resultSet.getString("photo"));
			Date date = resultSet.getDate("date");
			if (date != null) {
				tour.setDate(resultSet.getDate("date").toLocalDate());
			}		tour.setDescription(resultSet.getString("description"));
			tour.setDuration(Duration.ofDays(resultSet.getLong("duration")));
			tour.setCost(resultSet.getBigDecimal("cost"));
			tour.setTourType(TourType.getTypeByValue(resultSet.getString("t_type")));
			
			tours.add(tour);
			
			review.setId(resultSet.getInt("r_id"));
			review.setContent(resultSet.getString("content"));
			
			reviews.add(review);
			
			user.setTours(tours);
			user.setReviews(reviews);
			
			return user;
		}
	}
}
