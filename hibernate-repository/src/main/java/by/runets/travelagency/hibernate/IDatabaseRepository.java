package by.runets.travelagency.hibernate;

import java.util.List;
import java.util.Optional;

/** Common interface which provides CRUD methods in repository layer.
 * @param <T> is a generic param which must be inherited from PrimaryKeyEntity class.
 * @param <K> is a generic param which represents a key param.
 */
public interface IDatabaseRepository<T, K> {
	K create (final T entity);
	List<Optional<T>> readAll (final Class<T> classType);
	Optional<T> read (final Class<T> classType, final K id);
	void update (final T entity);
	void delete (final T entity);
}