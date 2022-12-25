package Parsing;

import Exceptions.CalculationByFunctionException;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class CalculationRun {
    public String CalculationRunByLibrary(String text)
    {
        int count=0;
        FindArithmeticExpression arithm = new FindArithmeticExpression(text);
        CalculationByLibrary lib = new CalculationByLibrary();
        ArrayList<String> lines = arithm.getExpressionWithBrackets();
        String result="";
        String middleres= "";
        String line="";
        while (lines != null)
        {
            for (int i=0; i<lines.size(); i++)
            {
                line = lines.get(i);
                middleres= lib.Calculate(arithm.deleteBrackets(line));
                result = arithm.replaceExpressionWithBrackets(line, middleres);
            }

            lines = arithm.getExpressionWithBrackets();
        }
        lines = arithm.getExpression();
        while (lines != null)
        {
            for (int i=0; i< lines.size(); i++)
            {
                line =lines.get(i);
                if (arithm.isNumber(line))
                    count++;
                middleres = lib.Calculate(line);
                result= arithm.replaceExpression(line, middleres);
            }
            if (count== lines.size())
                return result;
            count=0;
            lines = arithm.getExpression();
        }
        return result;
    }
    public String CalculationRunByFunction(String text) throws CalculationByFunctionException {
        int count=0;
        FindArithmeticExpression arithm = new FindArithmeticExpression(text);
        CalculationByFunction lib = new CalculationByFunction();
        ArrayList<String> lines = arithm.getExpressionWithBrackets();
        String result="";
        String middleres= "";
        String line="";
        while (lines != null)
        {
            for (int i=0; i<lines.size(); i++)
            {
                line = lines.get(i);
                middleres= lib.evaluate(arithm.deleteBrackets(line));
                result = arithm.replaceExpressionWithBrackets(line, middleres);
            }

            lines = arithm.getExpressionWithBrackets();
        }
        lines = arithm.getExpression();
        while (lines != null)
        {
            for (int i=0; i< lines.size(); i++)
            {
                line =lines.get(i);
                if (arithm.isNumber(line))
                    count++;
                middleres = lib.evaluate(line);
                result= arithm.replaceExpression(line, middleres);
            }
            if (count==lines.size())
                return result;
            count=0;
            lines = arithm.getExpression();
        }
        return result;
    }
}