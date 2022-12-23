package Parsing;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindArithmeticExpression {
    final Pattern regexExpressionWithBrackets = Pattern.compile("(\\b||[-+*/])\\(([-+]?[0-9]*\\.?[0-9]+([-+*/]?([0-9]*\\.?[0-9]+))*)\\)");
    final Pattern regexExpression = Pattern.compile("\\b([-+]?[0-9]*\\.?[0-9]+([-+*/]?([0-9]*\\.?[0-9]+))*)\\b");
    private Matcher matcher;
    public String text;

    public FindArithmeticExpression()
    {
        text="";
    }
    public FindArithmeticExpression(String _text)
    {
        text=_text;
    }
    public ArrayList<String> getExpressionWithBrackets()
    {
        ArrayList<String> exprs = new ArrayList<String>();
        matcher = regexExpressionWithBrackets.matcher(text);
        while(matcher.find())
            exprs.add(matcher.group());
        if (exprs.isEmpty())
            return null;
        return exprs;
    }

    public ArrayList<String> getExpression()
    {
        ArrayList<String> exprs = new ArrayList<String>();
        matcher = regexExpression.matcher(text);
        while(matcher.find())
            exprs.add(matcher.group().substring(1));
        if (exprs.isEmpty())
            return null;
        return exprs;
    }

    public String replaceExpressionWithBrackets(String what, String change)
    {
        return text.replaceAll(what, change);
    }

    public String replaceExpression (String what, String change)
    {
        return text.replaceAll(what, change);
    }

    public String deleteBrackets(String expr)
    {
        return expr.replaceAll("\\(","").replaceAll("\\)","");
    }
}
