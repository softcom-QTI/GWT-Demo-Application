package pro.softcom.archetype.repository;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

@Local
@Remote
public interface GenericDAO<T> {

	/**
	 * Persists the given entity.
	 * 
	 * @param entity
	 */
	public void persist(T entity);

	/**
	 * Removes the given entity.
	 * 
	 * @param entity
	 */
	public void remove(T entity);

	/**
	 * Merges the given entity and returns the updated object.
	 * 
	 * @param entity
	 * @return
	 */
	public T merge(T entity);

	/**
	 * Find the entity of the given class that has the given primary key.
	 * 
	 * @param entityClass
	 * @param primaryKey
	 * @return
	 */
	public T find(Class<T> entityClass, Object primaryKey);

	/**
	 * Find all entities of the given class.
	 * 
	 * @param entityClass
	 * @return
	 */
	public List<T> findAll(Class<T> entityClass);
}
