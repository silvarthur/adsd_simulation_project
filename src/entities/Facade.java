package entities;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import eduni.simjava.distributions.Sim_normal_obj;
import eduni.simjava.distributions.Sim_random_obj;

public class Facade extends Sim_entity {
	private Sim_port in;
	private Sim_port get;
	private Sim_port post;
	/**
	 * Gerador de números aleatórios baseado na distribuição Normal.
	 */
	private Sim_normal_obj delay;
	private Sim_random_obj prob;
	
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
		
	}

	public void body() {
		while (Sim_system.running()) {
			/**
			 * Esta classe representa eventos que são passados ​​entre as entidades na simulação
			 */
			Sim_event e = new Sim_event();
			sim_get_next(e);
			
			sim_process(delay.sample());
			
			sim_completed(e);
			
			double p = prob.sample();
			
			if (p < 0.5) {
				// 50 % vai ser uma request get
				sim_schedule(get, 0.0, 1);
				sim_trace(1, "Request GET\n");
			} else {
				// 50 % vai ser uma request post
				sim_schedule(post, 0.0, 1);
				sim_trace(1, "Request POST\n");
			}
		}
	}
}
