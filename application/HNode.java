package application;

public class HNode {
	private Date data;
	private AVL avl;
	private char state = 'E';

	public HNode() {
		avl =new AVL();
	}

	public HNode(Date data) {
		this.data = data;
		avl = new AVL();
	}

	public char getState() {
		return state;
	}

	public void setState(char state) {
		if (state == 'D' || state == 'E' || state == 'F')
			this.state = state;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public boolean isFull() {
		return state != 'D' && state != 'E';
	}
	public AVL getAvl() {
		return avl;
	}

	public void setAvl(AVL avl) {
		this.avl = avl;
	}

	@Override
	public String toString() {
		if (data==null) {
			return "Null";

		}
		return data.getDate() + " -->  avl = "+avl.traverse(avl.root)+"";
	}
}
