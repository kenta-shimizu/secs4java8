package example3;

import java.util.Arrays;
import java.util.List;

import com.shimizukenta.secs.secs2.AbstractSecs2;

public class ExampleBuildSecs2 {

	public ExampleBuildSecs2() {
		/* Nothing */
	}

	public static void main(String[] args) {
		
		{
			/* build <A "ASCII"> */
			AbstractSecs2 ss = AbstractSecs2.ascii("ASCII");
			
			System.out.println("build <A \"ASCII\">");
			System.out.println(ss);
			System.out.println();
		}
		
		{
			/* build <B 0x0> */
			AbstractSecs2 ss = AbstractSecs2.binary((byte)0x0);
			
			System.out.println("build <B 0x0>");
			System.out.println(ss);
			System.out.println();
		}
		
		{
			/* build <I4 100> */
			AbstractSecs2 ss = AbstractSecs2.int4(100);
			
			System.out.println("build <I4 100>");
			System.out.println(ss);
			System.out.println();
		}
		
		{
			/* build <U4 100 200 300> */
			AbstractSecs2 ss = AbstractSecs2.uint4(100, 200, 300);
			
			System.out.println("build <U4 100 200 300>");
			System.out.println(ss);
			System.out.println();
		}
		
		{
			/* build <F4 1.23> */
			AbstractSecs2 ss = AbstractSecs2.float4(1.23F);
			
			System.out.println("build <F4 1.23>");
			System.out.println(ss);
			System.out.println();
		}
		
		{
			/* build <BOOLEAN TRUE> */
			AbstractSecs2 ss = AbstractSecs2.bool(true);
			
			System.out.println("build <BOOLEAN TRUE>");
			System.out.println(ss);
			System.out.println();
		}
		
		{
			/* build <L[0] > */
			AbstractSecs2 ss = AbstractSecs2.list();
			
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
			AbstractSecs2 ss = AbstractSecs2.list(
					AbstractSecs2.binary((byte)0x0)
					, AbstractSecs2.int4(1001)
					, AbstractSecs2.ascii("MESSAGE")
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
			
			List<AbstractSecs2> ll = Arrays.asList(
					AbstractSecs2.binary((byte)0x0)
					, AbstractSecs2.int4(1001)
					, AbstractSecs2.ascii("MESSAGE")
					);
			
			AbstractSecs2 ss = AbstractSecs2.list(ll);
			
			System.out.println("build <L list>");
			System.out.println(ss);
			System.out.println();
		}
		
		{
			/* build empty */
			AbstractSecs2 ss = AbstractSecs2.empty();
			
			System.out.println("build empty");
			System.out.println(ss);
			System.out.println();
		}

	}

}
