package secs.secs2;

import java.math.BigInteger;

abstract public class Secs2BigInteger extends Secs2Number<BigInteger> {

	public Secs2BigInteger() {
		super();
	}
	
	@Override
	protected BigInteger getBigInteger(int index) throws Secs2Exception {
		
		try {
			return values().get(index);
		}
		catch ( IndexOutOfBoundsException e ) {
			throw new Secs2IndexOutOfBoundsException(e);
		}
	}

}
