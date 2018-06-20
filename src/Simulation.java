import eduni.simjava.Sim_system;
import entities.Facade;
import entities.LogFile;
import entities.Request;
import entities.RequestType;
import entities.Server;
import entities.Source;

public class Simulation {
	public static void main(String[] args) {
		// Initializes Sim_system.
		Sim_system.initialise();
		Source source = new Source("Source", 100);
		Facade facade = new Facade("Facade", 50, 10);
		LogFile logFile = new LogFile("LogFile");
		Request requestGet = new Request("GETRequest", RequestType.GET);
		Request requestPost = new Request("POSTRequest", RequestType.POST);
		Server server = new Server("Server", 120);
		
		Sim_system.link_ports("Source", "Out1", "Facade", "In");
		Sim_system.link_ports("Source", "Out2", "LogFile", "In");
		
		Sim_system.link_ports("Facade", "Get", "GETRequest", "In");
		Sim_system.link_ports("Facade", "Post", "POSTRequest", "In");
		
		Sim_system.link_ports("GETRequest", "Server", "Server", "In");
		
		Sim_system.link_ports("POSTRequest", "Server", "Server", "In");

		// Configura o rastreio para o simulador para apenas entidade (default, entity, event)
		Sim_system.set_trace_detail(false, true, false);

		// Executes the simulation.
		Sim_system.run();
	}
}
