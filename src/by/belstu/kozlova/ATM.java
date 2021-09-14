package by.belstu.kozlova;

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank("FirstBank");

        User user = bank.addUser("Alice", "Klack", "1234");

        Account newAccount = new Account("Checking", user, bank);
        user.addAccount(newAccount);
        bank.addAccount(newAccount);

        User curUser;
        while (true) {
            curUser = ATM.mainMenuPrompt(bank, sc);
            ATM.printUserMenu(curUser, sc);
        }
    }


    public static User mainMenuPrompt(Bank bank, Scanner sc) {
        String userId;
        String pin;
        User authUser;

        do {
            System.out.printf("\n\nWelcome to %s\n\n", bank.getName());
            System.out.println("Enter UserId: ");
            userId = sc.nextLine();
            System.out.println("Enter pin: ");
            pin = sc.nextLine();

            authUser = bank.userLogin(userId, pin);
            if (authUser == null)
                System.out.println("Incorrect id/pin, try again");
        } while (authUser == null);
        return authUser;
    }

    public static void printUserMenu(User user,Scanner sc){
        user.printAccountSummary();

        int choice;
        do{
            System.out.printf("Welcome %s, what would you like to do? ",user.getFirstName());
            System.out.println();
            System.out.println(" 1: Show account transaction history");
            System.out.println(" 2: Withdraw");
            System.out.println(" 3: Deposit");
            System.out.println(" 4: Transfer");
            System.out.println(" 5: Quit");
            System.out.println();
            System.out.println(" Enter choice");
            choice=sc.nextInt();

            if(choice<1 || choice>5){
                System.out.println("Invalid choice, try again");
            }
        }while(choice<1 || choice>5);

            switch(choice) {
                case 1:
                    ATM.showTransHistory(user, sc);
                    break;
                case 2:
                    ATM.withdrawFunds(user, sc);
                    break;
                case 3:
                    ATM.depositFunds(user, sc);
                    break;
                case 4:
                    ATM.transferFunds(user, sc);
                    break;
                case 5:
                    sc.nextLine();
                    break;
            }

            if(choice!=5)
                ATM.printUserMenu(user,sc);




        }

    private static void depositFunds(User user, Scanner sc) {
        int toAcct;
        double amount;
        double acctBal;
        String memory;

        //account to transfer from
        do {
            System.out.printf("Enter the number(1-%d) of the account\n" + "to deposit in: ", user.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= user.numAccounts())
            System.out.println("Invalid account, try again");
        } while (toAcct < 0 || toAcct >= user.numAccounts());
            acctBal = user.getAcctBalance(toAcct);

        // amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount=sc.nextDouble();
            if (amount < 0)
                System.out.println("Amount must be greater than zero");
        } while (amount < 0 );
        sc.nextLine();

        //get a memory
        System.out.println("Enter a memory");
        memory=sc.nextLine();

        // do the withdraw
        user.addAcctTransaction(toAcct,amount,memory);
    }

    private static void withdrawFunds(User user, Scanner sc) {

        int fromAcct;
        double amount;
        double acctBal;
        String memory;

        //account to transfer from
        do {
            System.out.printf("Enter the number(1-%d) of the account\n" + "to withdraw from: ",user.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= user.numAccounts())
            System.out.println("Invalid account, try again");
        } while (fromAcct < 0 || fromAcct >= user.numAccounts());
        acctBal = user.getAcctBalance(fromAcct);

        // amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount=sc.nextDouble();
            if (amount < 0)
                System.out.println("Amount must be greater than zero");
            else if (amount > acctBal)
                System.out.printf("Amount mustn't be greater than balance" + " balance of $%.02f.\n", acctBal);
        } while (amount < 0 || amount > acctBal);
        sc.nextLine();

        //get a memory
        System.out.println("Enter a memory");
        memory=sc.nextLine();

        // do the withdraw
        user.addAcctTransaction(fromAcct,-1*amount,memory);
    }

    private static void transferFunds(User user, Scanner sc) {
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        //account to transfer from
        do {
            System.out.printf("Enter the number(1-%d) of the account\n" + "to transfer from: ", user.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= user.numAccounts())
            System.out.println("Invalid account, try again");
        } while (fromAcct < 0 || fromAcct >= user.numAccounts());
        acctBal = user.getAcctBalance(fromAcct);

        //account to transfer to
        do {
            System.out.println("Enter the number(1-%d) of the account\n" + "to transfer to: ");
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= user.numAccounts())
            System.out.println("Invalid account, try again");
        } while (toAcct < 0 || toAcct >= user.numAccounts());

        // amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount=sc.nextDouble();
            if (amount < 0)
                System.out.println("Amount must be greater than zero");
            else if (amount > acctBal)
                System.out.printf("Amount mustn't be greater than balance" + " balance of $%.02f.\n", acctBal);
        } while (amount < 0 || amount > acctBal);

        // transfer
        user.addAcctTransaction(fromAcct, -1 * amount, String.format("Transfer to account %s", user.getAcctId(toAcct)));
        user.addAcctTransaction(toAcct, amount, String.format("Transfer to account %s", user.getAcctId(fromAcct)));
    }

    private static void showTransHistory(User user, Scanner sc) {

        int Acct;
        do{
            System.out.printf("Enter the number: (1-%d) of the account", user.numAccounts());
            Acct=sc.nextInt()-1;
            if(Acct<0 || Acct>=user.numAccounts()){
                System.out.println("Invalid account, try again");

            }
        } while(Acct<0 || Acct>=user.numAccounts());

        user.printAcctTransHistory(Acct);
    }
}

