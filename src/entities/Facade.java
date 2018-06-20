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

	// Gerador de números aleatórios baseado na distribuição Normal.
	private Sim_normal_obj delay;
	private Sim_random_obj prob;
	
	public Facade(String name, double average, double variance) {
		super(name);
		
		this.in = new Sim_port("In");
		this.outGet = new Sim_port("Get");
		this.outPost = new Sim_port("Post");
		
		add_port(in);
		add_port(outGet);
		add_port(outPost);
		
		this.delay = new Sim_normal_obj("Delay", average, variance);
		this.prob = new Sim_random_obj("Probability");
		
		add_generator(delay);
		add_generator(prob);
	}

	public void body() {
		while (Sim_system.running()) {
			//Esta classe representa eventos que são passados ​​entre as entidades na simulação.
			Sim_event e = new Sim_event();
			sim_get_next(e);
			
			sim_process(delay.sample());
			
			sim_completed(e);
			
			double p = prob.sample();
			
			if (p < 0.5) {
				// 50% de chance para uma request GET.
				sim_schedule(outGet, 0.0, 1);
				sim_trace(1, "GET Request\n");
			} else {
				// 50% de chance para uma request POST.
				sim_schedule(outPost, 0.0, 1);
				sim_trace(1, "POST Request\n");
			}
		}
	}
}
