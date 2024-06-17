package service;

import dao.userDAO;
import model.user;

import java.sql.SQLException;

public class UserService {
    public static Integer saveUser(user User){
        try{
            if(userDAO.isExists(User.getEmail())){
                return 0;
            }
            else {
                return userDAO.saveUser(User);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
