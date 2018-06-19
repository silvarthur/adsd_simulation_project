import eduni.simjava.*;

public class Database extends Sim_entity{
	private Sim_port out;
	
	public Database(String name) {
		super(name);
		out = new Sim_port("Out");
		add_port(out);
	}

}
