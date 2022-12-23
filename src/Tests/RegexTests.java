package Tests;

import Exceptions.CalculationByFunctionException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import Parsing.*;

import java.util.ArrayList;

public class RegexTests {

    @Test
    void FindBracketsTest()
    {
        String a = "(12+5*1)-1";
        ArrayList<String> res=new FindArithmeticExpression(a).getExpressionWithBrackets();
        for (int i=0; i<res.size(); i++)
        {
            assertEquals("12+5*1", new FindArithmeticExpression().deleteBrackets(res.get(i)));
        }
    }
    @Test
    void CalculateByLibraryTest()
    {
        String a ="12+5*2-1";
        String res = Double.toString(12+5*2-1);
        assertEquals(res, new CalculationByLibrary().Calculate(a));
    }
    @Test
    void CalculateByFunctionTest() throws CalculationByFunctionException {
        String a ="12+5*2-1";
        String res = Double.toString(12+5*2-1);
        assertEquals(res, new CalculationByFunction().evaluate(a));
    }
    @Test
    void CalculateRunByLibraryTest()
    {
        String a = "(12+5)*3-(1/2) e";
        double num = 1/2;
        double num1 = (12+5)*3-1.0/2.0;
        String res = Double.toString(num1) + " e";
        assertEquals(res, new CalculationRun().CalculationRunByLibrary(a));
    }

    @Test
    void CalculateRunByFunctionTest() throws CalculationByFunctionException {
        String a = "(12+5)*3-(1/2) e";
        double num = 1/2;
        double num1 = (12+5)*3-1.0/2.0;
        String res = Double.toString(num1) + " e";
        assertEquals(res, new CalculationRun().CalculationRunByFunction(a));
    }
}
