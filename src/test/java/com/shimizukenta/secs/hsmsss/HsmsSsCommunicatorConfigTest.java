package com.shimizukenta.secs.hsmsss;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.local.property.TimeoutAndUnit;

class HsmsSsCommunicatorConfigTest {

	@Test
	@DisplayName("HsmsSsCommunicatorConfig default")
	void testDefault() {
		
		HsmsSsCommunicatorConfig config = new HsmsSsCommunicatorConfig();
		
		// connectionMode
		assertEquals(config.connectionMode().get(), HsmsConnectionMode.PASSIVE);
		
		// isEquip
		assertEquals(config.isEquip().booleanValue(), false);
		
		// sessionId
		assertEquals(config.sessionId().intValue(), 10);
		
		// socketAddress
		assertEquals(config.socketAddress().isNull(), true);
		
		// timeout
		assertEqualsTimeoutAndUnit(config.timeout().t3().get(), 45000, TimeUnit.MILLISECONDS);
		assertEqualsTimeoutAndUnit(config.timeout().t5().get(), 10000, TimeUnit.MILLISECONDS);
		assertEqualsTimeoutAndUnit(config.timeout().t6().get(),  5000, TimeUnit.MILLISECONDS);
		assertEqualsTimeoutAndUnit(config.timeout().t7().get(), 10000, TimeUnit.MILLISECONDS);
		assertEqualsTimeoutAndUnit(config.timeout().t8().get(),  6000, TimeUnit.MILLISECONDS);
		
		// linktest
		assertEqualsTimeoutAndUnit(config.linktestTime().get(), 120000, TimeUnit.MILLISECONDS);
		assertEquals(config.doLinktest().booleanValue(), false);
		
		// rebindIfPassive
		assertEqualsTimeoutAndUnit(config.rebindIfPassiveTime().get(), 10000, TimeUnit.MILLISECONDS);
		assertEquals(config.doRebindIfPassive().booleanValue(), true);
	}
	
	@Test
	@DisplayName("HsmsSsCommunicatorConfig set-and-get")
	void testSetAndGet() {
		
		HsmsSsCommunicatorConfig config = new HsmsSsCommunicatorConfig();
		
		// connectionMode
		config.connectionMode(HsmsConnectionMode.ACTIVE);
		assertEquals(config.connectionMode().get(), HsmsConnectionMode.ACTIVE);
		
		// isEquip
		config.isEquip(true);
		assertEquals(config.isEquip().booleanValue(), true);
		
		// sessionId
		config.sessionId(20);
		assertEquals(config.sessionId().intValue(), 20);
		
		// socketAddress
		{
			SocketAddress a = new InetSocketAddress("127.0.0.1", 0);
			config.socketAddress(a);
			assertEquals(config.socketAddress().isNull(), false);
			assertEquals(config.socketAddress().get(), a);
		}
		
		// timeout
		config.timeout().t3(30.0F);
		assertEqualsTimeoutAndUnit(config.timeout().t3().get(),  30000, TimeUnit.MILLISECONDS);
		config.timeout().t5(50.0F);
		assertEqualsTimeoutAndUnit(config.timeout().t5().get(),  50000, TimeUnit.MILLISECONDS);
		config.timeout().t6(60.0F);
		assertEqualsTimeoutAndUnit(config.timeout().t6().get(),  60000, TimeUnit.MILLISECONDS);
		config.timeout().t7(70.0F);
		assertEqualsTimeoutAndUnit(config.timeout().t7().get(),  70000, TimeUnit.MILLISECONDS);
		config.timeout().t8(80.0F);
		assertEqualsTimeoutAndUnit(config.timeout().t8().get(),  80000, TimeUnit.MILLISECONDS);
		
		// linktest
		config.linktest(60.0F);
		assertEqualsTimeoutAndUnit(config.linktestTime().get(), 60000, TimeUnit.MILLISECONDS);
		assertEquals(config.doLinktest().booleanValue(), true);
		
		config.notLinktest();
		assertEquals(config.doLinktest().booleanValue(), false);
		
		// rebindIfPassive
		config.notRebindIfPassive();
		assertEquals(config.doRebindIfPassive().booleanValue(), false);
		
		config.rebindIfPassive(20.0F);
		assertEqualsTimeoutAndUnit(config.rebindIfPassiveTime().get(), 20000, TimeUnit.MILLISECONDS);
		assertEquals(config.doRebindIfPassive().booleanValue(), true);
	}
	
	private static void assertEqualsTimeoutAndUnit(TimeoutAndUnit t, long timeout, TimeUnit unit) {
		assertEquals(t.timeout(), timeout);
		assertEquals(t.unit(), unit);
	}

}
