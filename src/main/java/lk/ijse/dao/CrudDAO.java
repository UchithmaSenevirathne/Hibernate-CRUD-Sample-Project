package lk.ijse.dao;

public interface CrudDAO <T> extends SuperDAO{
    boolean save(T entity);
}
