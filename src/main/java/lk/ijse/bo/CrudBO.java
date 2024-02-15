package lk.ijse.bo;

public interface CrudBO <T> extends SuperBO{
    boolean save(T dto);
}
