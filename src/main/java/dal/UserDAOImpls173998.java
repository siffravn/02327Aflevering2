package dal;

import dal.dto.IUserDTO;
import dal.dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO Rename class so it matches your study-number
public class UserDAOImpls173998 implements IUserDAO {

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s173998?"
                + "user=s173998&password=qRibfryD9hC7hNICVopba");
    }

    @Override
    public void createUser(IUserDTO user) throws DALException {

        try (Connection c = createConnection()){

            PreparedStatement statement = c.prepareStatement("INSERT INTO user VALUES(?,?,?)");
            statement.setInt(1,user.getUserId());
            statement.setString(2, user.getUserName());
            statement.setString(3,user.getIni());
            statement.executeUpdate();

            insertRoles(user, c);

        }catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

    }

    @Override
    public IUserDTO getUser(int userId) throws DALException {

       try (Connection c = createConnection()){
           PreparedStatement statement = c.prepareStatement("SELECT * FROM user NATURAL LEFT JOIN roles WHERE ID = ? ;");
           statement.setInt(1,userId);
           ResultSet resultSet = statement.executeQuery();

           IUserDTO user = null;
           while (resultSet.next()){
               user = applyData(resultSet);
           }
           return user;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

    }



    @Override
    public List<IUserDTO> getUserList() throws DALException {

        try (Connection c = createConnection()){
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID FROM user");

            List<IUserDTO> userList = new ArrayList<>();
            while (resultSet.next()) {
                IUserDTO user = getUser(resultSet.getInt("ID"));
                userList.add(user);
            }
            return userList;
        }catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }



    @Override
    public void updateUser(IUserDTO user) throws DALException {

        try (Connection c = createConnection()){
            PreparedStatement statement = c.prepareStatement("UPDATE user SET name = ?, ini = ? WHERE ID = ?");
            statement.setString(1, user.getUserName());
            statement.setString(2,user.getIni());
            statement.setInt(3,user.getUserId());
            statement.executeUpdate();

            statement = c.prepareStatement("DELETE FROM roles WHERE ID = ?");
            statement.setInt(1,user.getUserId());
            statement.executeUpdate();

            insertRoles(user, c);

        }catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void deleteUser(int userId) throws DALException {

        try (Connection c = createConnection()){
            PreparedStatement statement = c.prepareStatement("DELETE FROM user WHERE ID = ?");
            statement.setInt(1,userId);
            statement.executeUpdate();

        }catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }


    private IUserDTO applyData(ResultSet resultSet)throws DALException {
        try {
            IUserDTO user = new UserDTO();

            user.setUserId(resultSet.getInt("ID"));
            user.setUserName(resultSet.getString("name"));
            user.setIni(resultSet.getString("ini"));
            user.addRole(resultSet.getString("role"));

            while (resultSet.next()){
                user.addRole(resultSet.getString("role"));
            }
            return user;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    private void insertRoles(IUserDTO user, Connection c) throws DALException{
        try {
            for (String temp : user.getRoles()) {
                String role = temp;
                PreparedStatement statement = c.prepareStatement("INSERT INTO roles VALUES(?,?)");
                statement.setInt(1, user.getUserId());
                statement.setString(2, role);
                statement.executeUpdate();
            }
        }catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }
}
