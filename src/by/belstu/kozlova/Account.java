package by.belstu.kozlova;

import java.util.ArrayList;

public class Account {
    private String name;
    private String id;
    private User holder;
    private ArrayList<Transaction> transactions;

    public Account(String name, User holder, Bank bank) {
        this.name = name;
        this.holder = holder;

        //new account Id
        this.id = bank.getNewAccountId();

        //init transactions
        this.transactions = new ArrayList<Transaction>();


    }

    public String getId() {
        return id;
    }

    public String getSummaryLine() {
        double balance = this.getBalance();
        if (balance >= 0)
            return String.format("%s : $%.02f : %s", this.id, balance, this.name);
        else
            return String.format("%s : $(%.02f) : %s", this.id, balance, this.name);

    }
    public double getBalance()
    {
        double balance=0;
        for(Transaction t: this.transactions)
            balance+=t.getAmount();
        return balance;
    }

    public void printTransHistory() {
        System.out.printf("\nTransaction history for account %s\n", this.id);
        for( int t=this.transactions.size()-1;t>=0;t--){
            System.out.printf(this.transactions.get(t).getSummaryLine());
            System.out.println();
        }
    }

    public void addTransaction(double amount, String memory) {
        //create new transaction object and add it to list
        Transaction newTrans=new Transaction(amount,memory,this);
        this.transactions.add(newTrans);
    }
}

