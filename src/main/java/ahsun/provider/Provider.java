package ahsun.provider;

import java.util.UUID;
import java.util.logging.Logger;

import ahsun.loadbalancer.LoadBalancer;
import ahsun.client.ClientRequest;

public class Provider {

    private static Logger log = Logger.getLogger(Provider.class.getName());
    
    private UUID uuid;
    private int numRequests;
    private boolean active;
    private LoadBalancer loadBalancer;
    private int health;
    public final static int maxRequests = 10;

    public Provider(LoadBalancer loadBalancer){
       this.uuid = UUID.randomUUID();
       this.loadBalancer = loadBalancer;
       this.active = true;  // default value is active
       this.health = 2;     // maximum health is 2
    }

    public String get(ClientRequest request){
        if (active && numRequests < Provider.maxRequests) {
            log.info("Provider "+this.uuid+" is processing client request "+request.getId());
            numRequests++;
            log.info("Provider "+this.uuid+" has now "+numRequests+" parallel request");
            if (numRequests == Provider.maxRequests){
                //Reached max capacity
                this.loadBalancer.excludeProvider(this);  
                log.info("Provider "+this.uuid+" has reached its maximum capacity : " + maxRequests + " parallel request");              
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
