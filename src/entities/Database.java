package entities;

import eduni.simjava.*;

public class Database extends Sim_entity{
	private Sim_port out;
	private Sim_port in;
	
	public Database(String name) {
		super(name);
		
		in = new Sim_port("In");
		add_port(in);
		
		out = new Sim_port("Out");
		add_port(out);
	}

}
