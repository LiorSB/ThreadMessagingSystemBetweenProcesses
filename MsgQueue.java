public class MsgQueue implements MSGQInterface
{
	//Maximum elements in the queue
	private static final int QSIZE = 500;
	
	//The simple Array implementing the queue
	private MSG[] queue;
	
	//index pointing to the head of queue
	private int head; 
	//index pointing to the tail of queue
	private int tail; 
	
	public MsgQueue()
	{
		queue = new MSG[QSIZE];
		head = 0;
		tail = 0;
	}
	
	public synchronized void enqueueMsg(MSG message)
	{
		try
		{
			while(queueIsFull())
			{
				wait();
			}
			
			queue[tail] = message;
			tail = (tail + 1) % QSIZE;
			
			notify();
		}
		catch (IllegalMonitorStateException imse)
		{
			System.err.println(imse);
		}
		catch (InterruptedException ie)
		{
			System.err.println(ie);
		}
	}
	
	public synchronized MSG dequeueMsg()
	{
		MSG message = null;
		
		try
		{
			while(queueIsEmpty())
			{
				wait();
			}
			
			message = queue[head];
			head = (head + 1) % QSIZE;
			
			notify();
		}
		catch (IllegalMonitorStateException imse)
		{
			System.err.println(imse);
		}
		catch (InterruptedException ie)
		{
			System.err.println(ie);
		}
		
		return message;
	}
	
	public synchronized boolean queueIsEmpty()
	{
		return (head == tail);
	}
	
	public synchronized boolean queueIsFull()
	{
		return (((tail + 1) % QSIZE) == head);
	}
}