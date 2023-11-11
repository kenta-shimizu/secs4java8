package com.shimizukenta.secs.secs2.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;
import com.shimizukenta.secs.secs2.Secs2IndexOutOfBoundsException;
import com.shimizukenta.secs.secs2.Secs2Item;
import com.shimizukenta.secs.secs2.Secs2LengthByteOutOfRangeException;

public class Secs2List extends AbstractSecs2 {
	
	private static final long serialVersionUID = -2380901931225887256L;
	
	private static final Secs2Item secs2Item = Secs2Item.LIST;
	
	private static final String SPACE = "  ";
	private static final String BR = System.lineSeparator();
	

	private final List<Secs2> values;
	private String proxyToString;
	private String proxyToJson;
	
	public Secs2List() {
		super();
		
		this.values = Collections.emptyList();
		this.proxyToString = null;
		this.proxyToJson = null;
	}

	public Secs2List(Secs2... values){
		super();
		
		if (values.length > 0x00FFFFFF) {
			throw new Secs2LengthByteOutOfRangeException();
		}
		
		this.values = Arrays.asList(values);
		this.proxyToString = null;
		this.proxyToJson = null;
	}

	public Secs2List(List<? extends Secs2> values) {
		super();
		
		if (values.size() > 0x00FFFFFF) {
			throw new Secs2LengthByteOutOfRangeException();
		}
		
		this.values = new ArrayList<>(values);
		this.proxyToString = null;
		this.proxyToJson = null;
	}

	@Override
	public int size() {
		return values.size();
	}
	
	@Override
	public Iterator<Secs2> iterator() {
		return values.iterator();
	}
	
	@Override
	public Stream<Secs2> stream() {
		return values.stream();
	}
	
	@Override
	protected void putBytesPack(Secs2BytesListBuilder builder) {
		
		this.putHeaderBytesToBytesPack(builder, size());
		
		for ( Secs2 ss : values ) {
			
			List<byte[]> bss = ss.getBytesList(1024);
			
			for (byte[] bs : bss ) {
				builder.put(bs);
			}
		}
	}
	
	@Override
	protected AbstractSecs2 get(LinkedList<Integer> list) throws Secs2Exception {
		
		if ( list.isEmpty() ) {
			
			return this;
			
		} else {
			
			try {
				int index = list.removeFirst();
				Secs2 ss = values.get(index);
				
				if ( ss instanceof AbstractSecs2 ) {
					
					return ((AbstractSecs2)ss).get(list);
					
				} else {
					
					throw new Secs2Exception("cast failed");
				}
			}
			catch ( IndexOutOfBoundsException e ) {
				throw new Secs2IndexOutOfBoundsException(e);
			}
		}
	}
	
	@Override
	protected Optional<AbstractSecs2> optional(LinkedList<Integer> list) {
		
		if ( list.isEmpty() ) {
			
			return Optional.of(this);
			
		} else {
			
			int index = list.removeFirst();
			
			if ( index >= 0 && index < size() ) {
				
				Secs2 ss = values.get(index);
				
				if ( ss instanceof AbstractSecs2 ) {
					
					return ((AbstractSecs2)ss).optional(list);
				}
			}
			
			return Optional.empty();
		}
	}


	@Override
	public Secs2Item secs2Item() {
		return secs2Item;
	}
	
	@Override
	public String toString() {
		synchronized ( this ) {
			if ( this.proxyToString == null ) {
				this.proxyToString = lineStrings("").stream().collect(Collectors.joining(BR));
			}
			return this.proxyToString;
		}
	}
	
	private List<String> lineStrings(String space) {
		
		final List<String> lines = new ArrayList<>();
		
		lines.add(space + "<" + secs2Item().symbol() + " [" + size() + "]");
		
		values.stream()
		.map(s -> {
			
			String ss = space + SPACE;
			
			if ( s instanceof Secs2List ) {
				
				return ((Secs2List)s).lineStrings(ss).stream()
						.collect(Collectors.joining(BR));
				
			} else {
				
				return ss + s.toString();
			}
		})
		.forEach(lines::add);
		
		lines.add(space + ">");
		
		return lines;
	}
	
	@Override
	public String toJson() {
		synchronized ( this ) {
			if ( this.proxyToJson == null ) {
				this.proxyToJson = super.toJson();
			}
			return this.proxyToJson;
		}
	}
	
	@Override
	protected String toJsonValue() {
		
		return stream().map(ss -> ss.toJson())
				.collect(Collectors.joining(",", "[", "]"));
	}
	
	@Override
	protected String toStringValue() {
		return "";
	}

}
