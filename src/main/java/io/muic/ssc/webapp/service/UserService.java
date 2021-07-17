package io.muic.ssc.webapp.service;

import io.muic.ssc.webapp.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserService is used in too many places and we only need one instance of it so we will make it singleton
 */
public class UserService {

    private static final String INSERT_USER_SQL = "INSERT INTO hw4_table (username, password, display_name) VALUES (?, ?, ?);";
    private static final String SELECT_USER_SQL = "SELECT * FROM hw4_table WHERE username = ?;";
    private static final String SELECT_ALL_USER_SQL = "SELECT * FROM hw4_table;";

    private static UserService service;
    private DatabaseConnectionService databaseConnectionService;

    public UserService() {

    }

    public static UserService getInstance() {
        if (service == null){
            service = new UserService();
            service.setDatabaseConnectionService(DatabaseConnectionService.getInstance());
        }
        return service;
    }

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
            return null;
        }
    }


    // list all users

    /**
     * list all users in the database
     * @return list of users, never return null
     */
    public List<User> findAll(){
        List<User> users = new ArrayList<>();
        try {
            Connection connection = databaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USER_SQL);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()) {
                users.add(
                        new User(
                                resultSet.getLong("id"),
                                resultSet.getString("username"),
                                resultSet.getString("password"),
                                resultSet.getString("display_name")));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    // delete user
    public void deleteUserByUsername() {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Users can only change their display name when updating profile
     * @param id
     * @param displayName
     */

    // update user by user id
    public void updateUserById(long id, String displayName) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Change password method is separated from update user method because it normally
     * never change password and update profile at the same time.
     * @param newPassword
     */

    public void changePassword(String newPassword) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    public static void main(String[] args) throws UserServiceException {
        UserService userService = UserService.getInstance();
        try {
            userService.createUser("newbie", "180721", "BornToday");
        }
        catch(UserServiceException e){
            e.printStackTrace();
        }
    }

}
