package by.runets.travelagency.repository.impl;

import by.runets.travelagency.entity.Entity;
import by.runets.travelagency.repository.ICollectionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Common class which implements common CRUD interface and provides default methods implementing.
 * @param <T> is a generic param which must be inherited from Entity class.
 * @param <K> is a generic param which represents a key param.
 * @deprecated this code will remove in the next version of project, also you can use database-repository
 */
@AllArgsConstructor
@Slf4j
@Deprecated
public class AbstractRepository<T extends Entity, K> implements ICollectionRepository<T, K> {
	private List<T> data;
	/**
	 * This is a method which add entity to common collection.
	 *
	 * @param entity generic exemplar.
	 */
	@Override
	public void create(final T entity) {
		if (data.add(entity)) {
			log.info("The entity " + entity + " added to collection.");
		}
	}
	
	/**
	 * This is a method which return all entities from collection.
	 *
	 * @return list of entities.
	 */
	@Override
	public List<Optional<T>> readAll() {
		log.info("Read all method invoke");
		return data.stream().map(Optional::ofNullable).collect(Collectors.toList());
	}
	
	/**
	 * This is a method which return entity by id.
	 *
	 * @param id is a generic param which represents a key param.
	 * @return entity from collection.
	 */
	@Override
	public Optional<T> read(final K id) {
		log.info("Read entity by id from collection method invoke");
		return data.stream().filter(entity -> {
			boolean state = entity.getId() == id;
			if (state) {
				log.info("Find entity " + entity + " by id " + id);
			}
			return state;
		}).findFirst();
	}
	
	/**
	 * This is a method which update entity in collection if id of such entity is exist.
	 * @param entity entity generic exemplar
	 */
	@Override
	public void update(final T entity) {
		log.info("Update entity in collection method invoke");
		Optional<T> optional = Optional.ofNullable(entity);
		int[] index = {0};
		optional.ifPresent(
				checkedEntity -> data.forEach(
						e -> {
							if (e.getId() == checkedEntity.getId()) {
								log.info("Object " + e + " is updating to " + checkedEntity);
								data.set(index[0], checkedEntity);
							}
							index[0]++;
						}));
	}
	
	/**
	 * This is a method which delete entity from collection.
	 * @param entity generic exemplar.
	 */
	@Override
	public void delete(final T entity) {
		log.info("Delete entity from collection method invoke");
		Optional<T> optional = Optional.ofNullable(entity);
		optional.ifPresent(
				checkedEntity -> data.removeIf(e -> {
					boolean state = e.getId() == entity.getId();
					if (state) {
						log.info("The entity " + entity + " removed from collection.");
					}
					return state;
				}));
	}
}
