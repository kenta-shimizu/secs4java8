package example8;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.gem.ACKC5;
import com.shimizukenta.secs.gem.ACKC6;
import com.shimizukenta.secs.gem.CMDA;
import com.shimizukenta.secs.gem.COMMACK;
import com.shimizukenta.secs.gem.ONLACK;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicator;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;
import com.shimizukenta.secs.util.AbstractSecsCommunicatorEntity;
import com.shimizukenta.secs.util.SecsCommunicatorEntity;

/**
 * SecsCommunicatorEntity example.
 * 
 * <p>
 * Equip Entity example is create-instance and add-listeners.<br />
 * Host Entity example is extend `AbstractSecsCommunicatorEntity` class.<br />
 * </p>
 * <p>
 * Host entity sequence.<br />
 * <ol>
 * <li>Trigger COMMUNICATED, Send S1F17 to try-online.</li>
 * <li>Triggee ONLINED, Send S2F21 to START.</li>
 * <li>Trigger ENDED, Send S1F15 to try-offline.</li>
 * </ol>
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public class EntityUtility {

	public EntityUtility() {
		/* Nothing */
	}
	
	private static final int sessionId = 10;
	private static final SocketAddress addr = new InetSocketAddress("127.0.0.1", 5000);
	
	private static final ExecutorService execServ = Executors.newCachedThreadPool(r -> {
		Thread th = new Thread(r);
		th.setDaemon(false);
		return th;
	});
	
	public static void main(String[] args) {
		
		try {
			
			HsmsSsCommunicatorConfig equipConfig = new HsmsSsCommunicatorConfig();
			equipConfig.connectionMode(HsmsConnectionMode.PASSIVE);
			equipConfig.isEquip(true);
			equipConfig.sessionId(sessionId);
			equipConfig.socketAddress(addr);
			equipConfig.timeout().t3(45.0F);
			equipConfig.timeout().t6( 5.0F);
			equipConfig.timeout().t7(10.0F);
			equipConfig.timeout().t8( 5.0F);
			equipConfig.logSubjectHeader("Equip: ");
			
			HsmsSsCommunicatorConfig hostConfig = new HsmsSsCommunicatorConfig();
			hostConfig.connectionMode(HsmsConnectionMode.ACTIVE);
			hostConfig.isEquip(false);
			hostConfig.sessionId(sessionId);
			hostConfig.socketAddress(addr);
			hostConfig.timeout().t3(45.0F);
			hostConfig.timeout().t5(10.0F);
			hostConfig.timeout().t6( 5.0F);
			hostConfig.timeout().t8( 5.0F);
			hostConfig.logSubjectHeader("Host: ");
			
			try (
					SecsCommunicator equipComm = HsmsSsCommunicator.newInstance(equipConfig);
					) {
				
				equipComm.addSecsLogListener(EntityUtility::echo);
				
				try (
						SecsCommunicatorEntity equipEntity = buildEquipEntity(equipComm);
						) {
					
					equipEntity.open();
					
					try (
							SecsCommunicator hostComm = HsmsSsCommunicator.newInstance(hostConfig);
							) {
						
						hostComm.addSecsLogListener(EntityUtility::echo);
						
						try (
								SecsCommunicatorEntity hostEntity = new HostSecsCommunicatorEntity(hostComm);
								) {
							
							hostEntity.open();
							
							synchronized ( EntityUtility.class ) {
								EntityUtility.class.wait();
							}
						}
						catch ( InterruptedException ignore ) {
						}
					}
				}
			}
		}
		catch ( Throwable t ) {
			echo(t);
		}
		finally {
			
			try {
				execServ.shutdown();
				if ( ! execServ.awaitTermination(1L, TimeUnit.MILLISECONDS) ) {
					execServ.shutdownNow();
					if ( ! execServ.awaitTermination(5L, TimeUnit.SECONDS) ) {
						echo("ExecutorService#shutdown failed.");
					}
				}
			}
			catch (InterruptedException giveup ) {
			}
		}
	}
	
	private enum Trigger {
		
		UNKNOWN(-1, ""),
		
		COMMUNICATED(-1, "COMMUNICATED"),
		DISCOMMUNICATED(-1, "DISCOMMUNICATED"),
		
		ONLINED(1000, "ONLINED"),
		OFFLINED(2000, "OFFLINED"),
		
		STARTED(11000, "STARTED"),
		STOPPED(12000, "STOPPED"),
		ENDED(13000, "ENDED"),
		;
		
		private final int ceid;
		private final String desc;
		
		private Trigger(int ceid, String desc) {
			this.ceid = ceid;
			this.desc = desc;
		}
		
		public int ceid() {
			return this.ceid;
		}
		
		public String description() {
			return this.desc;
		}
		
		public static Trigger getByCEID(int ceid) {
			for ( Trigger a : values() ) {
				if ( a.ceid >= 0 ) {
					if ( a.ceid == ceid ) {
						return a;
					}
				}
			}
			return UNKNOWN;
		}
		
		@Override
		public String toString() {
			return "{CEID: " + this.ceid() + ", DESC: \"" + this.description() + "\"}";
		}
	}
	
	private static SecsCommunicatorEntity buildEquipEntity(SecsCommunicator communicator) {
		
		final SecsCommunicatorEntity entity = SecsCommunicatorEntity.newInstance(communicator);
		
		entity.setReplySxF0(true);
		entity.setSendS9Fy(true);
		
		entity.addMessageReceiveListener(1, 13, (msg, comm) -> {
			if ( msg.wbit() ) {
				try {
					comm.gem().s1f14(msg, COMMACK.OK);
				}
				catch ( SecsException ignore ) {
				}
			}
		});
		
		entity.addMessageReceiveListener(1, 15, (msg, comm) -> {
			if ( msg.wbit() ) {
				try {
					comm.gem().s1f16(msg);
				}
				catch ( SecsException ignore ) {
				}
			}
		});
		
		entity.addMessageReceiveListener(1, 17, (msg, comm) -> {
			if ( msg.wbit() ) {				
				try {
					comm.gem().s1f18(msg, ONLACK.OK);
				}
				catch ( SecsException ignore ) {
				}
				
				executeS6F11(entity, Trigger.ONLINED, 1000L);
			}
		});
		
		entity.addMessageReceiveListener(2, 21, (msg, comm) -> {
			if ( msg.wbit() ) {
				final String cmd = msg.secs2().getAscii();
				
				try {
					if ( cmd.equals("START") ) {
						
						comm.gem().s2f22(msg, CMDA.OK);
						
						execServ.execute(() -> {
							try {
								TimeUnit.MILLISECONDS.sleep(200L);
								executeS6F11(entity, Trigger.STARTED);
								TimeUnit.MILLISECONDS.sleep(3000L);
								executeS6F11(entity, Trigger.ENDED);
							}
							catch ( InterruptedException ignore ) {
							}
						});
						
					} else if (cmd.equals("STOP") ) {
						
						comm.gem().s2f22(msg, CMDA.OK);
						executeS6F11(entity, Trigger.STOPPED, 500L);
						
					} else {
						
						comm.gem().s2f22(msg, CMDA.CommandDoesNotExist);
					}
				}
				catch ( SecsException ignore ) {
				}
			}
		});
		
		return entity;
	}
	
	private static void executeS6F11(SecsCommunicatorEntity entity, Trigger trigger) throws InterruptedException {
		
		try {
			entity.send(
					6, 11, true,
					Secs2.list(
							entity.gem().autoDataId(),
							Secs2.uint2(trigger.ceid()),
							Secs2.list()
							)
					);
		}
		catch ( SecsException ignore ) {
		}

	}
	
	private static void executeS6F11(SecsCommunicatorEntity entity, Trigger trigger, long delayMilliSeconds) {
		
		execServ.execute(() -> {
			
			try {
				TimeUnit.MILLISECONDS.sleep(delayMilliSeconds);
				executeS6F11(entity, trigger);
			}
			catch ( InterruptedException ignore ) {
			}
		});;
	}
	
	private static class HostSecsCommunicatorEntity extends AbstractSecsCommunicatorEntity {
		
		public HostSecsCommunicatorEntity(SecsCommunicator communicator) {
			super(communicator);
			this.setup();
		}
		
		private final BlockingQueue<Trigger> queue = new LinkedBlockingQueue<>();
		
		private void setup() {
			
			this.setReplySxF0(false);
			this.setSendS9Fy(false);
			
			this.addCommunicatableStateChangeListener((communicatable, comm) -> {
				
				try {
					if ( communicatable ) {
						this.queue.put(Trigger.COMMUNICATED);
					} else {
						this.queue.put(Trigger.DISCOMMUNICATED);
					}
				}
				catch ( InterruptedException ignore ) {
				}
			});
			
			this.addMessageReceiveListener(1, 13, (msg, comm) -> {
				if ( msg.wbit() ) {
					try {
						comm.gem().s1f14(msg, COMMACK.OK);
					}
					catch ( SecsException ignore ) {
					}
				}
			});
			
			this.addMessageReceiveListener(5, 1, (msg, comm) -> {
				if ( msg.wbit() ) {
					try {
						comm.gem().s5f2(msg, ACKC5.OK);
					}
					catch ( SecsException ignore ) {
					}
				}
			});
			
			this.addMessageReceiveListener(6, 11, (msg, comm) -> {
				
				if ( msg.wbit() ) {
					try {
						comm.gem().s6f12(msg, ACKC6.OK);
					}
					catch ( SecsException ignore ) {
					}
				}
				
				int ceid = msg.secs2().getInt(1, 0);
				Trigger t = Trigger.getByCEID(ceid);
				this.queue.put(t);
			});
			
			execServ.execute(() -> {
				
				try {
					for ( ;; ) {
						Trigger t = this.queue.take();
						echo(t);
						this.recvTrigger(t);
					}
				}
				catch ( InterruptedException ignore ) {
				}
			});
		}
		
		private void recvTrigger(Trigger trigger) throws InterruptedException {
			
			switch ( trigger ) {
			case COMMUNICATED: {
				this.tryOnline();
				break;
			}
			case ONLINED: {
				this.tryStart();
				break;
			}
			case ENDED: {
				this.tryOffline();
				break;
			}
			default: {
				/* Nothing */
			}
			}
		}
		
		private void tryOnline() throws InterruptedException {
			
			TimeUnit.MILLISECONDS.sleep(200L);
			
			try {
				if ( this.gem().s1f17() == ONLACK.OK ) {
					return;
				}
			}
			catch ( SecsException | Secs2Exception ignore ) {
			}
			
			this.tryOffline();
		}
		
		private void tryStart() throws InterruptedException {
			
			TimeUnit.MILLISECONDS.sleep(200L);
			
			try {
				Secs2 ss = this.send(2, 21, true, Secs2.ascii("START"))
						.filter(r -> r.getStream() == 2)
						.filter(r -> r.getFunction() == 22)
						.map(r -> r.secs2())
						.orElse(null);
				
				if ( ss != null ) {
					if ( CMDA.get(ss) == CMDA.OK ) {
						return;
					}
				}
			}
			catch ( SecsException | Secs2Exception ignore ) {
			}
			
			this.tryOffline();
		}
		
		private void tryOffline() throws InterruptedException {
			
			try {
				this.gem().s1f15();
				TimeUnit.MILLISECONDS.sleep(100L);
			}
			catch ( SecsException | Secs2Exception ignore ) {
			}
			finally {
				
				synchronized ( EntityUtility.class ) {
					EntityUtility.class.notifyAll();
				}
			}
		}
	}
	
	private static Object syncStaticEcho = new Object();
	
	private static void echo(Object o) {
		
		synchronized ( syncStaticEcho ) {
			
			if ( o instanceof Throwable) {
				
				try (
						StringWriter sw = new StringWriter();
						) {
					
					try (
							PrintWriter pw = new PrintWriter(sw);
							) {
						
						((Throwable) o).printStackTrace(pw);
						pw.flush();
						
						System.out.println(sw.toString());
					}
				}
				catch ( IOException e ) {
					e.printStackTrace();
				}
				
			} else {
				
				System.out.println(o);
			}
			
			System.out.println();
		}
	}

}
