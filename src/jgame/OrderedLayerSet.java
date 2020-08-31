package jgame;

import java.util.Iterator;

import jgame.entity.Entity;
/**
 * This is an ordered set (ordered by the layer of the game object) that stores game objects
 * Why is this necessary? Since java overlaps the last graphics that were drawn the game objects 
 * with a bigger layer number must be drawn later
 * @author David Almeida
 * @since 1.0
 */
class OrderedLayerSet implements Iterable<Entity>{

	private Node first;
	private Node last;
	
	/**
	 * This class represents a node in the linked set
	 * @author David Almeida
	 * @since 1.0
	 */
	private class Node{
		private Node next;
		private Node before;
		private Entity value;
		
		/**
		 * Creates a node
		 * @param n the next node
		 * @param b the previous node
		 * @param v the value of the node
		 */
		private Node(Node n,Node b,Entity v){
			next = n;
			before = b;
			value = v;
		}
	}
	
	/**
	 * Creates the set
	 */
	public OrderedLayerSet(){
		first = null;
		last = null;
	}
	
	/**
	 * Adds a new game object to the set
	 * @param o the game object to add
	 * @since 1.0
	 */
	public void add(Entity o){
		if(first == null){
			first = new Node(null,null,o);
			last = first;
		}else{
			if(first.value.getLayer() >= o.getLayer() && !o.equals(first.value)){
				Node newFirst = new Node(first,null,o);
				first.before = newFirst;
				first = newFirst;
				return;
			}
			
			Node current = first.next;
			while(current != null){
				if(current.value.getLayer() >= o.getLayer()){
					//this means that the actual node has a bigger layer 
					//so it must be next of the new one
					Node node = new Node(current,current.before,o);
					if(current.before.next == null)
						break;
					if(o.equals(current.value))
						return;
					
					current.before.next = node;
					current.before = node;
					return;
				}
				current = current.next;
			}
			
			//this happens when there is not any game object with a bigger layer than o
			Node newLast = new Node(null,last,o);
			last.next = newLast;
			last = newLast;
		}
	}
	
	/**
	 * Removes a game object of the set
	 * @param o the game object
	 * @since 1.0
	 */
	public void remove(Entity o){
		if(first == null)
			return;
		if(first.value.equals(o)){
			if(first.next == null)
				first = last = null; //list with only one element
			else
				first = first.next;
			return;
		}
		
		if(last.value.equals(o)){
			last.before.next = null;
			last = last.before;
		}
		
		Node current = first.next;
		while(current != null){
			if(current.value.equals(o)){
				current.before.next = current.next;
				return;
			}
			current = current.next;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Entity> iterator() {
		return new ListIterator(first);
	}

	/**
	 * This class is the set iterator 
	 * @author David Almeida
	 * @since 1.0
	 */
	private class ListIterator implements Iterator<Entity>{
		
		private Node current;
		
		/**
		 * Creates the iterator
		 * @param f the first node of the set
		 * @since 1.0
		 */
		private ListIterator(Node f){
			current = f;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return current != null; 
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Entity next() {
			Node aux = current;
			current = current.next;
			return aux.value;
		}
	}

}
