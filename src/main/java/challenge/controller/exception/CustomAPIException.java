package challenge.controller.exception;

public class CustomAPIException {
 
    private String errorMessage;
 
    public CustomAPIException(String errorMessage){
        this.errorMessage = errorMessage;
    }
 
    public String getErrorMessage() {
        return errorMessage;
    }
 
}