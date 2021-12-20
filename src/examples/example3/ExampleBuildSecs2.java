package example3;

import java.util.Arrays;
import java.util.List;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.SmlDataItemParser;
import com.shimizukenta.secs.sml.SmlParseException;

/**
 * Example-3.
 * 
 * <p>
 * This is building SECS-II examples.
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public class ExampleBuildSecs2 {

	public ExampleBuildSecs2() {
		/* Nothing */
	}

	public static void main(String[] args) {
		
		{
			/* build <A "ASCII"> */
			Secs2 ss = Secs2.ascii("ASCII");
			
			System.out.println("build <A \"ASCII\">");
			System.out.println(ss);
			System.out.println();
		}
		
		{
			/* build <B 0x0> */
			Secs2 ss = Secs2.binary((byte)0x0);
			
			System.out.println("build <B 0x0>");
			System.out.println(ss);
			System.out.println();
		}
		
		{
			/* build <I4 100> */
			Secs2 ss = Secs2.int4(100);
			
			System.out.println("build <I4 100>");
			System.out.println(ss);
			System.out.println();
		}
		
		{
			/* build <U4 100 200 300> */
			Secs2 ss = Secs2.uint4(100, 200, 300);
			
			System.out.println("build <U4 100 200 300>");
			System.out.println(ss);
			System.out.println();
		}
		
		{
			/* build <F4 1.23> */
			Secs2 ss = Secs2.float4(1.23F);
			
			System.out.println("build <F4 1.23>");
			System.out.println(ss);
			System.out.println();
		}
		
		{
			/* build <BOOLEAN TRUE> */
			Secs2 ss = Secs2.bool(true);
			
			System.out.println("build <BOOLEAN TRUE>");
			System.out.println(ss);
			System.out.println();
		}
		
		{
			/* build <L[0] > */
			Secs2 ss = Secs2.list();
			
			System.out.println("build <L[0] >");
			System.out.println(ss);
			System.out.println();
		}
		
		{
			/*
			 * build-pattern-1
			 * <L
			 *   <B 0x0>
			 *   <I4 1001>
			 *   <A "MESSAGE">
			 * >
			 */
			Secs2 ss = Secs2.list(
					Secs2.binary((byte)0x0)
					, Secs2.int4(1001)
					, Secs2.ascii("MESSAGE")
					);
			
			System.out.println("build <L ...>");
			System.out.println(ss);
			System.out.println();
		}
		
		{
			/*
			 * build-pattern-2-use-LIST
			 * <L
			 *   <B 0x0>
			 *   <I4 1001>
			 *   <A "MESSAGE">
			 * >
			 */
			
			List<Secs2> ll = Arrays.asList(
					Secs2.binary((byte)0x0)
					, Secs2.int4(1001)
					, Secs2.ascii("MESSAGE")
					);
			
			Secs2 ss = Secs2.list(ll);
			
			System.out.println("build <L list>");
			System.out.println(ss);
			System.out.println();
		}
		
		{
			/* build empty */
			Secs2 ss = Secs2.empty();
			
			System.out.println("build empty");
			System.out.println(ss);
			System.out.println();
		}
		
		{
			try {
				final SmlDataItemParser parser = SmlDataItemParser.newInstance();
				
				Secs2 ss = parser.parse("<L <B 0x81><I4 1001><A \"ON FIRE\">>");
				
				System.out.println("build by SML");
				System.out.println(ss);
				System.out.println();
			}
			catch ( SmlParseException e ) {
				e.printStackTrace();
			}
			
		}

	}

}
