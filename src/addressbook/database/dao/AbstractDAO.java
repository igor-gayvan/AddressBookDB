/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addressbook.database.dao;

import addressbook.subject.EntityAddressBook;
import addressbook.database.WrapperConnector;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Haivan
 */
public abstract class AbstractDAO<T extends EntityAddressBook> {

    protected Connection connection;

    protected WrapperConnector connector;

    public AbstractDAO() {
    }

    // методы добавления, поиска, замены, удаления
    public abstract List<T> selectAll();

    public abstract T findEntityById(int id);

    public abstract boolean delete(int id);

    public abstract boolean delete(T entity);

    public abstract boolean insert(T entity);

    public abstract boolean update(T entity);

    public void close(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            // лог о невозможности закрытия Statement
        }
    }

    // методы закрытия коннекта и Statement
    public void close() {
        connector.closeConnection();
    }

    protected void closeStatement(Statement statement) {
        connector.closeStatement(statement);
    }

}
