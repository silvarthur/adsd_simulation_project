package entities;
import eduni.simjava.*;

public class LogFile extends Sim_entity{
	private Sim_port in;
	
	public LogFile(String name) {
		super(name);
		in = new Sim_port("Out");
		
		add_port(in);
	}

}
