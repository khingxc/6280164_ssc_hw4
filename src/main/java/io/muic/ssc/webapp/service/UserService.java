package io.muic.ssc.webapp.service;

import io.muic.ssc.webapp.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserService {

    private static final String INSERT_USER_SQL = "INSERT INTO hw4_table (username, password, display_name) VALUES (?, ?, ?);";
    private static final String SELECT_USER_SQL = "SELECT * FROM hw4_table WHERE username = ?;";

    private DatabaseConnectionService databaseConnectionService;

    public void setDatabaseConnectionService(DatabaseConnectionService databaseConnectionService) {
        this.databaseConnectionService = databaseConnectionService;
    }

    // create new user
    public void createUser(String username, String password, String displayName) throws UserServiceException {

        try{
            Connection connection = databaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL);
            ps.setString(1, username);
            ps.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
            ps.setString(3, displayName);
            ps.executeUpdate();
            connection.commit();
        }
        catch (SQLIntegrityConstraintViolationException e){
            throw new UsernameNotUniqueException(String.format("Username %s has already been taken.", username));
        }
        catch (SQLException e){
            throw new UserServiceException(e.getMessage());
        }

    }

    // find user by username
    public User findByUsername(String username) {
        try {
            Connection connection = databaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_USER_SQL);
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return new User(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"), // this is hashed password
                    resultSet.getString("display_name")
            );
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    // delete user
    // list all users
    // update user by user id

    public static void main(String[] args) throws UserServiceException {
        UserService userService = new UserService();
        userService.setDatabaseConnectionService(new DatabaseConnectionService());
//        userService.createUser("admin", "123678", "anonymous guy");
        User user = userService.findByUsername("khingc");
        System.out.println(user.getUsername());
    }

}
