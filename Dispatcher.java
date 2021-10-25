import java.util.*;

public class Dispatcher extends MsgQueue implements Runnable
{
	//Max Sleep time is 500 msecs.
	private static final int MAX_SLEEP = 500;
	
	//must get this to create a DB (array) of message Queues
	//array is indexed by Entity ID
	private int numEntities;
	
	//Array of message queues for the Entities. Each entity registers its
	// own MSG Queue with the dispatcher - enabling dispatcher to
	//send it messages. Array is indexed by Entity ID. Its size is
	//numEntities, of course.
	private MsgQueue[] entityQueue;
	
	//Random number generator class to randomize sleeping time at
	//the end of dispatcher iteration!
	private Random rand;
	
	public Dispatcher(int numEntities)
	{
		this.numEntities = numEntities;
		
		rand = new Random();
		entityQueue = new MsgQueue[numEntities];
		
		for (int i = 0; i < numEntities; i++)
		{
			entityQueue[i] = null;
		}
	}
	
	public void register(MsgQueue q, int entityID)
	{
		if (entityID < 0 || entityID >= numEntities)
		{
			System.out.println("Dispatcher::register::Unexpected Error - " +
				"entityID isn't valid\n");
			return;
		}
		
		if (entityQueue[entityID] != null)
		{
			System.out.println("Dispatcher::register::Unexpected Error - " +
				"Entity already registered to its MsgQueue\n");
			return;
		}
		
		entityQueue[entityID] = q;
		System.out.println("registered ID" + entityID);
	}
	
	@Override
	public void run()
	{
		int destID;
		int sleepTime;
		
		MSG message;
		
		while(true)
		{
			//dequeueMsg from Dispatcher’s Queue
			message = dequeueMsg();
			
			// Make sure you validate MSG and protect from
 			// Abnormalities: ensure MSG is not null! If it is
 			// Print an error Message to Screen and Discard MSG
 			if (message == null)
 			{
 				System.out.println("Dispatcher::run::Unexpected Error - " +
				"MSG is null\n");
				continue;
 			}
 			
 			// Also, if MSG is not null, ensure its destination ID is
 			// a valid Entity ID in the system: must be ID in the range:
 			// 0.. (numEntities – 1).
 			destID = message.getDestID();
 			
 			if (destID < 0 || destID >= numEntities)
 			{
 				System.out.println("Dispatcher::run::Unexpected Error - " +
				"destID not in range\n");
				continue;
 			}
 			
 			//If all OK with MSG, enqueue it in the proper 
 			// destination MsgQueue (in entityQueue Array)
 			entityQueue[destID].enqueueMsg(message);
 			
 			// Force Dispatcher Thread to Sleep for Random time
 			// in the range of (1.. MAX_SLEEP) milliseconds
 			// Since Dispatcher class is not extending Thread
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