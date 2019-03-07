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
     * Saves the instance of the object within the database
     */
    void saveOrUpdate();

    /**
     * Deeletes the object from the database
     */
    void delete();
}
