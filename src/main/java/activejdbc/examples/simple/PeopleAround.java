package activejdbc.examples.simple;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import java.util.Scanner;


public class PeopleAround {


	private Scanner lector = new Scanner(System.in);
	private int opcion;
	private String key;

	public PeopleAround() throws ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");
    	Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/Webber_DB?zeroDateTimeBehavior=convertToNull","root","852456diego852456");
       
	}


	public void listarOpciones()
	{
	
		System.out.println("1- Mostrar Clientes.\n2- Agregar Cliente.\n3- Eliminar Cliente\n4- Borrar todos los clientes\n5- Salir");
		
		System.out.print("Opcion: ");
		opcion = lector.nextInt();

		switch(opcion)
		{
			case 1:
				mostrarClientes();
			break;
			case 2:
				agregarCliente();
			break;
			case 3:
				eliminarCliente();
			break;
			case 4:
				borrarTodosClientes();
			break;
			case 5:
				Base.close();
				System.exit(0);
			break;
			default:
				System.out.println("Introduzca un valor valido");
			break;
		}

	}


	public void borrarTodosClientes()
	{
		System.out.println("Esta seguro de que quiere borrar todos los clientes? [S]|[N]");
		String respuesta = lector.next();

		if(respuesta.toLowerCase().equals("s"))
		{
			Cliente.deleteAll();
			System.out.println("\t\t******* Todos los clientes fueron borrados *******");
		}
		else
		{
			System.out.println("No se borro ningun cliente");
		}

		//Volver a la pantalla principal
		listarOpciones();
	}

	public void eliminarCliente()
	{
		System.out.println("-------Borrar Cliente-------\n");

		try
		{
			System.out.print("Introduzca el id del cliente a borrar:");
			Cliente cliente = Cliente.findFirst("ID = ?", lector.next());

			System.out.println("Esta seguro de que quiere borrar este cliente? [S]|[N]");
			String respuesta = lector.next();
			String clienteNombre = (String) cliente.get("NOMBRE");

			if(respuesta.toLowerCase().equals("s"))
			{
				
				cliente.delete();
				System.out.printf("\t\t** El cliente %s fue borrado **\n",clienteNombre);
			}
			else
			{
				System.out.println("No se borro ningun cliente");
			}
	
	    }
	    catch(NullPointerException ex)
	    {	
	    	System.out.println("Ese cliente no existe");	 
	    }
	    finally
	    {
	    	//Volver a la pantalla principal
	    	listarOpciones();	
	   	}
       	
	}

	public void agregarCliente()
	{
		Cliente cliente = new Cliente();

        System.out.print("Nombre:");
        cliente.set("NOMBRE",lector.next());

        System.out.print("Apellido:");
        cliente.set("APELLIDO",lector.next());

        System.out.print("Correo:");
        cliente.set("CORREO",lector.next());

        System.out.print("Telefono:");
        cliente.set("TELEFONO",lector.next());

        System.out.print("Direccion:");
        cliente.set("DIRECCION",lector.next());

        System.out.println();

        cliente.saveIt();

        //Va a pantalla opciones
        listarOpciones();
	}

	public void mostrarClientes()
	{
		System.out.println("\t\tTodos los clientes\n");

		LazyList<Cliente> clientes = Cliente.findAll();

		for (Cliente cliente : clientes) {
			System.out.printf("\tNombre: %s, ",cliente.get("NOMBRE"));
			System.out.printf("Apellido: %s, ",cliente.get("APELLIDO"));
			System.out.printf("Correo: %s, ",cliente.get("CORREO"));
			System.out.printf("Telefono: %s, ",cliente.get("TELEFONO"));
			System.out.printf("Direccion: %s\n\n",cliente.get("DIRECCION"));
			System.out.println("\t******************************************************************");
		}

		//Promtea por la salida
		do{
			System.out.println("\tEscriba \"volver\"");
			key = lector.next();
		}while(!key.equals("volver"));

		//Volver a la pantalla principal
		listarOpciones();
	}
    
    public static void main(String[] args)
    {
       try
       {
    		PeopleAround people = new PeopleAround();
       		people.listarOpciones();  
       }
       catch (ClassNotFoundException ex){
       		System.out.println(ex.getMessage());
       }     
     
    }
    
}