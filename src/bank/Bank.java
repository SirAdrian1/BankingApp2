package bank;

import bank.exceptions.InsufficientFundsException;
import bank.exceptions.InterestType;
import bank.exceptions.NonExcistingBankAccountException;
import bank.exceptions.User;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import static java.lang.Math.pow;

public class Bank {
    private Set<BankAccount> accounts;

    public Bank(){
        accounts = new HashSet<>();
    }

    void createBankAccount(User owner, double interest, InterestType interestType){
        BankAccount account = new BankAccount(
                owner,interest,interestType);
        accounts.add(account);
        System.out.println(account.getId());
    }

    void createBankAccount(String firstName, String lastName, int age, double interest, InterestType interestType){
        User owner = new User(firstName,lastName, age);
        createBankAccount(owner,interest,interestType);
    }

    public List<String> showOperations(String accountID) throws NonExcistingBankAccountException {
        for(BankAccount account : accounts){
            if(accountID.equals(account.getId())){
                return account.getHistory();
            }
        }
        throw new NonExcistingBankAccountException(String.format("Bank account %s does not exist.%n",accountID));
    }

    public void addMoney(String accountID, double amount) throws NonExcistingBankAccountException {
        BankAccount account = findAccount(accountID);
        account.add(amount);
    }
    public boolean withdrawMoney(String accountID,double amount) throws NonExcistingBankAccountException, InsufficientFundsException {
        BankAccount account = findAccount(accountID);
        return account.withdraw(amount);
    }

    public boolean transferMoney(String source, String destination, double amount) throws NonExcistingBankAccountException, InsufficientFundsException {
        BankAccount sourceAccount = findAccount(source);
        BankAccount destinationAccount = findAccount(destination);

        return sourceAccount.transfer(destinationAccount,amount);
    }

    public double calculateAmount(String accountID, int months) throws NonExcistingBankAccountException{
        BankAccount account = findAccount(accountID);
        double years = ((double) months)/12;
        switch(account.getInterestType()) {
            case SIMPLE:
                return account.getBalance() * account.getInterest() * years;
            case COMPLEX:
                return account.getBalance() * (pow((1 + account.getInterest()), years) - 1);
            default:
                throw new IllegalArgumentException();
        }
    }
    private BankAccount findAccount(String accountID) throws NonExcistingBankAccountException{
        for(BankAccount account : accounts){
            if(accountID.equals(account.getId())){
                return account;
            }
        }
        throw new NonExcistingBankAccountException(String.format("Bank account %s does not exist.%n",accountID));
    }
}