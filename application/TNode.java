package application;

public class TNode {
	private Martyr martyr;
	private TNode left, right;
	int height;

	public TNode(Martyr martyr) {
		super();
		this.martyr = martyr;
	}

	public Martyr getMartyr() {
		return martyr;
	}

	public void setMartyr(Martyr martyr) {
		this.martyr = martyr;
	}

	public TNode getLeft() {
		return left;
	}

	public void setLeft(TNode left) {
		this.left = left;
	}

	public TNode getRight() {
		return right;
	}

	public void setRight(TNode right) {
		this.right = right;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "TNode [martyr=" + martyr + "]";
	}

}
