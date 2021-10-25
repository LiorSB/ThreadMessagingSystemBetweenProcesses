public class Manager
{
	// Maximum number of Entities in the system
	private static final int MAX_ENT = 5;
	
	// Array of Entity Threads
	private Entity[] entities;
	
	// The dispatcher in the system
	private Dispatcher dispatcher;
	
	// Recall Dispatcher is a Runnable Object (implements Runnable).
	// In order to explicitly run its Thread part, we need to create a new
	// Thread object and send the dispatcher to its constructor. This is
 	// exactly what this Object is for!
 	private Thread dispatcherThread;
 	
 	public Manager()
 	{
 		dispatcher = new Dispatcher(MAX_ENT);
 		entities = new Entity[MAX_ENT];

 		for (int i = 0; i < MAX_ENT; i++)
 		{
 			entities[i] = new Entity(i, dispatcher, MAX_ENT);
 		}
 		
 		for (int i = 0; i < MAX_ENT; i++)
 		{
 			entities[i].start();
 		}
 		
 		dispatcherThread = new Thread(dispatcher);
 		dispatcherThread.start();
 	}
 	
 	public static void main(String[] args)
 	{
 		Manager manager = new Manager();
 	}
}