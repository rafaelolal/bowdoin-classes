
public class LinkedListInnerClass<E> {
	
	
	// attempt one
	static private class StaticListNode {
		
		// verboten.  Can't access the (instance) generic type from a static nested class
		// this is reasonable -- you can't access generic types from static methods in the 
		// outer class either.
		private E data;
		
		private StaticListNode next;
		
	}
	
	// attempt two
	// Warning: The type parameter E is hiding the type E
	// this creates a new generic type E that is not the same 
	// as the generic type E from LinkedList
	// this will cause *much pain* if we insist on going this route
	// other warnings just say we're not using this, which is fair
	private class GenericListNode<E> {
		
		private E data;
		
		private GenericListNode next;
		
	}
	
	// attempt three
	// warnings just say we're not using this, which is fair.  We'll ignore those
	private class ListNode {
		
		// E is available, and inherited from our outer class
		// so, E within ListNode is exactly the same as E outside of ListNode, but within LinkedList
		// perfect!
		private E data;
		
		private ListNode next;
	}
	
	

}
