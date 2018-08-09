package core;

import abstracts.User;

import java.util.ArrayList;

public class UserDataBase {
    private ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> getUsers() {
        return users;
    }
    public void addUser(User anUser){
        users.add(anUser);
    }
    public Boolean isUserStaff(String username){
        Boolean result=false;
        for (User each:users){
            if(each.getName().equals(username)&&each.getType().equals("Staff")){
                result=true;
            }
        }
        return result;
    }
}
