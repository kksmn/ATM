package by.belstu.kozlova;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private String firstname;
    private String lastname;
    private String id;
    private byte pinHash[];
    private ArrayList<Account> accounts;
    public User(String firstname, String lastname, String pin, Bank bank) {
        //user's name
        this.firstname=firstname;
        this.lastname=lastname;

        // pin's MD5 hash
        try {
            MessageDigest md=MessageDigest.getInstance("MD5");
            this.pinHash=md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        // get new user id
        this.id=bank.getNewUserID();

        //empty list of accounts
        this.accounts=new ArrayList<Account>();

        //log message
        System.out.printf("New user %s,%s with ID %s created.\n",lastname, firstname, this.id);

    }

    public void addAccount(Account acct) {
        this.accounts.add(acct);
    }
    public boolean validatePin(String pin){
        try {
            MessageDigest md=MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()),this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }


    public String getId() {
        return this.id;
    }
    public String getFirstName(){
        return this.firstname;
    }

    public void printAccountSummary() {
        System.out.printf("\n\n%s's accounts summary", this.firstname);
        System.out.println();
        for(int a=0;a<this.accounts.size();a++){
            System.out.printf("%d) %s\n",a+1,this.accounts.get(a).getSummaryLine() );

        }
    }

    public int numAccounts() {
        return this.accounts.size();
    }

    public void printAcctTransHistory(int acct) {
        this.accounts.get(acct).printTransHistory();
    }

    public String getAcctId(int acctId) {
        return this.accounts.get(acctId).getId();
    }

    public void addAcctTransaction(int acctId, double amount, String memory) {
        this.accounts.get(acctId).addTransaction(amount,memory);
    }

    public double getAcctBalance(int Id) {
        return this.accounts.get(Id).getBalance();
           }
}
