package com.bazhanova.logic.calculation;

import com.bazhanova.logic.Exceptions.CalculationByFunctionException;

public class CalculationByFunction {
    final int FAIL = 0;
    final int DELIMITER = 1;
    final int VARIABLE = 2;
    final int NUMBER = 3;

    final int SYNTAXERROR = 0;  //Syntax error type (1 * 2 3 + 1)
    final int UNBALPARENS = 1;  //  The number of open and closed brackets does not match
    final int NOEXP = 2;        //  Missing expression when running analyzer
    final int DIVBYZERO = 3;    //  Error devided by zero

    final String EOF = "\0"; // A lexeme that defines the end of an expression

    private String exp;     //  Link to a string with an expression
    private int explds;     //  Current index in expression
    private String token;   //  Saving the current token
    private int tokType;    //  Saving a token type


    public String toString(){
        return String.format("Exp = {0}\nexplds = {1}\nToken = {2}\nTokType = {3}", exp.toString(), explds,
                token.toString(), tokType);
    }

    private void getToken(){
        tokType = FAIL;
        token = "";

        if(explds == exp.length()){
            token = EOF;
            return;
        }
        while(explds < exp.length() && Character.isWhitespace(exp.charAt(explds)))
            ++ explds;
        if(explds == exp.length()){
            token = EOF;
            return;
        }
        if(isDelim(exp.charAt(explds))){
            token += exp.charAt(explds);
            explds++;
            tokType = DELIMITER;
        }
        else if(Character.isLetter(exp.charAt(explds))){
            while(!isDelim(exp.charAt(explds))){
                token += exp.charAt(explds);
                explds++;
                if(explds >= exp.length())
                    break;
            }
            tokType = VARIABLE;
        }
        else if (Character.isDigit(exp.charAt(explds))){
            while(!isDelim(exp.charAt(explds))){
                token += exp.charAt(explds);
                explds++;
                if(explds >= exp.length())
                    break;
            }
            tokType = NUMBER;
        }
        else {
            token = EOF;
        }
    }

    private boolean isDelim(char charAt) {
        if((" +-/*%^=()".indexOf(charAt)) == -1)
            return false;
        return true;
    }

    public String evaluate(String expstr) throws CalculationByFunctionException {
        double result;

        exp = expstr;
        explds = 0;
        getToken();

        if(token.equals(EOF))
            handleErr(NOEXP);

        result = evalExp2();

        if(!token.equals(EOF))
            handleErr(SYNTAXERROR);

        return Double.toString(result);
    }

    private double evalExp2() throws CalculationByFunctionException {

        char op;
        double result;
        double partialResult;
        result = evalExp3();
        while((op = token.charAt(0)) == '+' ||
                op == '-'){
            getToken();
            partialResult = evalExp3();
            switch(op){
                case '-':
                    result -= partialResult;
                    break;
                case '+':
                    result += partialResult;
                    break;
            }
        }
        return result;
    }

    private double evalExp3() throws CalculationByFunctionException {

        char op;
        double result;
        double partialResult;

        result = evalExp4();
        while((op = token.charAt(0)) == '*' ||
                op == '/' | op == '%'){
            getToken();
            partialResult = evalExp4();
            switch(op){
                case '*':
                    result *= partialResult;
                    break;
                case '/':
                    if(partialResult == 0.0)
                        handleErr(DIVBYZERO);
                    result /= partialResult;
                    break;
                case '%':
                    if(partialResult == 0.0)
                        handleErr(DIVBYZERO);
                    result %= partialResult;
                    break;
            }
        }
        return result;
    }

    private double evalExp4() throws CalculationByFunctionException {

        double result;
        double partialResult;
        double ex;
        int t;
        result = evalExp5();
        if(token.equals("^")){
            getToken();
            partialResult = evalExp4();
            ex = result;
            if(partialResult == 0.0){
                result = 1.0;
            }else
                for(t = (int)partialResult - 1; t >  0; t--)
                    result *= ex;
        }
        return result;
    }

    private double evalExp5() throws CalculationByFunctionException {
        double result;

        String op;
        op = " ";

        if((tokType == DELIMITER) && token.equals("+") ||
                token.equals("-")){
            op = token;
            getToken();
        }
        result = evalExp6();
        if(op.equals("-"))
            result =  -result;
        return result;
    }

    private double evalExp6() throws CalculationByFunctionException {
        double result;

        if(token.equals("(")){
            getToken();
            result = evalExp2();
            if(!token.equals(")"))
                handleErr(UNBALPARENS);
            getToken();
        }
        else
            result = atom();
        return result;
    }

    private double atom()   throws CalculationByFunctionException {

        double result = 0.0;
        switch(tokType){
            case NUMBER:
                try{
                    result = Double.parseDouble(token);
                }
                catch(NumberFormatException exc){
                    handleErr(SYNTAXERROR);
                }
                getToken();

                break;
            default:
                handleErr(SYNTAXERROR);
                break;
        }
        return result;
    }

    private void handleErr(int nOEXP2) throws CalculationByFunctionException {

        String[] err  = {
                "Syntax error",
                "Unbalanced Parentheses",
                "No Expression Present",
                "Division by zero"
        };
        throw new CalculationByFunctionException(err[nOEXP2]);
    }
}