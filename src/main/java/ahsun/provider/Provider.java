package ahsun.provider;

import java.util.UUID;

public class Provider {

    private UUID uuid;

    public Provider(){
        this.uuid = UUID.randomUUID();
    }

    public String get(){
        return this.uuid.toString();
    }

    public UUID getUuid() {
		return uuid;
	}

    public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
    
}
