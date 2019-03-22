package database.dao;

import java.util.List;
import java.util.Optional;

public interface IDAO<T> {

    /**
     * Gets all the entries within the table
     * @return All entries within the table of the database
     */
    T get();

    /**
     * Inserts the instance of the object within the database
     */
    void insert(T domain);

    /**
     * Updates the instance of the object within the database
     */
    void update(T domain);

    /**
     * Delete's the object from the database
     */
    void delete(T domain);
}
