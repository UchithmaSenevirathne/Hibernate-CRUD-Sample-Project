package lk.ijse.dao;

import java.util.List;

public interface CrudDAO <T> extends SuperDAO{

    //save, update, delete, get

    boolean save(T entity);
    List<T> getAll();
    boolean update(T entity);
    boolean delete(String id);
}
