package lk.ijse.aad.css_assignment_06_backend.persistance;

import lk.ijse.aad.css_assignment_06_backend.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public sealed interface ItemData permits ItemDataProcess{
    boolean saveItem (ItemDTO itemDTO, Connection connection) throws Exception;
    boolean deleteItem (String id, Connection connection) throws Exception;
    boolean updateItem (String id, ItemDTO itemDTO, Connection connection) throws Exception;
    ItemDTO getItem (String id, Connection connection) throws Exception;
    List<ItemDTO> getAllItems(Connection connection) throws Exception;
    boolean updateItemQuantity(String id, String qty, Connection connection) throws Exception;
}
