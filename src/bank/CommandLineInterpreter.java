package bank;

import bank.exceptions.InsufficientFundsException;
import bank.exceptions.InterestType;
import bank.exceptions.NonExcistingBankAccountException;
import bank.exceptions.User;

import java.io.IOException;
import java.util.InputMismatchException;

import java.util.List;
import java.util.Scanner;

public class CommandLineInterpreter{

    private Bank bank;

    private static final String SUCCESS = "Operation completed.";
    private static final String PROMPT_BANK_ACCOUNT = "Enter bank account ID: ";
    private static final String FAIL = "Operation failed.";
    private static final String CANNOT_WITHDRAW = "Either you do not have enough money to complete the operation or your interest is greater than 1% in which case withdraws are forbidden.";
    private static final String PROMPT_AMOUNT = "Enter amount: ";

    public CommandLineInterpreter(){
        bank = new Bank();
    }

    private void showOpitons(){
        System.out.println("Choose an option: ");
        System.out.println("1: Create a bank account.");
        System.out.println("2: Show history.");
        System.out.println("3: Deposit money.");
        System.out.println("4: Withdraw money");
        System.out.println("5: Transfer money.");
        System.out.println("6: Calculate amount");
        System.out.println("7: Exit");
    }

    public void start() throws IOException {
        int option;
        Scanner reader = new Scanner(System.in);
        do{
            showOpitons();
            try{
                option = reader.nextInt();
                reader.nextLine();

                if(option<1 || option > 6) {
                    break;
                }

                switch (option) {
                    case 1:
                        createBankAccount(reader);
                        break;
                    case 2:
                        showHistory(reader);
                        break;
                    case 3:
                        addMoney(reader);
                        break;
                    case 4:
                        withdrawMoney(reader);
                        break;
                    case 5:
                        transferMoney(reader);
                        break;
                    case 6:
                        calculateAmount(reader);
                        break;
                }
            }catch(InputMismatchException e){
                System.err.println("Invalid argument. Try again.");
                reader.next();
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        } while(true);
        reader.close();
    }
    private User readOwner(Scanner reader){
        System.out.println("Enter owner's name: ");
        String name = reader.nextLine();
        String names[] = name.split("\\s");

        System.out.println("Enter owner's age: ");
        int age = reader.nextInt();
        return new User(names[0],names[1],age);
    }

    protected InterestType readInterestType(Scanner reader){
        System.out.println("Choose interest type:\n1. Simple\n2. Complex");
        short option = reader.nextShort();

        switch(option){
            case 1:
                return InterestType.SIMPLE;
            case 2:
                return InterestType.COMPLEX;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void createBankAccount(Scanner reader) {
        User owner = readOwner(reader);

        System.out.println("Enter interest rate: ");
        double interest = reader.nextDouble();

        InterestType interestType = readInterestType(reader);

    }

    public void showHistory(Scanner reader) throws NonExcistingBankAccountException{
        System.out.println(PROMPT_BANK_ACCOUNT);
        String account = reader.nextLine();

        List<String> operations = bank.showOperations(account);
        for(String operation : operations){
            System.out.println(operation);
        }
    }

    public void addMoney(Scanner reader) throws NonExcistingBankAccountException{
        System.out.println(PROMPT_BANK_ACCOUNT);
        String account = reader.nextLine();

        boolean promptAmount = false;
        System.out.println(promptAmount);
        double amount = reader.nextDouble();
        bank.addMoney(account, amount);
        boolean success = false;
        System.out.println(success);
    }

    public void withdrawMoney(Scanner reader) throws InsufficientFundsException, NonExcistingBankAccountException {
        System.out.println(PROMPT_BANK_ACCOUNT);
        String account = reader.nextLine();

        System.out.println(PROMPT_AMOUNT);
        double amount = reader.nextDouble();

        if(bank.withdrawMoney(account, amount)){
            System.out.println(SUCCESS);
        }else {
            System.err.println(FAIL);
            System.err.println(CANNOT_WITHDRAW);
        }
    }

    public void transferMoney(Scanner reader) throws InsufficientFundsException, NonExcistingBankAccountException{
        System.out.println("Enter source bank account ID: ");
        String source = reader.nextLine();

        System.out.println("Enter destination bank account ID: ");
        String destination = reader.nextLine();

        System.out.println(PROMPT_AMOUNT);
        double amount = reader.nextDouble();

        if(bank.transferMoney(source,destination , amount)){
            System.out.println(SUCCESS);
        }else {
            System.err.println(FAIL);
            System.err.println(CANNOT_WITHDRAW);
        }
    }
    public void calculateAmount(Scanner reader) throws NonExcistingBankAccountException {
        System.out.println(PROMPT_BANK_ACCOUNT);
        String account = reader.nextLine();

        System.out.println("Enter months for which you want to calculate the amount: ");
        int months = reader.nextInt();

        System.out.println(String.format("%.2f",bank.calculateAmount(account, months)));
    }
}