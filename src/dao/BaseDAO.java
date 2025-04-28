package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import util.DatabaseConnection;


public abstract class BaseDAO<T> {
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    // CRUD
    public abstract T getById(int id) throws SQLException;
    public abstract List<T> getAll() throws SQLException;
    public abstract int create(T entity) throws SQLException;
    public abstract boolean update(T entity) throws SQLException;
    public abstract boolean delete(int id) throws SQLException;

    protected void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    System.err.println("Error closing resource: " + e.getMessage());
                }
            }
        }
    }
}