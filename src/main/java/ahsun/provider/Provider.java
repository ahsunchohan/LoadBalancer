package ahsun.provider;

import java.util.UUID;

import ahsun.loadbalancer.LoadBalancer;

public class Provider {

    private UUID uuid;
    private int numRequests;
    private boolean active;
    private LoadBalancer loadBalancer;
    public final static int maxRequests = 10;

    public Provider(LoadBalancer loadBalancer){
        this.uuid = UUID.randomUUID();
        this.loadBalancer = loadBalancer;
        setActive(true);        
    }

    public String get(){
        if (active && numRequests < Provider.maxRequests) {
            numRequests++;
            if (numRequests == Provider.maxRequests){
                //Reached max capacity
                this.loadBalancer.excludeProvider(this);                
            }
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

    public boolean isActive() {
		return active;
	}

    public void setActive(boolean active) {
		this.active = active;
	}

    public void register() {
		this.loadBalancer.registerProvider(this);
	}
    
}
