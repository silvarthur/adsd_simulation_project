import eduni.simjava.*;

public class LogFile extends Sim_entity{
	private Sim_port out;
	
	public LogFile(String name) {
		super(name);
		out = new Sim_port("Out");
		add_port(out);
	}

}
