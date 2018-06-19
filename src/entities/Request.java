package entities;

import eduni.simjava.*;
import eduni.simjava.distributions.Sim_uniform_obj;

public class Request extends Sim_entity{
	private Sim_port server;
	private Sim_port in;
	private Sim_uniform_obj delay;
	private RequestType type;
	
	public Request(String name, RequestType type) {
		super(name);
		this.type = type;
		
		this.delay = new Sim_uniform_obj("Delay", 0, 1);
		add_generator(delay);		
		
		
		in = new Sim_port("In");
		add_port(in);
		
		server = new Sim_port("Server");
		add_port(server);
	}

	public void body() {
		while (Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e);
			sim_process(delay.sample());
			sim_completed(e);
			sim_trace(1, "Requisição do tipo " + this.getType().toString() + " vai para o servidor\n");
			sim_schedule(server, 0.0, 1);
		}
	}
	
	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}
}
