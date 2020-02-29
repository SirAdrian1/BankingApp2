package bank.exceptions;

public class NonExcistingBankAccountException extends Exception {
    public NonExcistingBankAccountException(){
        super();
    }
    public NonExcistingBankAccountException(String message){
        super(message);
    }
}
