package manager;

import com.sun.xml.internal.ws.developer.MemberSubmissionAddressing;
import db.DBConnectionProvider;
import model.Gender;
import model.User;
import util.Validator;

import java.sql.*;


public class UserManager {

    private DBConnectionProvider provider;
    private Connection connection;


    public UserManager() {
        provider = DBConnectionProvider.getInstance();
        connection = provider.getConnection();
    }

    public void addUser(User user) {
        String query = "INSERT INTO `user` (`name`,`surname`,`email`, `gender`, `password`) VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getGender().name());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save the user at DB." + e);
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

    public boolean isEmailExists(String email) {
        String query = "SELECT count (*) FROM `user` WHERE `email` = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            } else {return false;}
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }


    public User getUserByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM user WHERE `email` = ? AND `password` = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setSurname(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
                user.setGender(Gender.valueOf(resultSet.getString(5)));
                user.setPassword(resultSet.getString(6));
                return user;
            } else return null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user from DB.");
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


}
