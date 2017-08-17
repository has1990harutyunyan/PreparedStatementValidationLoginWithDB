package manager;

import db.DBConnectionProvider;
import model.Book;

import javax.naming.ldap.PagedResultsControl;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Hasmik on 25.06.2017.
 */
public class BookManager {
    private DBConnectionProvider provider;
    private Connection connection;

    public BookManager() {
        provider = DBConnectionProvider.getInstance();
        connection = provider.getConnection();
    }


    public void addBook(Book book) {

        String query = "INSERT INTO book (`name`, `author`, `price`, `description`, `user_id`) VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setDouble(3, book.getPrice());
            preparedStatement.setString(4, book.getDescription());
            preparedStatement.setLong(5, book.getUserId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save the book at DB." + e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public ArrayList<Book> getBooksByUserId(long userId) {
        String query = "SELECT * FROM book WHERE `user_id` = ?";
        ArrayList<Book> books ;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            books = new ArrayList<Book>();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong(1));
                book.setName(resultSet.getString(2));
                book.setAuthor(resultSet.getString(3));
                book.setPrice(resultSet.getDouble(4));
                book.setDescription(resultSet.getString(5));
                book.setUserId(resultSet.getLong(6));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve books by userId." + e);
        }
        return books;

    }


}
