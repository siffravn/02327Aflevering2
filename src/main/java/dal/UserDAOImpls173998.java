package dal;

import dal.dto.IUserDTO;
import dal.dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO Rename class so it matches your study-number
public class UserDAOImpls173998 implements IUserDAO {
    //TODO Make a connection to the database
    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s173998?"
                + "user=s173998&password=qRibfryD9hC7hNICVopba");
    }

    @Override
    public void createUser(IUserDTO user) throws DALException {
        //TODO Implement this - Should insert a user into the db using data from UserDTO object.

        try (Connection c = createConnection()){
            String values = user.getUserId() + ", '" + user.getUserName() + "', '" + user.getIni() + "', '" + user.getRoles().toString() + "'" ;
            Statement statement = c.createStatement();
            statement.executeUpdate("INSERT INTO userDTO VALUES(" + values +")");
        }catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

    }

    @Override
    public IUserDTO getUser(int userId) throws DALException {
        //TODO Implement this - should retrieve a user from db and parse it to a UserDTO
        //TODO: Make a user from the resultset

       try (Connection c = createConnection()){
           Statement statement = c.createStatement();
           ResultSet resultSet = statement.executeQuery("" +
                   "SELECT *\n" +
                   "FROM user\n" +
                    "NATURAL LEFT JOIN roles\n" +
                   "WHERE"  ID = 1");

           IUserDTO user = null;
           if (resultSet.next()){
               user = applyData(resultSet);
           }
           return user;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

    }



    @Override
    public List<IUserDTO> getUserList() throws DALException {
        //TODO Implement this - Should retrieve ALL users from db and parse the resultset to a List of UserDTO's.

        try (Connection c = createConnection()){
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM userDTO");

            List<IUserDTO> userList = new ArrayList<>();
            while (resultSet.next()) {
                IUserDTO user = applyData(resultSet);
                userList.add(user);
            }
            return userList;
        }catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }



    @Override
    public void updateUser(IUserDTO user) throws DALException {
        //TODO Implement this - Should update a user in the db using data from UserDTO object.

        try (Connection c = createConnection()){
            Statement statement = c.createStatement();
            statement.executeUpdate("UPDATE userDTO SET username = '" + user.getUserName() +"', ini = '" +user.getIni()+ "', roles = '" +user.getRoles().toString()+ "' WHERE userID =" + user.getUserId() + ";");
        }catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void deleteUser(int userId) throws DALException {
        //TODO Implement this - Should delete a user with the given userid from the db.

        try (Connection c = createConnection()){
            Statement statement = c.createStatement();
            statement.executeUpdate("DELETE FROM userDTO WHERE userID =" + userId + "");
        }catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }


    private IUserDTO applyData(ResultSet resultSet)throws DALException {
        try {
            IUserDTO user = new UserDTO();

            user.setUserId(resultSet.getInt("userId"));
            user.setUserName(resultSet.getString("userName"));
            user.setIni(resultSet.getString("ini"));

            while (resultSet.next()){
                user.addRole(resultSet.getString("role"));
            }
            return user;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }
}
