package jgame.structures;

/**
 * Represents an object that holds two objects of any type
 * @author David Almeida
 * @param <E> The type of the first object
 * @param <V> The type of the second object
 * @version 1.0
 * @since 1.0
 */
public class Pair<E,V>{
	private E e;
	private V v;

	/**
	 * 
	 * @param e the first element
	 * @param v the second element
	 * <br>
	 * v to be of the same type of the second generic type
	 * @since 1.0
	 */
	public Pair(E e,V v){
		this.e = e;
		this.v = v;
	}

	/**
	 * 
	 * @return the first element of the pair
	 * @since 1.0
	 */
	public E getE(){
		return e;
	}

	/**
	 * 
	 * @return the second element of the pair
	 * @since 1.0
	 */
	public V getV(){
		return v;
	}
	
	/**
	 * 
	 * @param e the new first element of pair
	 * @since 1.0
	 */
	public void setE(E e){
		this.e = e;
	}

	/**
	 * 
	 * @param v the new second element of pair
	 * @since 1.0
	 */
	public void setV(V v){
		this.v = v;
	}
}
