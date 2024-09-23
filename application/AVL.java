package application;

public class AVL {
	TNode root;

	public TNode getRoot() {
		return root;
	}

	public void setRoot(TNode root) {
		this.root = root;
	}

	public int getSize() {
		return getSize(root);
	}

	private int getSize(TNode node) {
		if (node == null) {
			return 0;
		}
		if (node.getRight() == null && node.getLeft() == null) {
			return 1;
		}
		if (node.getRight() != null && node.getLeft() != null) {
			return 1 + getSize(node.getLeft()) + getSize(node.getRight());
		} else if (node.getRight() != null) {
			return 1 + getSize(node.getRight());
		} else {
			return 1 + getSize(node.getLeft());
		}
	}

	public void updateHeight(TNode node) {
		if (node == null)
			return;
		node.setHeight(getDepth(node) - 1);
	}

	public int getDepth(TNode node) {
		if (node == null)
			return 0;
		return Math.max(1 + getDepth(node.getLeft()), 1 + getDepth(node.getRight()));
	}

	private TNode rotateToRight(TNode node) {
		TNode leftChild = node.getLeft();
		if (leftChild != null) {
			node.setLeft(leftChild.getRight());
			leftChild.setRight(node);
		}
		updateHeight(node);
		updateHeight(leftChild);
		return leftChild;
	}

	private TNode rotateToLeft(TNode node) {
		TNode rightChild = node.getRight();
		if (rightChild != null) {
			node.setRight(rightChild.getLeft());
			rightChild.setLeft(node);
		}
		updateHeight(node);
		updateHeight(rightChild);
		return rightChild;
	}

	public int getHeight(TNode node) {
		return node == null ? -1 : node.getHeight();
	}

	public int Balance(TNode node) {
		if (node == null)
			return 0;
		return getHeight(node.getLeft()) - getHeight(node.getRight());
	}

	public void insert(Martyr data) {
		this.setRoot(insert(this.getRoot(), data));
	}

	public TNode insert(TNode node, Martyr data) {
		if (node == null)
			return new TNode(data);
		if (data.compareTo(node.getMartyr()) < 0)
			node.setLeft(insert(node.getLeft(), data));
		else if (data.compareTo(node.getMartyr()) > 0)
			node.setRight(insert(node.getRight(), data));
		else
			return node;
		updateHeight(node);
		int bf = Balance(node);
		if (bf > 1) {
			if (Balance(node.getLeft()) >= 0)
				return rotateToRight(node);
			else {
				node.setLeft(rotateToLeft(node.getLeft()));
				return rotateToRight(node);
			}
		} else if (bf < -1) {
			if (Balance(node.getRight()) <= 0) {
				return rotateToLeft(node);
			} else {
				node.setRight(rotateToRight(node.getRight()));
				return rotateToLeft(node);
			}
		} else
			return node;
	}

	public void traverseInOrder() {
		traverseInOrder(this.getRoot());
	}

	private void traverseInOrder(TNode node) {
		if (node == null)
			return;
		traverseInOrder(node.getLeft());
		System.out.print(node.getMartyr() + " ");
		traverseInOrder(node.getRight());
	}

	public String traverse(TNode node) {
		StringBuilder result = new StringBuilder();
		traverseInOrder(node, result);
		return result.toString();
	}

	private void traverseInOrder(TNode node, StringBuilder result) {
		if (node == null) {
			return;
		}
		traverseInOrder(node.getLeft(), result);
		result.append(node.getMartyr()).append(" ");
		traverseInOrder(node.getRight(), result);
	}

	public void delete(Martyr data) {
		this.setRoot(delete(data, this.getRoot()));
	}

	private TNode delete(Martyr data, TNode node) {
		System.out.println(0);

		if (node == null) {
			System.out.println(1);
			return null;
		}
		if (data.compareTo(node.getMartyr()) > 0) {
			System.out.println(2);
			node.setRight(delete(data, node.getRight()));
		} else if ((data.compareTo(node.getMartyr()) < 0)) {
			node.setLeft(delete(data, node.getLeft()));
			System.out.println(3);
		} else if (node.getRight() == null && node.getLeft() == null)
			node = null;
		else if (node.getLeft() == null)
			node = node.getRight();
		else if (node.getRight() == null)
			node = node.getLeft();
		else
			deleteNodeWithTwoChildren(node);
		updateHeight(node);
		int bf = Balance(node);
		if (bf > 1) {
			if (Balance(node.getLeft()) >= 0)
				return rotateToRight(node);
			else {
				node.setLeft(rotateToLeft(node.getLeft()));
				return rotateToRight(node);
			}
		} else if (bf < -1) {
			if (Balance(node.getRight()) <= 0) {
				return rotateToLeft(node);
			} else {
				node.setRight(rotateToRight(node.getRight()));
				return rotateToLeft(node);
			}
		} else
			return node;
	}

	private void deleteNodeWithTwoChildren(TNode node) {
		TNode suc = getMin(node.getRight());
		node.setMartyr(suc.getMartyr());
		delete(suc.getMartyr(), node.getRight());
	}

	private TNode getMin(TNode node) {
		if (node.getLeft() == null)
			return node;
		return getMin(node.getLeft());
	}

}
