package entities;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_stat;
import eduni.simjava.Sim_system;
import eduni.simjava.distributions.Sim_normal_obj;
import eduni.simjava.distributions.Sim_random_obj;

public class Facade extends Sim_entity {
	private Sim_port inFromRobot, outToGet, outToPost;
	
	private Sim_normal_obj delay;
	private Sim_random_obj prob;
	
	private Sim_stat stat;
	
	public Facade(String name, double average, double variance) {
		super(name);
		
		this.delay = new Sim_normal_obj("Delay", average, variance);
		this.prob = new Sim_random_obj("Probability");
		
		stat = new Sim_stat();
		
		this.inFromRobot = new Sim_port("InFromRobot");
		this.outToGet = new Sim_port("OutToGet");
		this.outToPost = new Sim_port("OutToPost");
		
		add_port(inFromRobot);
		add_port(outToGet);
		add_port(outToPost);
		
		add_generator(delay);
		add_generator(prob);

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
				sim_schedule(outToGet, 0.0, 1);
				sim_trace(1, "GET Request.");
			} else {
				sim_schedule(outToPost, 0.0, 1);
				sim_trace(1, "POST Request.");
			}
		}
	}
}
