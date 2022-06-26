package ahsun.provider;

import java.util.UUID;

import ahsun.loadbalancer.LoadBalancer;

public class Provider {

    private UUID uuid;
    private int numRequests;
    private boolean active;
    private LoadBalancer loadBalancer;
    private int health;
    private final static int maxRequests = 10;

    public Provider(LoadBalancer loadBalancer){
       this.uuid = UUID.randomUUID();
       this.loadBalancer = loadBalancer;
       this.active = true;
       this.health = 2;
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
            return "MaxRequestReached";
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

    public void setHealth(int health){
        this.health = health;
    }

    public int getHealth(){
        return this.health;
    }
    
}
