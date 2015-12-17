package elemental.dom;

import java.util.Iterator;

public class IterableNodeList implements Iterable<Node> {

	private final NodeList nodeList;

	public IterableNodeList(NodeList nodeList) {
		this.nodeList = nodeList;
	}
	
	public static IterableNodeList iterator(NodeList nodeList) {
		return new IterableNodeList(nodeList);
	}
	
	@Override
	public Iterator<Node> iterator() {
		return new Iterator<Node>() {
			int index = 0;
			int size = nodeList.getLength();
			@Override
			public boolean hasNext() {
				return index < size;
			}

			@Override
			public Node next() {
				return nodeList.item(index++);
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
}
