package Exceptions;

public class CalculationByFunctionException extends Exception {
    private static final long serialVersionUID = 1L;
    private String errStr;

    public CalculationByFunctionException(String errStr) {
        super();
        this.errStr = errStr;
    }

    public String toString(){
        return this.errStr;
    }
}