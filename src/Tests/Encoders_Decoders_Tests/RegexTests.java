package Tests.Encoders_Decoders_Tests;

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

    
}
