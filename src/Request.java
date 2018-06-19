import eduni.simjava.*;

public class Request extends Sim_entity{
	private Sim_port out;
	
	public Request(String name) {
		super(name);
		out = new Sim_port("Out");
		add_port(out);
	}

}
