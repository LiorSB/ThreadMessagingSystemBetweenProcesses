public class MSG
{
	//MSG Body
	private String body;
	
	//ID of destination Entity!
	private int destID; 
	
	// Parameter Constructor
	public MSG(String body, int destID)
	{
		this.body = body;
		this.destID = destID;
	}
	
	// Set Methods
	
	public void setBody(String body)
	{
		this.body = body;
	}
	
	public void setDestID(int destID)
	{
		this.destID = destID;
	}
	
	// Get Methods
	
	public String getBody()
	{
		return body;
	}
	
	public int getDestID()
	{
		return destID;
	}
}