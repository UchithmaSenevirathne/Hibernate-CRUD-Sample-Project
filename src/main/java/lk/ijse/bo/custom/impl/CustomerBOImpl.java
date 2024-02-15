package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean save(CustomerDTO dto) {
        return customerDAO.save(new Customer(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getContact())
        );
    }

    @Override
    public List<CustomerDTO> getAll() {
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer customer : customerDAO.getAll()) {
            customerDTOS.add(new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getContact())
            );
        }
        return customerDTOS;
    }

    @Override
    public boolean update(CustomerDTO dto) {
        return customerDAO.update(new Customer(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getContact())
        );
    }

    @Override
    public boolean delete(String id) {
        return customerDAO.delete(id);
    }
}
