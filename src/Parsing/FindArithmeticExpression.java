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
        String middle="";
        matcher = regexExpressionWithBrackets.matcher(text);
        while(matcher.find())
        {
            middle = matcher.group();
            if (middle.charAt(0) == '(')
                exprs.add(middle);
            else
                exprs.add(middle.substring(1));
        }
        if (exprs.isEmpty())
            return null;
        return exprs;
    }

    public ArrayList<String> getExpression()
    {
        ArrayList<String> exprs = new ArrayList<String>();
        matcher = regexExpression.matcher(text);
        while(matcher.find())
            exprs.add(matcher.group());
        if (exprs.isEmpty())
            return null;
        return exprs;
    }

    public String replaceExpressionWithBrackets(String what, String change)
    {
        text = text.replace(what, change);
        return text;
    }

    public String replaceExpression (String what, String change)
    {
        text = text.replace(what, change);
        return text;
    }

    public String deleteBrackets(String expr)
    {
        return expr.replaceAll("\\(","").replaceAll("\\)","");
    }

    public boolean isNumber(String line)
    {
        Pattern pattern = Pattern.compile("^(\\d+)*[.]?\\d+$");
        matcher = pattern.matcher(line);
        return matcher.find();
    }
}