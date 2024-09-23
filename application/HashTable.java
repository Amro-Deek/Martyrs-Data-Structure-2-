package application;

public class HashTable {
	private HNode[] hash = new HNode[11];
	private int size = 11;
	private int counter = 0;

	public HashTable() {
		initialization();
	}

	public int getSize() {
		return size;
	}

	public HNode get(int index) {
		return hash[index];
	}

//	public void insert(HTData data) {
////		if (search(data)!=-1) {
////			hash[search(data)].getData().getAvl().insert(data.getAvl().getRoot().getMartyr());
////		}
//		int h = data.hashCode(), j = 1, i = h % hash.length;
//		while (hash[i].isFull()) {
//			i = (h + (int) Math.pow(j, 2)) % hash.length;
//			j++;
//		}
////		int j = hash(data);
////		hash[j] = new HNode(data);
////		hash[j].setState('F');
////		counter++;
////		if (counter >= size / 2)
////			rehash(getNextPrime(2 * size));
//
//	}

	public void insert(Date date, Martyr m) {
		int h = Math.abs(date.getDate().hashCode()), j = 1, i = h % size;
		while (hash[i].isFull() && !hash[i].getData().getDate().equals(date.getDate())) {
			i = Math.abs(h + (int) Math.pow(j, 2)) % size;
			j++;
		}
		if (hash[i].getState() != 'F') {
			hash[i] = new HNode(date);
			hash[i].setState('F');
			counter++;
			if (counter >= size / 2)
				rehash(getNextPrime(2 * size));
		}
		hash[i].getAvl().insert(m);

	}

	private int getNextPrime(int x) {
		while (true) {
			if (isPrime(x))
				break;
			x++;
		}
		return x;
	}

	private boolean isPrime(int n) {
		if (n <= 1) {
	        return false;
	    }
	    if (n <= 3) {
	        return true; // 2 and 3 are prime numbers
	    }
	    if (n % 2 == 0 || n % 3 == 0) {
	        return false; // exclude multiples of 2 and 3
	    }
	    for (int i = 5; i * i <= n; i += 6) {
	        if (n % i == 0 || n % (i + 2) == 0) {
	            return false;
	        }
	    }
	    return true;
	}

	private void rehash(int newSize) {
		HNode[] h = hash;
		hash = new HNode[newSize];
		initialization();
		counter = 0;
		System.out.println("new table size = " + size);
		size = newSize;
		for (int i = 0; i < h.length; i++) {
			System.out.println("I ="+i);
			if (h[i].getState() == 'F') {
				int index = hash(h[i].getData());
				hash[index] = h[i];
			}
		}
	}
	
	private void rehashD(int newSize) {
		
		hash = new HNode[newSize];
		HNode[] h = hash;
		initialization();
		counter = 0;
		System.out.println("new table size = " + size);
		size = newSize;
		for (int i = 0; i < h.length; i++) {
			System.out.println("I ="+i);
			if (h[i].getState() == 'F') {
				int index = hash(h[i].getData());
				hash[index] = h[i];
			}
		}
	}

	public Date delete(Date data) {
		System.out.println(-1);
		int j = search(data);
		System.out.println(0);
		if (j != -1) {
			System.out.println(1);
			counter--;
			System.out.println(2);
			hash[j].setState('D');
			if (counter <= size / 4)
				System.out.println(3);
				//rehash(getPrevPrime(size / 2));
				System.out.println(4);

			return data;
		} else
			return null;
	}

	public int getPrevPrime(int x) {
		x--; // Start checking from the number just below x
	    while (x >= 2) {
	        if (isPrime(x)) {
	            return x;
	        }
	        x--;
	    }
//		while (true) {
//			if (isPrime(x))
//				break;
//			if (x < 3) {
//				x = 3;
//				break;
//			}
//			x--;
//		}

		return x;
	}

	public int search(Date data) {
		int h = Math.abs(data.getDate().hashCode()), j = 1, i = h % size, index = -1;
		while (hash[i].isFull()) {
			if (hash[i].getData().getDate().equals(data.getDate())) {
				index = i;
				break;
			}
			i = Math.abs(h + (int) Math.pow(j, 2)) % size;
			j++;
		}
		return index;
	}

	private void initialization() {
		for (int i = 0; i < hash.length; i++) {
			hash[i] = new HNode();
		}
	}

	private int hash(Date data) {
		int h = Math.abs(data.getDate().hashCode()), j = 1, i = h % size;
		while (hash[i].isFull()) {
			i = Math.abs(h + (int) Math.pow(j, 2)) % size;
			j++;
		}
		return i;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (HNode thNode : hash) {
			if (thNode.getState() != 'D')
				s.append(thNode).append("\n");
			else
				s.append("null\n");
		}
		return s.toString();
	}
}
