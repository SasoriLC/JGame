package jgame.component;

import jgame.structures.Point2D;
/**
 * TODO
 * @author David Almeida
 * @since 1.0
 */
public class BoxCollider extends Component{
	
	public Point2D position;
	private int width;
	private int height;
	
	/**
	 * 
	 * @param x - x position
	 * @param y - y position
	 * @param width - the width
	 * @param height - the height
	 * @since 1.0
	 */
	public BoxCollider(float x, float y,int width, int height){
		position = new Point2D(x,y);
		this.width = width;
		this.height = height;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + width;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoxCollider other = (BoxCollider) obj;
		if (height != other.height)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (width != other.width)
			return false;
		return true;
	}
		
}
