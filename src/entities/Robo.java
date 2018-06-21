package entities;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import eduni.simjava.distributions.Sim_poisson_obj;

public class Robo extends Sim_entity {
	private Sim_port in, servePort, facadePort, logFilePort;
	private Sim_poisson_obj delay;
	
	public Robo(String name, double average) {
		super(name);
		
		this.delay = new Sim_poisson_obj("Delay", average);
		add_generator(delay);
		
		this.in = new Sim_port("In");
		add_port(in);
		this.servePort = new Sim_port("ServePort");
		add_port(servePort);
		
		this.facadePort = new Sim_port("FacadePort");
		add_port(facadePort);
		this.logFilePort = new Sim_port("LogFilePort");
		add_port(logFilePort);
	}
	
	public void body() {
		while (Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e);
			sim_trace(1, "Robo iniciando solicitação ao facade.");
			
			sim_process(delay.sample());
			sim_completed(e);
			
			sim_trace(1, "Solicitação ao facade completa.");
			
			sim_trace(1, "");
			try {
				String nameEvent = Sim_system.get_entity(e.get_src()).get_name();
				
				if (nameEvent.equals("Server")) {
					sim_trace(1, "Salvando no Log File.");
		    		sim_schedule(logFilePort, 0.0, 1);    	
				} else {
					sim_trace(1, "Enviando solicitação ao Facade.");
		    		sim_schedule(facadePort, 0.0, 1);    	
				}
			} catch (eduni.simjava.Sim_exception ex) {
		    	 System.out.println(ex.getMessage());
			}
			
		}
	}
}
