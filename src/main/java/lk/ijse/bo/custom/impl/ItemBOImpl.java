package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.ItemBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    @Override
    public boolean save(ItemDTO dto) {
        return itemDAO.save(new Item(
                dto.getCode(),
                dto.getDescription(),
                dto.getUnitPrice(),
                dto.getQtyOnHand())
        );
    }

    @Override
    public List<ItemDTO> getAll() {
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : itemDAO.getAll()) {
            itemDTOS.add(new ItemDTO(
                    item.getCode(),
                    item.getDescription(),
                    item.getUnit_price(),
                    item.getQty_on_hand())
            );
        }
        return itemDTOS;
    }

    @Override
    public boolean update(ItemDTO dto) {
        return itemDAO.update(new Item(
                dto.getCode(),
                dto.getDescription(),
                dto.getUnitPrice(),
                dto.getQtyOnHand())
        );
    }

    @Override
    public boolean delete(String id) {
        return itemDAO.delete(id);
    }
}
