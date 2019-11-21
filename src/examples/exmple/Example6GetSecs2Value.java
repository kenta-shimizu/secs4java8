package exmple;

import secs.secs2.Secs2;
import secs.secs2.Secs2Exception;

public class Example6GetSecs2Value {

	public Example6GetSecs2Value() {
		/* Nothign */
	}

	public static void main(String[] args) {

		Secs2 ss = Secs2.list(
				Secs2.binary((byte)0xA)	/* 0 */
				, Secs2.ascii("MESSAGE-1")	/* 1 */
				, Secs2.bool(true)	/* 2 */
				, Secs2.list(	/* 3 */
						Secs2.list(	/* 3,0 */
								Secs2.ascii("KEY-1")	/* 3,0,0 */
								, Secs2.int4(100, 101, 102)	/* 3,0,1 */
								)
						, Secs2.list(	/* 3,1 */
								Secs2.ascii("KEY-2")	/* 3,1,0 */
								, Secs2.int4(200, 201, 202)	/* 3,1,1 */
								)
						, Secs2.list(	/* 3,2 */
								Secs2.ascii("KEY-3")	/* 3,2,0 */
								, Secs2.int4(300, 301, 302)	/* 3,2,1 */
								)
						)
				);
		
		System.out.println("# Example-SECS2");
		System.out.println(ss);
		System.out.println();
		
		try {
			
			System.out.println("# Get by index");
			System.out.println("getByte(0, 0):\t" + ss.getByte(0, 0));
			System.out.println("getAscii(1):\t" + ss.getAscii(1));
			System.out.println("getBoolean(2, 0):\t" + ss.getBoolean(2, 0));
			System.out.println("getAscii(3, 0, 0):\t" + ss.getAscii(3, 0, 0));
			System.out.println("getInt(3, 0, 1, 0):\t" + ss.getInt(3, 0 , 1, 0));
			System.out.println("getInt(3, 0, 1, 1):\t" + ss.getInt(3, 0 , 1, 1));
			System.out.println("getInt(3, 0, 1, 2):\t" + ss.getInt(3, 0 , 1, 2));
			System.out.println("getInt(3, 1, 1, 0):\t" + ss.getInt(3, 1 , 1, 0));
			System.out.println("getInt(3, 2, 1, 0):\t" + ss.getInt(3, 2 , 1, 0));
			Secs2 s3 = ss.get(3);
			System.out.println("get(3):\t" + s3);
			System.out.println();
			
			System.out.println("# Get Item-Type");
			System.out.println("get() item-type:\t" + ss.get().secs2Item());
			System.out.println("get(0) item-type:\t" + ss.get(0).secs2Item());
			System.out.println("get(1) item-type:\t" + ss.get(1).secs2Item());
			System.out.println("get(2) item-type:\t" + ss.get(2).secs2Item());
			System.out.println("get(3) item-type:\t" + ss.get(3).secs2Item());
			System.out.println("get(3, 0, 1) item-type:\t" + ss.get(3, 0, 1).secs2Item());
			System.out.println();
			
			System.out.println("# Get Size");
			System.out.println("get() size:\t" + ss.get().size());
			System.out.println("get(0) size:\t" + ss.get(0).size());
			System.out.println("get(1) size:\t" + ss.get(1).size());
			System.out.println("get(2) size:\t" + ss.get(2).size());
			System.out.println("get(3) size:\t" + ss.get(3).size());
			System.out.println("get(3, 0, 1) size:\t" + ss.get(3, 0, 1).size());
			System.out.println();
			
			{
				System.out.println("# Use for-each-loop, getAscii(3, x, 0)");
				
				for ( Secs2 s : s3 ) {
					System.out.println("getAscii(3, x, 0):\t" + s.getAscii(0));
				}
				
				System.out.println();
			}
			
			{
				System.out.println("# Use stream, getInt(3, x, 1, 0)");
				
				s3.stream()
				.map(s -> {
					try {
						return s.getInt(1, 0);
					}
					catch ( Secs2Exception e ) {
						throw new RuntimeException(e);
					}
				})
				.map(v -> "getInt(3, x, 1, 0):\t" + v)
				.forEach(System.out::println);
				
				System.out.println();
			}
		}
		catch ( Throwable t ) {
			t.printStackTrace();
		}
	}

}
