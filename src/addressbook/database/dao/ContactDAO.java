/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addressbook.database.dao;

import addressbook.subject.contact.Contact;
import addressbook.database.WrapperConnector;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Haivan
 */
public class ContactDAO extends AbstractDAO<Contact> {

    private final static String SQL_CONTACT_SELECT_LIST = "{call sp_contact_sel(?, ?)}";
    private final static String SQL_CONTACT_INSERT = "{call sp_contact_ins(?, ?, ?, ?, ?)}";
    private final static String SQL_CONTACT_UPDATE = "{call sp_contact_upd(?, ?, ?, ?, ?)}";
    private final static String SQL_CONTACT_DELETE = "{call sp_contact_del(?)}";

    private String filterNameFull;

    public ContactDAO() {
        this.connector = new WrapperConnector();
    }

    public String getFilterNameFull() {
        return filterNameFull;
    }

    public void setFilterNameFull(String filterNameFull) {
        this.filterNameFull = filterNameFull;
    }

    /**
     * Get all records from database
     *
     * @return
     */
    @Override
    public List<Contact> selectAll() {
        List<Contact> contactList = new ArrayList<>();
        CallableStatement cs = null;

        try {
            cs = connector.getConnection().prepareCall(SQL_CONTACT_SELECT_LIST);

            cs.setNull("pi_contact_id", java.sql.Types.INTEGER);

            if (filterNameFull == null) {
                cs.setNull("pi_name_full", java.sql.Types.VARCHAR);
            } else {
                cs.setString("pi_name_full", filterNameFull);
            }

            ResultSet resultSet = cs.executeQuery();

            //contact_id int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
            //name_full varchar(255) NOT NULL COMMENT 'ФИО',
            //phone varchar(255) DEFAULT NULL COMMENT 'Телефон',
            //skype varchar(255) DEFAULT NULL COMMENT 'Скайп',
            //email varchar(255) DEFAULT NULL COMMENT 'Электронная почта',
            while (resultSet.next()) {
                Contact contact = new Contact();

                contact.setId(resultSet.getInt("contact_id"));
                contact.setNameFull(resultSet.getString("name_full"));
                contact.setPhone(resultSet.getString("phone"));
                contact.setSkype(resultSet.getString("skype"));
                contact.setEmail(resultSet.getString("email"));

                contactList.add(contact);
            }
        } catch (SQLException e) {
            System.err.println("SQL exception (request or table failed):\n " + e);
        } finally {
            closeStatement(cs);
//            close();
        }
        return contactList;
    }

    /**
     * Add new contact into database
     *
     * @param contact
     * @return
     */
    @Override
    public boolean insert(Contact contact) {
        CallableStatement cs = null;

        boolean isInserted = false;

        try {
            cs = connector.getConnection().prepareCall(SQL_CONTACT_INSERT);

            //IN `pi_contact_id` INTEGER(11),
            //IN `pi_name_full` VARCHAR(255),
            //IN `pi_phone` VARCHAR(255),
            //IN `pi_skype` VARCHAR(255),
            //IN `pi_email` VARCHAR(255)            
            cs.setNull("pi_contact_id", java.sql.Types.INTEGER);
            cs.setString("pi_name_full", contact.getNameFull());
            cs.setString("pi_phone", contact.getPhone());
            cs.setString("pi_skype", contact.getSkype());
            cs.setString("pi_email", contact.getEmail());

            int executeUpdate = cs.executeUpdate();

            try (ResultSet rs = cs.getGeneratedKeys()) {
                if (rs.next()) {
                    contact.setId(rs.getInt(1));
                }
            }

            isInserted = executeUpdate > 0;
        } catch (SQLException e) {
            System.err.println("SQL exception (request or table failed): \n" + e);
        } finally {
            closeStatement(cs);
        }
        return isInserted;
    }

    @Override
    public Contact findEntityById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(int id) {
        CallableStatement cs = null;

        boolean isDeleted = false;

        try {
            cs = connector.getConnection().prepareCall(SQL_CONTACT_DELETE);

            //IN `pi_contact_id` INTEGER(11),
            cs.setInt("pi_contact_id", id);

            int executeUpdate = cs.executeUpdate();

            isDeleted = executeUpdate > 0;
        } catch (SQLException e) {
            System.err.println("SQL exception (request or table failed): \n" + e);
        } finally {
            closeStatement(cs);
        }

        return isDeleted;
    }

    @Override
    public boolean delete(Contact entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Contact contact) {
        CallableStatement cs = null;

        boolean isUpdated = false;

        try {
            cs = connector.getConnection().prepareCall(SQL_CONTACT_UPDATE);

            //IN `pi_contact_id` INTEGER(11),
            //IN `pi_name_full` VARCHAR(255),
            //IN `pi_phone` VARCHAR(255),
            //IN `pi_skype` VARCHAR(255),
            //IN `pi_email` VARCHAR(255)            
            cs.setInt("pi_contact_id", contact.getId());
            cs.setString("pi_name_full", contact.getNameFull());
            cs.setString("pi_phone", contact.getPhone());
            cs.setString("pi_skype", contact.getSkype());
            cs.setString("pi_email", contact.getEmail());

            int executeUpdate = cs.executeUpdate();

            try (ResultSet rs = cs.getGeneratedKeys()) {
                if (rs.next()) {
                    contact.setId(rs.getInt(1));
                }
            }

            isUpdated = executeUpdate > 0;
        } catch (SQLException e) {
            System.err.println("SQL exception (request or table failed): \n" + e);
        } finally {
            closeStatement(cs);
        }

        return isUpdated;
    }
}
