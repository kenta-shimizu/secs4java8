package com.shimizukenta.secs.secs1ontcpip;

import static org.junit.jupiter.api.Assertions.*;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shimizukenta.secs.local.property.TimeoutAndUnit;

class Secs1OnTcpIpReceiverCommunicatorConfigTest {

	@Test
	@DisplayName("Secs1OnTcpIpReceiverCommunicatorConfig default")
	void testDefault() {
		Secs1OnTcpIpReceiverCommunicatorConfig config = new Secs1OnTcpIpReceiverCommunicatorConfig();
		
		// deviceId
		assertEquals(config.deviceId().intValue(), 10);
		
		// isEquip
		assertEquals(config.isEquip().booleanValue(), false);
		
		// isMaster
		assertEquals(config.isMaster().booleanValue(), true);
		
		// socketAddress
		assertEquals(config.socketAddress().isNull(), true);
		
		//timeout
		assertEqualsTimeoutAndUnit(config.timeout().t1().get(),  1000, TimeUnit.MILLISECONDS);
		assertEqualsTimeoutAndUnit(config.timeout().t2().get(), 15000, TimeUnit.MILLISECONDS);
		assertEqualsTimeoutAndUnit(config.timeout().t3().get(), 45000, TimeUnit.MILLISECONDS);
		assertEqualsTimeoutAndUnit(config.timeout().t4().get(), 45000, TimeUnit.MILLISECONDS);
		
		// retry
		assertEquals(config.retry().intValue(), 3);
		
		// reconnectSeconds
		assertEqualsTimeoutAndUnit(config.rebindSeconds().get(), 5000, TimeUnit.MILLISECONDS);
		
		// isCheckMessageBlockDeviceId
		assertEquals(config.isCheckMessageBlockDeviceId().booleanValue(), true);
	}
	
	@Test
	@DisplayName("Secs1OnTcpIpReceiverCommunicatorConfig set-and-get")
	void testSetAndGet() {
		Secs1OnTcpIpReceiverCommunicatorConfig config = new Secs1OnTcpIpReceiverCommunicatorConfig();
		
		// deviceId
		config.deviceId(20);
		assertEquals(config.deviceId().intValue(), 20);
		
		// isEquip
		config.isEquip(true);
		assertEquals(config.isEquip().booleanValue(), true);
		
		// isMaster
		config.isMaster(false);
		assertEquals(config.isMaster().booleanValue(), false);
		
		// socketAddress
		{
			SocketAddress a = new InetSocketAddress("127.0.0.1", 0);
			config.socketAddress(a);
			assertEquals(config.socketAddress().isNull(), false);
			assertEquals(config.socketAddress().get(), a);
		}
		
		//timeout
		config.timeout().t1(10.0F);
		assertEqualsTimeoutAndUnit(config.timeout().t1().get(), 10000, TimeUnit.MILLISECONDS);
		config.timeout().t2(20.0F);
		assertEqualsTimeoutAndUnit(config.timeout().t2().get(), 20000, TimeUnit.MILLISECONDS);
		config.timeout().t3(30.0F);
		assertEqualsTimeoutAndUnit(config.timeout().t3().get(), 30000, TimeUnit.MILLISECONDS);
		config.timeout().t4(40.0F);
		assertEqualsTimeoutAndUnit(config.timeout().t4().get(), 40000, TimeUnit.MILLISECONDS);
		
		// retry
		config.retry(5);
		assertEquals(config.retry().intValue(), 5);
		
		// reconnectSeconds
		config.rebindSeconds(20.0F);
		assertEqualsTimeoutAndUnit(config.rebindSeconds().get(), 20000, TimeUnit.MILLISECONDS);
		
		// isCheckMessageBlockDeviceId
		config.isCheckMessageBlockDeviceId(false);
		assertEquals(config.isCheckMessageBlockDeviceId().booleanValue(), false);
	}
	
	private static void assertEqualsTimeoutAndUnit(TimeoutAndUnit t, long timeout, TimeUnit unit) {
		assertEquals(t.timeout(), timeout);
		assertEquals(t.unit(), unit);
	}
	
}
