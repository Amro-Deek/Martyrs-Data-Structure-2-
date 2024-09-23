package application;

public class MinHeap {
	private static final int DEF_MAX_HEAP_SIZE = 100;
	private int size;
	Martyr elements[];

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Martyr[] getElements() {
		return elements;
	}

	public MinHeap() {
		setup(DEF_MAX_HEAP_SIZE);
	}

	private void setup(int MaxSize) {
		elements = new Martyr[MaxSize];
		size = -1;
	}

	public void insert(Martyr value) {
		if (isFull())
			return;
		elements[++size] = value;
		int i = size;
		int parent = (int) Math.ceil(((double) size) / 2) - 1;

		while (i > 0) {
			if (elements[parent].getAge() > elements[i].getAge()) {
				swap(parent, i);
				i = parent;
				parent = (int) Math.ceil(((double) parent) / 2) - 1;
			} else
				return;
		}
	}

	public void heapSort() {
		int b = size;
		for (int j = size; j >= 0; j--) {
			swap(0, j);
			size--;
			minHeapify(0);
		}
		size = b;
	}

	public void removeMinNum() {
		swap(0, size--);
		minHeapify(0);
	}

	private void minHeapify(int i) {
		int largest = i;
		int l = i * 2 + 1;
		int r = i * 2 + 2;
		if (l <= size && elements[l].getAge() < elements[largest].getAge())
			largest = l;
		if (r <= size && elements[r].getAge() < (elements[largest]).getAge())
			largest = r;
		if (largest != i) {
			swap(i, largest);
			minHeapify(largest);
		}
	}

	private void swap(int parent, int i) {
		Martyr x = elements[parent];
		elements[parent] = elements[i];
		elements[i] = x;
	}

	public void showStructure() {
		int i = 1;
		int j = 0;
		int p = 1;
		while (true) {
			for (; j < i; j++) {
				if (j > size)
					return;
				System.out.print(elements[j] + " ");
			}
			p *= 2;
			i += p;
			System.out.println();
		}
	}

	public void showSubTree(int i, int level) {
		int j = 0, p = 1;
		while (level >= 0) {
			for (; j <= i; j++) {
				if (j > size)
					return;
				System.out.print(elements[j] + " ");
			}
			p *= 2;
			i += p;
			System.out.println();
			level--;
		}
	}

	public boolean isFull() {
		return size == DEF_MAX_HEAP_SIZE - 1;
	}

}
