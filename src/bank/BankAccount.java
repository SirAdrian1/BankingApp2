package bank;
import bank.exceptions.InsufficientFundsException;
import bank.exceptions.InterestType;
import bank.exceptions.User;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.UUID;

public class BankAccount {
    private String id;
    private User owner;
    private double balance;
    private double interest;
    private InterestType interestType;
    private Queue<String> operations;

    public BankAccount(User owner, double interest, InterestType interestType){
        this.owner = owner;
        this.interest = interest;
        this.interestType = interestType;
        this.id = UUID.randomUUID().toString();
        this.operations = new ArrayDeque<>(5);
    }

    public BankAccount(Object owner, double interest, InterestType interestType) {

    }


    public void add(double money){
        balance += money;
        addOperation(String.format("%s %.2f%n", "added", money));
    }
    public boolean withdraw(double money) throws InsufficientFundsException {
        if(interest > 1){
            return false;
        }
        if(money > balance){
            throw new InsufficientFundsException("Not enough money to complete operation");
        }

        balance -= money;
        addOperation(String.format("%s %.2f%n", "withdrawn", money));
        return true;
    }

    public List<String> getHistory(){
        List<String> operationsList = new ArrayList<>(this.operations);
        addOperation(String.format("%s%n", "viewed history"));
        return operationsList;
    }

    public boolean transfer(BankAccount account, double amount) throws InsufficientFundsException {
        boolean withdrawn = withdraw(amount);
        if(withdrawn){
            account.add(amount);
            addOperation(String.format("%s %.2f to %s %n","transferred",amount,account.getId()));
        }
        return withdrawn;
    }

    public String getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public double getInterest() {
        return interest;
    }

    public InterestType getInterestType() {
        return interestType;
    }

    private void addOperation(String operation){
        if(operations.size() == 5){
            operations.remove();
        }
        operations.add(operation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount)) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    private class User {
    }
}