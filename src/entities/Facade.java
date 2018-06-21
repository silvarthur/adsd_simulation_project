package entities;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import eduni.simjava.distributions.Sim_normal_obj;
import eduni.simjava.distributions.Sim_random_obj;

public class Facade extends Sim_entity {
	private Sim_port in;
	private Sim_port outGet;
	private Sim_port outPost;

	private Sim_normal_obj delay; // Random number generator based on the normal distribution.
	private Sim_random_obj prob;
	
	public Facade(String name, double average, double variance) {
		super(name);
		this.delay = new Sim_normal_obj("Delay", average, variance);
		this.prob = new Sim_random_obj("Probability");
		
		add_generator(delay);
		add_generator(prob);
		
		in = new Sim_port("In"); // Port for receiving events from the source entity.
		outGet = new Sim_port("Get"); // Port for sending events to the server entity. Represents a GET Request.
		outPost = new Sim_port("Post"); // Port for sending events to the server entity. Represents a POST Request.
		
		add_port(in);
		add_port(outGet);
		add_port(outPost);
	}

	public void body() {
		while (Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e); // Get the next event.
			sim_process(delay.sample()); // Process the event.
			sim_completed(e); // The event has completed service.
			
			double p = prob.sample();
			
			if (p < 0.5) {
				sim_schedule(outGet, 0.0, 1); // 50% chance that the entity sends a GET request.
				sim_trace(1, "Sending a GET Request\n");
			} else {
				sim_schedule(outPost, 0.0, 1); // 50% chance that the entity sends a POST request.
				sim_trace(1, "Sending a POST Request\n");
			}
		}
	}
}
