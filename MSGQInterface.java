public interface MSGQInterface
{
	// Put the message in the tail of the queue, but must wait() while
	// queue is Full!
	void enqueueMsg(MSG message);
	
	// Take the message from the head of the queue, but must wait()
	 // while queue is Empty!
	MSG dequeueMsg();
	
	//Queue implementation in a simple array - queue is empty when
	//(head==tail)
	boolean queueIsEmpty();
	
	//Queue implementation in a simple array - queue is full when:
	//((tail + 1) % QSIZE) == head)
	//Recall the Naive implementation of Bounded Buffer (Chapter 2,
	//slides 42, 43)
	boolean queueIsFull();
}