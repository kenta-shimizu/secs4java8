package secs.secs2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Secs2List extends Secs2 {

	private static final Secs2Item secs2Item = Secs2Item.LIST;
	
	private static final String SPACE = "  ";
	private static final String BR = System.lineSeparator();
	

	private final List<Secs2> values;
	
	public Secs2List() {
		super();
		
		this.values = Collections.emptyList();
	}

	public Secs2List(Secs2... values){
		super();
		
		this.values = Stream.of(values).collect(Collectors.toList());
	}

	public Secs2List(List<? extends Secs2> values) {
		super();
		
		this.values = new ArrayList<>(values);
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
	public byte[] secs2Bytes() throws Secs2Exception {
		
		try (
				ByteArrayOutputStream st = new ByteArrayOutputStream();
				) {
			
			st.write(createHeadBytes(secs2Item, size()));
			for ( Secs2 ss : values ) {
				st.write(ss.secs2Bytes());
			}
			
			return st.toByteArray();
		}
		catch ( IOException e ) {
			throw new Secs2Exception(e);
		}
	}

	@Override
	protected Secs2 get( LinkedList<Integer> list ) throws Secs2Exception {
		
		if ( list.isEmpty() ) {
			
			return get();
			
		} else {
			
			try {
				int index = list.removeFirst();
				return values.get(index).get(list);
			}
			catch ( IndexOutOfBoundsException e ) {
				throw new Secs2IndexOutOfBoundsException(e);
			}
		}
	}

	@Override
	public Secs2Item secs2Item() {
		return secs2Item;
	}
	
	@Override
	public String toString() {
		return lineStrings("").stream().collect(Collectors.joining(BR));
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
	protected String parsedJsonValue() {
		
		return stream().map(ss -> ss.parseToJson())
				.collect(Collectors.joining(",", "[", "]"));
	}
	
	@Override
	protected String toStringValue() {
		return "";
	}

}
