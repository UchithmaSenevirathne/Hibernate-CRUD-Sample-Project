package lk.ijse.dao;

import java.util.List;

public interface CrudDAO <T> extends SuperDAO{
    boolean save(T entity);
    List<T> getAll();
}
