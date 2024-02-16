package lk.ijse.bo;

import java.util.List;

public interface CrudBO <T> extends SuperBO{

    //save, update, delete, get

    boolean save(T dto);
    List<T> getAll();
    boolean update(T dto);
    boolean delete(String id);
}
