import eduni.simjava.Sim_system;
import entities.Facade;
import entities.Request;
import entities.RequestType;
import entities.Server;
import entities.Souce;

public class Simulation {

	public static void main(String[] args) {
		/**
		 * Inicializa o Sim_system
		 */
		Sim_system.initialise();
		Souce souce = new Souce("Souce", 100);
		Facade facade = new Facade("Facede", 50, 10);
		Request requestGet = new Request("RequestGET", RequestType.GET);
		Request requestPost = new Request("RequestPOST", RequestType.POST);
		Server server = new Server("Server", 120);
		
		
		Sim_system.link_ports("Souce", "Out", "Facede", "In");
		Sim_system.link_ports("Facede", "Get", "RequestGET", "In");
		Sim_system.link_ports("Facede", "Post", "RequestPOST", "In");
		Sim_system.link_ports("RequestGET", "Server", "Server", "In");
		Sim_system.link_ports("RequestPOST", "Server", "Server", "In");
		
		/**
		 * Configura o rastreio para o simulador para apenas entidade (default, entity, event)
		 */
		Sim_system.set_trace_detail(false, true, false);
		/**
		 * Execuçao a simulação
		 */
		Sim_system.run();
	}

}
