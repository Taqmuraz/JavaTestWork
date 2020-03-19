import java.util.HashMap;
import java.util.Scanner;

public class Program
{
	public static void main (String[] args)
	{
		ComputeProvider computer = new ComputeProvider();
		Scanner in = new Scanner(System.in);
		while (true)
		{
			String a_string = in.next().toUpperCase();
			String action_string = in.next();
			String b_string = in.next().toUpperCase();
			
			try
			{
				IntParser parser = selectParser (a_string, b_string);
				int a = parser.parse(a_string);
				int b = parser.parse(b_string);
				int resoult = computer.Compute(a, b, action_string);
				
				System.out.println(parser.toString(resoult));
				
			} catch (Exception ex)
			{
				System.out.println("Error while computing : " + ex.toString());
			}
		}
	}
	
	static IntParser selectParser (String a, String b) throws Exception
	{
		IntParser arabic = new ArabicParser ();
		IntParser roman = new RomanParser();
		
		if (arabic.canParse(a) && arabic.canParse(b)) return arabic;
		else if (roman.canParse(a) && roman.canParse(b)) return roman;
		else throw new Exception("Can't parse values");
	}
}
class ComputeProvider
{
	interface ComputeAction
	{
		int Compute (int a, int b);
	}
	
	static HashMap<String, ComputeAction> actions;
	
	static
	{
		actions = new HashMap<String, ComputeAction>();
		
		actions.put("+", (int a, int b) -> a + b);
		actions.put("-", (int a, int b) -> a - b);
		actions.put("*", (int a, int b) -> a * b);
		actions.put("/", (int a, int b) -> a / b);
	}
	
	
	public int Compute (int a, int b, String action) throws Exception
	{
		if (actions.containsKey(action)) return actions.get(action).Compute(a,  b);
		else throw new Exception ("There are no " + action + " action");
	}
}
class ArabicParser implements IntParser
{
	public int parse (String value)
	{
		return Integer.parseInt(value);
	}
	public String toString (Integer value)
	{
		return value.toString();
	}
	public boolean canParse (String value)
	{
		try
		{
			Integer.parseInt(value);
			return true;
		} catch (Throwable t)
		{
			return false;
		}
	}
}
class RomanParser implements IntParser
{
	static HashMap<String, Integer> values;
	static HashMap<Integer, String> strings;
	
	static
	{
		values = new HashMap<String,Integer>();
		strings = new HashMap<Integer, String>();
		
		String[] ss =
		{
			"I",
			"II",
			"III",
			"IV",
			"V",
			"VI",
			"VII",
			"VIII",
			"IX",
			"X"
		};
		
		for (int i = 0; i < 10; i++)
		{
			values.put(ss[i], i + 1);
			strings.put(i + 1, ss[i]);
		}
	}
	
	public boolean canParse (String value)
	{
		return values.containsKey(value);
	}
	
	public int parse (String value)
	{
		return values.get(value);
	}
	public String toString (Integer value)
	{
		int c = value / 100;
		value %= 100;
		
		int l = value / 50;
		value %= 50;
		
		int x = value / 10;
		value %= 10;
		
		String resoult = new String();
		for (int i = 0; i < c; i++) resoult += "C";
		for (int i = 0; i < l; i++) resoult += "L";
		for (int i = 0; i < x; i++) resoult += "X";
		
		if (strings.containsKey(value)) resoult += strings.get(value);
		
		return resoult;
	}
}
interface IntParser
{
	int parse (String value);
	String toString (Integer value);
	boolean canParse (String value);
}
