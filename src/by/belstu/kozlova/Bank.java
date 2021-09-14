package by.belstu.kozlova;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    public Bank(String name)
    {
        this.name=name;
        this.users=new ArrayList<User>();
        this.accounts=new ArrayList<Account>();
    }

    public String getNewUserID() {
        String id;
        Random rng=new Random();
        int len=6;
        boolean nonUnique;

        do {

            id = "";
            for (int c = 0; c < len; c++) {
                id += ((Integer) rng.nextInt(10)).toString();
            }
            nonUnique=false;
            for(User u: this.users){
                if(id.compareTo(u.getId())==0) {
                    nonUnique = true;
                    break;
                }
            }
        } while(nonUnique);
        return id;
    }
    public String getNewAccountId() {
        String id;
        Random rng=new Random();
        int len=10;
        boolean nonUnique;

        do {

            id = "";
            for (int c = 0; c < len; c++) {
                id += ((Integer) rng.nextInt(10)).toString();
            }
            nonUnique=false;
            for(Account acct: this.accounts){
                if(id.compareTo(acct.getId())==0) {
                    nonUnique = true;
                    break;
                }
            }
        } while(nonUnique);
        return id;
    }

    public User addUser(String firstname, String lastname, String pin){
        User newuser=new User(firstname,lastname,pin,this);
        this.users.add(newuser);

        Account newAccount=new Account("Savings",newuser,this);

        newuser.addAccount(newAccount);
        this.accounts.add(newAccount);
        return newuser;
    }
    public User userLogin(String Id, String pin){
        for(User u: this.users)
        {
            if(u.getId().compareTo(Id)==0 && u.validatePin(pin)){
                return u;
            }
        }
        // if there is no user or incorrect pin
        return null;
    }
    public void addAccount(Account acct)
    {
        this.accounts.add(acct);
    }

    public String getName() {
        return this.name;
    }
}
