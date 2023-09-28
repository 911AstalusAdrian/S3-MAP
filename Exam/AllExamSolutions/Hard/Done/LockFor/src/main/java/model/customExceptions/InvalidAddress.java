package model.customExceptions;

public class InvalidAddress extends RuntimeException {
    public InvalidAddress(String address) {
        super( address + " is not a valid table address");
    }
}
