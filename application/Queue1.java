package application;

public class Queue1 {
	private int front, rear, size;
	private int capacity;
	private TNode[] queueArray;

	// Constructor to initialize the queue
	public Queue1(int capacity) {
		this.capacity = capacity;
		front = this.size = 0;
		rear = capacity - 1;
		queueArray = new TNode[capacity];
	}

	// Method to check if the queue is full
	public boolean isFull() {
		return (size == capacity);
	}

	// Method to check if the queue is empty
	public boolean isEmpty() {
		return (size == 0);
	}

	// Method to add an item to the queue (enqueue)
	public void enqueue(TNode item) {
		if (isFull()) {
			//System.out.println("Queue is full");
			return;
		}
		rear = (rear + 1) % capacity;
		queueArray[rear] = item;
		size++;
		// System.out.println(item + " enqueued to queue");
	}

	// Method to remove an item from the queue (dequeue)
	public TNode dequeue() {
		if (isEmpty()) {
			//System.out.println("Queue is empty");
			return null;
		}
		TNode item = queueArray[front];
		front = (front + 1) % capacity;
		size--;
		return item;
	}

	// Method to peek at the front item of the queue
	public TNode peek() {
		if (isEmpty()) {
			//System.out.println("Queue is empty");
			return null;
		}
		return queueArray[front];
	}

	// Method to get the front item of the queue
	public TNode front() {
		if (isEmpty()) {
			//System.out.println("Queue is empty");
			return null;
		}
		return queueArray[front];
	}

	// Method to get the rear item of the queue
	public TNode rear() {
		if (isEmpty()) {
			//System.out.println("Queue is empty");
			return null;
		}
		return queueArray[rear];
	}

	public void print(Queue1 q1) {
		Queue1 q2 = new Queue1(q1.capacity);
		while (!q1.isEmpty()) {
			System.out.println(q1.peek());
			q2.enqueue(q1.dequeue());
		}
		while (!q2.isEmpty()) {
			q1.enqueue(q2.dequeue());
		}

	}
	

	// Method to get the current size of the queue
	public int getSize() {
		return size;
	}
}
