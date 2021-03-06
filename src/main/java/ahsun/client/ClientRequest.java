package ahsun.client;

import java.util.UUID;

import ahsun.loadbalancer.LoadBalancer;

public class ClientRequest {

    private UUID id;
    private String response;

    private int duration;

    public ClientRequest(int duration) {
		this.id= UUID.randomUUID();
		this.duration=duration;
	}
	
	public void sendRequest(LoadBalancer loadBalancer) {
		setResponse(loadBalancer.distributeLoad(this));
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}


    
}
