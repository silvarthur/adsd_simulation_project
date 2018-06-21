package entities;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_stat;
import eduni.simjava.Sim_system;
import eduni.simjava.distributions.Sim_normal_obj;
import eduni.simjava.distributions.Sim_random_obj;

public class Facade extends Sim_entity {
	private Sim_port in, get, post;
	private Sim_normal_obj delay;
	private Sim_random_obj prob;
	private Sim_stat stat;
	
	public Facade(String name, double average, double variance) {
		super(name);
		
		this.in = new Sim_port("In");
		this.get = new Sim_port("Get");
		this.post = new Sim_port("Post");
		
		add_port(in);
		add_port(get);
		add_port(post);
		
		this.delay = new Sim_normal_obj("Delay", average, variance);
		this.prob = new Sim_random_obj("Probability");
		
		add_generator(delay);
		add_generator(prob);
		
		stat = new Sim_stat();
		stat.add_measure(Sim_stat.QUEUE_LENGTH);
		
		set_stat(stat);
	}

	public void body() {
		while (Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e);
			sim_process(delay.sample());
			sim_completed(e);
			
			double p = prob.sample();
			
			if (p < 0.5) {
				sim_schedule(get, 0.0, 1);
				sim_trace(1, "Requisição GET");
			} else {
				sim_schedule(post, 0.0, 1);
				sim_trace(1, "Requisição POST");
			}
		}
	}
}
