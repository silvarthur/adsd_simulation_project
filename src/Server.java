import eduni.simjava.*;

public class Server extends Sim_entity{
	private Sim_port out;
	
	public Server(String name) {
		super(name);
		out = new Sim_port("Out");
		add_port(out);
	}

}
