package entities;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import eduni.simjava.distributions.Sim_poisson_obj;

public class Robot extends Sim_entity {
	private Sim_port inFromSource, inFromServer, outToFacade, outToLogFile;
	private Sim_poisson_obj delay;
	
	public Robot(String name, double average) {
		super(name);
		
		this.delay = new Sim_poisson_obj("Delay", average);
		
		this.inFromSource = new Sim_port("InFromSource");
		this.inFromServer = new Sim_port("InFromServer");		
		this.outToFacade = new Sim_port("OutToFacade");
		this.outToLogFile = new Sim_port("OutToLogFile");
		
		add_port(inFromSource);
		add_port(inFromServer);
		add_port(outToFacade);
		add_port(outToLogFile);
		
		add_generator(delay);
	}
	
	public void body() {
		while (Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e);
			sim_trace(1, "Robot starting request to the facade entity.");
			
			sim_process(delay.sample());
			sim_completed(e);
			
			sim_trace(1, "Request to the facade entity finished.");
			
			sim_trace(1, "");
			try {
				String nameEvent = Sim_system.get_entity(e.get_src()).get_name();
				
				if (nameEvent.equals("Server")) {
					sim_trace(1, "Saving in the log file.");
		    		sim_schedule(outToLogFile, 0.0, 1);    	
				} else {
					sim_trace(1, "Sending request to the facade entity.");
		    		sim_schedule(outToFacade, 0.0, 1);    	
				}
			} catch (eduni.simjava.Sim_exception ex) {
		    	 System.out.println(ex.getMessage());
			}
			
		}
	}
}
