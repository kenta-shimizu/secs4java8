package com.shimizukenta.secs.sml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SmlMessageTest {

	private static void assertSmlParseFailed(CharSequence sml) {
		try {
			SmlMessage.of(sml);
			fail("not reach");
		}
		catch (SmlParseException e) {
			/* success */
		}

	}
	
	@Test
	@DisplayName("SmlMessage of")
	void testBuild() {
		
		try {
			{
				SmlMessage sm = SmlMessage.of("S1F13 W.");
				
				assertEquals(sm.getStream(), 1);
				assertEquals(sm.getFunction(), 13);
				assertEquals(sm.wbit(), true);
				assertEquals(sm.secs2().isEmpty(), true);
			}
			{
				SmlMessage sm = SmlMessage.of("S2F26 <L>.");
				
				assertEquals(sm.getStream(), 2);
				assertEquals(sm.getFunction(), 26);
				assertEquals(sm.wbit(), false);
				assertEquals(sm.secs2().isEmpty(), false);
			}
		}
		catch (SmlParseException e) {
			fail(e);
		}
	}
	
	@Test
	@DisplayName("SmlMessage#of blank")
	void testOfBlank() {
		assertSmlParseFailed("");
	}
	
	@Test
	@DisplayName("SmlMessage#of no-period")
	void testOfNoPeriod() {
		assertSmlParseFailed("S1F1W");
	}
	
	@Test
	@DisplayName("SmlMessage#of Out-of-Range")
	void testOutOfRange() {
		assertSmlParseFailed("S128F0.");
		assertSmlParseFailed("S0F256.");
		assertSmlParseFailed("S1234F0.");
		assertSmlParseFailed("S0F1234.");
	}
	
}
