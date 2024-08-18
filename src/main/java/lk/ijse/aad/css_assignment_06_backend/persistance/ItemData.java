package lk.ijse.aad.css_assignment_06_backend.persistance;

import lk.ijse.aad.css_assignment_06_backend.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;

public sealed interface ItemData permits ItemDataProcess{
    boolean saveItem (ItemDTO itemDTO, Connection connection) throws Exception;
    boolean deleteItem (String id, Connection connection) throws Exception;
    boolean updateItem (String id, ItemDTO itemDTO, Connection connection) throws Exception;
    ItemDTO getItem (String id, Connection connection) throws Exception;
}
