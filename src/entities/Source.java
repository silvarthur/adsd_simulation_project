package entities;
import eduni.simjava.*;
import eduni.simjava.distributions.Sim_poisson_obj;

public class Source extends Sim_entity {
	private Sim_port in, out1, out2;
	private Sim_poisson_obj delay;
	
	public Source(String name, double average) {
		super(name);
		this.delay = new Sim_poisson_obj("Delay", average);
		
		add_generator(delay);		
		
		this.in = new Sim_port("In"); // Port for receiving events from the server entity.
		this.out1 = new Sim_port("Out1"); // Port for sending events to the facade entity.
		this.out2 = new Sim_port("Out2"); // Port for sending events to the log info entity.
		
		add_port(in);
		add_port(out1);
		add_port(out2);
	}

	public void body() {
		for(int i = 0; i < 100; i++) {
			Sim_event e = new Sim_event();
			sim_get_next(e); // Get the next event.
			sim_process(delay.sample()); // Process the event.
			sim_completed(e); // The event has completed service.
			
			sim_schedule(out1, 0.0, 0);
			sim_schedule(out2, 0.0, 0); 
			
			sim_pause(delay.sample());
		}
	}
		
}
