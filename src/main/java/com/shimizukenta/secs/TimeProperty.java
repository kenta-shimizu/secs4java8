package com.shimizukenta.secs;

public class TimeProperty extends AbstractProperty<Float> {

	public TimeProperty(float initial) {
		super(Float.valueOf(initial));
	}
	
	public float getSeconds() {
		return get().floatValue();
	}
	
	public long getMilliSeconds() {
		return (long)(get().floatValue() * 1000.0F);
	}

}
