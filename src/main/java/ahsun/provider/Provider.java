package ahsun.provider;

import java.util.UUID;

public class Provider {

    private UUID uuid;
    private int numRequests;
    public final static int maxRequests = 10;

    public Provider(){
        this.uuid = UUID.randomUUID();
    }

    public String get(){
        if (numRequests < Provider.maxRequests) {
            numRequests++;
            return this.uuid.toString();
        }else if (numRequests == Provider.maxRequests) {
            return "MaxRequestsReached";
        }else{
            return null;
        }        
    }

    public UUID getUuid() {
		return uuid;
	}

    public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

    public int getNumRequests() {
		return numRequests;
	}

	public void setNumRequests(int numRequests) {
		this.numRequests = numRequests;
	}
	
	public void addRequest() {
		this.numRequests++;
	}
    
}
