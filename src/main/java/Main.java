import dal.IUserDAO;
import dal.UserDAOImpls173998;
import dal.dto.IUserDTO;
import dal.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserDAOImpls173998 uDAO = new UserDAOImpls173998();
        IUserDTO user = new UserDTO();
        IUserDTO update = new UserDTO();

        try {
//             //TEST of getUser() - SUCCEEDED
//                user = uDAO.getUser(1);
//                System.out.println(user.toString());
//
//             //TEST of getUserLIDT() - SUCCEEDED
//                List<IUserDTO> ulist = uDAO.getUserList();
//                for(IUserDTO temp : ulist){
//                    System.out.println(temp.toString());
//                }
//
//            //TEST of createUser() - SUCCEEDED
//                List<String> roles = new ArrayList<>();
//                roles.add("Student");
//                roles.add("SW");
//
//                user.setUserId(6);
//                user.setUserName("Alen");
//                user.setIni("AH");
//                user.setRoles(roles);
//
//                uDAO.createUser(user);
//
//            // TEST of updateUser() - ??
//                roles.clear();
//                roles.add("Student");
//                roles.add("BIO");
//
//                user.setUserName("Frederik");
//                user.setIni("FS");
//                user.setRoles(roles);
//
//                uDAO.updateUser(user);
//
//
//            // TEST of deleteUser() - ??
//                uDAO.deleteUser(7);

        }catch (IUserDAO.DALException e) {
           e.getMessage();
        }

    }

}

