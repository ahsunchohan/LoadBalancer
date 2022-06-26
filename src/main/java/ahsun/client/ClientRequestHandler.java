package ahsun.client;

import java.util.ArrayList;
import java.util.List;

public class ClientRequestHandler {

    private List<ClientRequest> requests;
	
	public ClientRequestHandler() {
		this.requests = new ArrayList<ClientRequest>();
	}

	public List<ClientRequest> getRequests() {
		return requests;
	}

	public void setRequests(List<ClientRequest> requests) {
		this.requests = requests;
	}
	
	public ClientRequest addNewRequest(int duration) {
		ClientRequest req = new ClientRequest(duration);
		this.requests.add(req);
		return req;
	}

}