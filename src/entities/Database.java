package entities;

import eduni.simjava.*;
import eduni.simjava.distributions.Sim_negexp_obj;

public class Database extends Sim_entity{
	private Sim_port inFromServer, outToServer;
	
	private Sim_negexp_obj delay;
	
	private Sim_stat stat;
	
	public Database(String name, double average) {
		super(name);
		
		this.delay = new Sim_negexp_obj("Delay", average);
		
		this.stat = new Sim_stat();
		
		this.inFromServer = new Sim_port("InFromServer");
		this.outToServer = new Sim_port("OutToServer");
		
		add_port(inFromServer);
		add_port(outToServer);
		add_generator(delay);

		this.stat.add_measure(Sim_stat.ARRIVAL_RATE);
		this.stat.add_measure(Sim_stat.QUEUE_LENGTH);
		this.stat.add_measure(Sim_stat.WAITING_TIME);
		this.stat.add_measure(Sim_stat.UTILISATION);
		this.stat.add_measure(Sim_stat.RESIDENCE_TIME);
		
		set_stat(stat);
	}

	public void body() {
		while (Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e);
			sim_process(delay.sample());
			sim_completed(e);
			
			sim_trace(1, "Request from the server to the database.");
			sim_schedule(outToServer, 0.0, 1); 
		}
	}
}
