import dal.IUserDAO;
import dal.UserDAOImpls173998;
import dal.dto.IUserDTO;
import dal.dto.UserDTO;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        UserDAOImpls173998 uDAO = new UserDAOImpls173998();
        IUserDTO user = new UserDTO();

        try {
            // TEST of getUser() - SUCCEEDED
            user = uDAO.getUser(1);
            System.out.println(user.toString());


        }catch (IUserDAO.DALException e) {
           e.getMessage();
        }

    }

}

