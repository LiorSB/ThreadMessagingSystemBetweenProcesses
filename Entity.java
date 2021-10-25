import java.util.*;

public class Entity extends Thread
{
	//Max Sleep time is 500 msecs.
	private static final int MAX_SLEEP = 500;
	
	//ID of the Entity (passed by the creating manager via the
	// constructor)
	private int ID;
	
	//Number of entities in the System (passed by the creating
	//manager via the constructor)
	private int numEntities;
	
	//The entity's private message queue
	private MsgQueue queue;
	
	//The reference to the (single) dispatcher in the system (passed by
	//the creating manager via the constructor)
	private Dispatcher dispatcher;
	
	//Random number generator class to randomize sleeping time at
	//the end of entity iteration!
	private Random rand;
	
	public Entity(int ID, Dispatcher dispatcher, int numEntities)
	{
		this.ID = ID;
		this.dispatcher = dispatcher;
		this.numEntities = numEntities;
		
		queue = new MsgQueue();
		rand = new Random();
		
		dispatcher.register(queue, ID);
	}
	
	@Override
	public void run()
	{
		int i = 0;
		int destID;
		int sleepTime;
		
		MSG message;
		
		while(true)
		{
			// Ensure you don’t send MSG to yourself
			if (i == ID)
			{
				i = (i + 1) % numEntities;
			}
			
			// send MSG to someone at each iteration to avoid Deadlock
			message = new MSG(new String("MSG from " + ID), i);
			dispatcher.enqueueMsg(message);
			i = (i + 1) % numEntities;
			
			// Read one MSG from private MsgQueue
			message = queue.dequeueMsg();
			
 			// Make sure you validate MSG and protect from
 			// Abnormalities: ensure MSG is not null! If it is
 			// Print an error Message to Screen and Discard MSG
 			if (message == null)
 			{
 				System.out.println("Entity::run::Unexpected Error - " +
				"MSG is null\n");
				continue;
 			}
 			
 			// Also, if MSG is not null, ensure its destination ID is
 			// equal to Entity ID (otherwise, something is wrong in
 			// System – MSG shouldn’t have gotten here!)
 			destID = message.getDestID();
 			
 			if (destID != ID)
 			{
 				System.out.println("Entity::run::Unexpected Error - " +
				"destID is not equal to Entity ID\n");
				continue;
 			}
 			
 			// If all OK with MSG, Print it to the Screen
 			System.out.println("ID: " + ID + " - " + message.getBody());
 			
 			// Force Thread to Sleep for Random time
 			sleepTime = rand.nextInt(MAX_SLEEP + 1);
 			
 			try
 			{
 				Thread.sleep(sleepTime);
 			}
 			catch (InterruptedException ie)
 			{
 				System.err.println(ie);
 			}
		}
	}
}