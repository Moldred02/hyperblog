package client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.*;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import client.DatosCliente;


public class ultimo {
	
	static int numax=3;
	private static boolean condicion=true;
	private static String host;
	private static int puntuacion=1; 
	static DatagramSocket[] client = new DatagramSocket[4];
	
	
	public static void main(String[]args) throws Exception
	{
		condicion=true;
          while(true)
          {
        	  
        	  if(puntuacion==0)
        	  {
        		  server(); 
        	  }
        	  else if(puntuacion==1)
        	  {
        		  //prueba2.Client_compu3.cliente();
        		  cliente();
        	  }
        	   
          }
			
		}
	
	
	public static void server() throws Exception
	{
		 condicion=true;
		System.out.println("Eres el server");
		while(condicion==true )
		 {
			 
			 
		 
		DatagramSocket ss = null;
	 
		try {
			
		JFrame ventana;
		 
		
 		 
        ventana= new JFrame("Rankeo");
        
         
         ss= new DatagramSocket(5432);
         ss.setBroadcast(true);
         String mensaje="Soy el server";
		 
          
        			System.out.println("Esperando conexion...");
        			
        			InetAddress[] clientAddresses = new InetAddress[4]; 
        			int tClient=0;
        			boolean dec=false;
        			boolean c=true;
        			//Thread.sleep(3000);
        		
        			
        		//	 boolean allConnect=false;
                    
        			
        			while(  tClient<numax)
        			{
        				 
        				byte[] buffer = new byte[1024];
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        ss.receive(packet);

                        // Verifica si el mensaje recibido es una solicitud de descubrimiento
                        
                        String solicitud = new String(packet.getData(), 0, packet.getLength());
                         
                        if (solicitud.equals("¿Quién es el servidor?")) {
                        	
                            // Obtiene la dirección IP y el puerto del cliente que hizo la solicitud
                        	
                            InetAddress direccionCliente = packet.getAddress();
                            System.out.println(direccionCliente);
                            int puertoCliente = packet.getPort();

                            // Verifica si el cliente ya se unió previamente
                            boolean clienteExistente = false;
                            for (int i = 0; i < tClient; i++) {
                                if (clientAddresses[i].equals(direccionCliente)) {
                                    clienteExistente = true;
                                     
                                    
                                }
                            }

                            if (!clienteExistente) {
                                // Almacena el socket y la dirección IP del cliente
                                DatagramSocket socketCliente = new DatagramSocket();
                                client[tClient] = socketCliente;
                                clientAddresses[tClient] = direccionCliente;

                                // Envía la dirección IP del servidor al cliente
                                String men= "Conectando al servidor";
                                byte[] respuesta = men.getBytes();
                                DatagramPacket respuestaPacket = new DatagramPacket(respuesta, respuesta.length, direccionCliente, puertoCliente);
                                ss.send(respuestaPacket);

                                // Incrementa el contador de clientes conectados
                                tClient++;

                                System.out.println("Cliente " + tClient + " conectado");

                                // Envía mensaje a todos los clientes indicando que pueden avanzar3
                                if (tClient == numax) {
                                	Thread.sleep(1500);
                                	 for (int i = 0; i < numax; i++) {
                                         String mensajeAvanzar = "S";
                                         byte[] bufferAvanzar = mensajeAvanzar.getBytes();
                                         DatagramPacket paqueteAvanzar = new DatagramPacket(bufferAvanzar, bufferAvanzar.length, clientAddresses[i], 2222);
                                         client[i].send(paqueteAvanzar);
                                         System.out.println("ip::" + clientAddresses[i]);
                                     }
                                
                                }
                                
                    			 
                                
                            }

                            
                        }
        				 
        		
              				
              			
        					 
        					 
        			}
        			
        			
        			
        		  
        				 
        			 
        			 
        			 
        			 
        			 
        			
        			try
        			{
        				System.out.println("A continuacion el programa:");

        				//Conexiones
        				String [] nom = new String[tClient+1];
        				String [] pn = new String[tClient+1];
        				String [] ps= new String[tClient+1];
        				String [] os = new String[tClient+1];
        				int [] punt = new int [tClient+1];
        				int [] ram = new int [tClient+1];
        				double[] cpu = new double [tClient+1];
        				long [] gbt = new long[tClient+1];
        				int [] cor = new int [tClient+1];
        				double [] gbf = new double [tClient+1];
        				String [] ip= new String[tClient+1];
        				
        				String [] cip= new String[tClient+1];
        				String saludo="La informacion se envio correctamente al server en: "+System.currentTimeMillis()+" milisegundos";

        				 
        				System.out.println("Prueba");
        				for(int s=0; s<tClient; s++)
        				{
        					 
        					 
        					
        					
        					byte[] buffer = new byte[1024];
 				
        	                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
   
        	                ss.receive(packet);
        	                
        	                
        	                int numBytesRecibidos = packet.getLength();
        	                
        	                
        	                byte[] datosRecibidos = Arrays.copyOf(buffer, numBytesRecibidos);
        	                
        	              
        	    
        	            
        	              DatosCliente datosCliente=deserialize(datosRecibidos);
        	              
        	    
        	               
        	                InetAddress direccionCliente = packet.getAddress();
        	                 
        	                int puertoCliente = packet.getPort();

        	       
        	                 
        	                
        	                System.out.println("Se conectaron desde la IP "+direccionCliente.toString());
        	                
        					ip[s]= direccionCliente.toString();
        					cip[s]=ip[s];
        			 
        					  nom[s] = datosCliente.getNombre();
        					  System.out.println(nom[s]);
        		              punt[s] = datosCliente.getPuntuacion();
        		              pn[s] = datosCliente.getPrName();
        		              ps[s] = datosCliente.getPrSpeed();
        		              ram[s] = datosCliente.getRam();
        		              cpu[s]= datosCliente.getMemoria();
        		              cor[s] = datosCliente.getCores();
        		              gbt[s] = datosCliente.getGbTotales();
        		              gbf[s] = datosCliente.getGbLibres();
        		              os[s] = datosCliente.getSistemaOperativo();
        				}
        				
        				 
        				 
        				 
        				 
        				
        				
        				 
        		        
        		       
        		        
        				//Informacion del server
        				
         				ip[tClient]=InetAddress.getLocalHost().getHostAddress();
         				
         				cip[tClient]=ip[tClient];

         				String ipServer=ip[tClient];
         				System.out.println("ipserver:"+ipServer);
        			    nom[tClient]=System.getProperty("user.name");;
        				
        				OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        	 		    
        				 
        				 
        			 
        				 
        				 
        				
        				String osVersion = osBean.getName(); // Sistema operativo 
        				os[tClient]=osVersion;
        			    
        				int processorNumber = osBean.getAvailableProcessors();
        				
        				
        			   String processorName = getProcessor("name");
        			 pn[tClient]=processorName;   
        			    
        		 
        			 String valorStr = getProcessor("loadpercentage");
        			 int cpum = 0; // Valor por defecto en caso de que la conversión falle
        			 try {
        			     cpum = Integer.parseInt(valorStr);
        			 } catch (NumberFormatException e) {
        			     e.printStackTrace();
        			 }
        			    ram[tClient]=cpum;
        			   
        			    double memoria = 0;
        	       		   
        	       		 try {
        	 	    	    Process process = Runtime.getRuntime().exec("wmic OS get FreePhysicalMemory, TotalVisibleMemorySize /Value");
        	 	    	    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        	 	    	    String line;
        	 	    	    long freeMemory = 0;
        	 	    	    long totalMemory = 0;

        	 	    	    while ((line = reader.readLine()) != null) {
        	 	    	        String[] parts = line.split("=");
        	 	    	        if (parts.length >= 2) {
        	 	    	            String key = parts[0].trim();
        	 	    	            String value = parts[1].trim();

        	 	    	            if (key.equals("FreePhysicalMemory")) {
        	 	    	                freeMemory = Long.parseLong(value);
        	 	    	            } else if (key.equals("TotalVisibleMemorySize")) {
        	 	    	                totalMemory = Long.parseLong(value);
        	 	    	            }
        	 	    	        }
        	 	    	    }

        	 	    	    memoria = ((double) (totalMemory - freeMemory) / totalMemory) * 100;
        	 	    	     

        	 	    	    int exitCode = process.waitFor();
        	 	    	    
        	 	    	} catch (IOException | InterruptedException e) {
        	 	    	    e.printStackTrace();
        	 	    	}
        	       		 
        	       		 cpu[tClient]=memoria;
        	       		 
        			    
        	       		 
        	       		 
        			    String processorSpeed = osBean.getArch(); //Velocidad
        			    ps[tClient]=processorSpeed;
        			 
        		        
        		        String processorModel = System.getenv("PROCESSOR_IDENTIFIER");//Modelo de procesador
        	 	        
        		        Long freespace=Runtime.getRuntime().totalMemory()/1000;
        		        int numCores = Runtime.getRuntime().availableProcessors(); //Numero de nucleos
        		        cor[tClient]=numCores;
        		        
        		        File[] roots = File.listRoots();
        		        long totalSpace = 0;
        		        for (File root : roots) {
        		            totalSpace += root.getTotalSpace();
        		        }
        		        long gbTotalSpace = totalSpace / (1024 * 1024 * 1024);
        		        gbt[tClient]=gbTotalSpace;
        		    	 
        		        File disco = new File("C:"); // Reemplaza "C:" con la letra de la unidad de disco que quieras calcular
            	        long espacioLibreGB = disco.getFreeSpace();
            	        double espacioLibre = (double) espacioLibreGB / (1024 * 1024 * 1024);
            		   gbf[tClient]=freespace;
        		        
        		        
        	 		 
         				punt[tClient]=0;
         				
         				
         				  //AQUI EMPIEZA
         			      //AQUI EMPIEZA
         			        
         			        ///parte estatica
         			        
         			      
         			        
         			        switch(processorName)
         			        {
         			        	case "Intel(R) Core(TM) i7-9750H CPU @ 2.60GHz":
         			        		punt[tClient]+=600;
         			        		break;
         			        	case "AMD Ryzen 7 5700U with Radeon Graphics":
         			        		punt[tClient]+=450;
         			        		break;
         			        	case "ADM Ryzen 5 3500U with Radeon Vega Mobile Gfx":
         			        		punt[tClient]+=400;
         			        		break;
         			        	case "Intel(R) Core(TM) i5-7300HQ CPU @ 2.50GHz":
         			        		punt[tClient]+=300;
         			        		break;
         			        		
         			        }
         			        switch(numCores) {
         			        	case 4:
         			        		punt[tClient]+=400;
         			        		break;
         			        	case 6:
         			        		punt[tClient]+=600;
         			        		break;
         			        	case 8:
         			        		punt[tClient]+=800;
         			        		break;
         			        	case 12:
         			        		punt[tClient]+=1000;
         			        		break;
         			        	case 16:
         			        		punt[tClient]+=1200;
         			        		break;
         			        }
         			        
         			        switch(osVersion) {
         			        	case "Windows 10":
         			        		punt[tClient]+=500;
         			        		break;
         			        	case "Windows 11":
         			        		punt[tClient]+=700;
         			        		break;
         			        }
         			        
         			        switch(processorSpeed) {
         			        	case "amd64":
         			        		punt[tClient]+=500;
         			        		break;
         			        	case "x86":
         			        		punt[tClient]+=300;
         			        		break;
         			        		
         			        }
         			        
         			        
         			        
         			       //parte dinamica
             			    
          			      // punt[tClient]+=freespace*2;

             		        //parte dinamica
             		        
             		        if(cpu[tClient]>=0 && cpu[tClient]<=7)
             		        {
             		        	punt[tClient]-=50;
             		        }
             		        else if(cpu[tClient]>=8 && cpu[tClient]<=15)
             		        {
             		        	punt[tClient]-=150;
             		        }
             		        else if(cpu[tClient]>=16 && cpu[tClient]<=30)
             		        {
             		        	punt[tClient]-=250;
             		        }
             		        else if(cpu[tClient]>=30 && cpu[tClient]<=50)
             		        {
             		        	punt[tClient]-=350;
             		        }
             		     else if(cpu[tClient]>=51 && cpu[tClient]<=70)
          		        {
             		    	punt[tClient]-=500;
          		        }
          		        else if(cpu[tClient]>=71 && cpu[tClient]<=80)
          		        {
          		        	punt[tClient]-=650;
          		        }
          		        else if(cpu[tClient]>=81 && cpu[tClient]<=100)
          		        {
          		        	punt[tClient]-=750;
          		        }
             		        
             		        
             		     if(ram[tClient]>=0 && ram[tClient]<=7)
          		        {
             		    	punt[tClient]+=700;
          		        }
          		        else if(ram[tClient]>=8 && ram[tClient]<=15)
          		        {
          		        	punt[tClient]+=500;
          		        }
          		        else if(ram[tClient]>=16 && ram[tClient]<=30)
          		        {
          		        	punt[tClient]+=300;
          		        }
          		        else if(ram[tClient]>=30 && ram[tClient]<=50)
          		        {
          		        	punt[tClient]+=200;
          		        }
          		     else if(ram[tClient]>=51 && ram[tClient]<=70)
       		        {
          		    	punt[tClient]+=100;
       		        }
       		        else if(ram[tClient]>=71 && ram[tClient]<=100)
       		        {
       		        	punt[tClient]+=50;
       		        }
         		        
         		        int capsula=0;
         		        String nombre="";
         		        int c1=0;
         		        int c2=0;
          		       double c5=0;
         		        long c3=0;
         		        double c4=0;
         		       String prn="";
         		       String psp="";
         		       String ovn="";
         		       String ipp="";
         		        
         		        for(int i=0; i<tClient+1; i++) //Rankea del mayor al menor
         		        {
         		        	for(int j=0; j<tClient+1; j++)
         		        	{
         		        		if(punt[i]>punt[j])
         			        	{
         		        			capsula=punt[i]; //para guardar
        			        		 nombre=nom[i]; //esta
        			        		 c1=cor[i];
        			        	     c2=ram[i];
        			        	     c3=gbt[i];
        			        	     c4=gbf[i];
        			        	     c5=cpu[i];
        			        	     prn=pn[i];
        			        	     psp=ps[i];
        			        		 ovn=os[i];
        			        		 ipp=ip[i];
        			        		 
        			        		 
        			        		 
        			        		 nom[i]=nom[j];
        			        		punt[i]=punt[j];
        			        		cor[i]=cor[j];
       			        	     ram[i]=ram[j];
       			        	     cpu[i]=cpu[j];
       			        	     gbt[i]=gbt[j];
       			        	     gbf[i]=gbf[j];
       			        	     pn[i]=pn[j];
        			        	 ps[i]=ps[j];
       			        		 os[i]=os[j];
        			        	 ip[i]=ip[j];
        			        		
        			        		nom[j]=nombre;
        			        		punt[j]=capsula;
        			        		ram[j]=c2;
          			        	     gbt[j]=c3;
          			        	     gbf[j]=c4;
          			        	     cpu[j]=c5;
          			        	     pn[j]=prn;
           			        	     ps[j]=psp;
          			        		 os[j]=ovn;
          			        		 ip[j]=ipp;
         			        		
         			        		
         			        		  
         			        	}
         		        	}
         		        	 
         		        	capsula=0;
         		        }
         		        
        		        
         		       JTable tabla;
       		        
       		        String [][] datos = new String[tClient+1][11]; //lectura de la tabla
       		        
       		        for (int i=0; i<tClient+1; i++)
       		        {
       		        	datos[i][0]=ip[i];
       		        	datos[i][1]=nom[i];
       		        	datos[i][2]=pn[i];
       		        	datos[i][3]=ps[i];
       		        	datos[i][4]=Integer.toString(ram[i]);
       		        	datos[i][5]=Double.toString(cpu[i]);
       		        	datos[i][6]=Integer.toString(cor[i]);
       		        	datos[i][7]=Long.toString(gbt[i]);
       		        	datos[i][8]=Double.toString(gbf[i]);
       		        	datos[i][9]=os[i];
       		        	datos[i][10]=Integer.toString(punt[i]);
       		        	
       		        	    
       		        }
       		        
       		        String[] cabezera= {"ip","nombre","nombre_procesador","velocidad","%cpu","%memoria","nucleos","gb_total","gb_libres","Sistema_op","rankeo"};
       		        
       		                	
 
        		        ventana.setLayout(new FlowLayout());
        		        ventana.setSize(700,500);
        		        tabla= new JTable(datos, cabezera);
        		        JScrollPane JS= new JScrollPane(tabla);
        		        JS.setPreferredSize(new Dimension(650,300));
        		        ventana.add(JS);
        		        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		      //  ventana.pack();
        		        ventana.setVisible(true);
        		        
        		       // System.out.println("Rankeo del mayor al menor");
        		       // for(int i=0;i<4; i++)
        		       // {
        		       // 	System.out.println("\nNombre del usuario: "+nom[i] + "\n Puntuacion: "+punt[i]+"\n\n");
        		       // }
        		        
        		        MensajeSender sender = new MensajeSender(ss);
        		        
        		        
        		        
        		        System.out.println("primer ip:"+ip[0]);
        		        
        		        
        		        for (int i=0; i<tClient; i++)
        		        {
        		            
        		      
        		        	
        		        	InetAddress direccion= InetAddress.getByName(cip[i].substring(1));
        		        	System.out.println("primer mensaje:"+cip[i].substring(1));
        		        	sender.enviarMensaje("Tu informacion le ha llegado al server", direccion, 5555);
        		        	  
              		       
              		     if(ip[0]!=ipServer)
            		       {
              		    	 
              		    	 InetAddress dir= InetAddress.getByName(cip[i].substring(1));
              		    	 
              		    	sender.enviarMensaje("1", dir  , 5555);//1 es por si habra cambio de cualquier tipo y 0 es que nada
              		    	System.out.println("segundo mensaje cambio:"+cip[i].substring(1));
            		       }
            		       else
            		       {
            		    	   sender.enviarMensaje("0", direccion, 5555);
            		    	   System.out.println("segundo mensaje normal:"+cip[i].substring(1));
            		    	  
          		        		   
          		        		  sender.enviarMensaje("3er mensaje:", direccion, 5555);
          		        		  System.out.println("3er mensaje xs:"+cip[i].substring(1));
          		        		  
          		        	  
            		    	 
            		       }
        		        }
        		        
        		        int contador=0;//Sistema para asegurar que solo haya un servidor
        		        
        		        //Condicion para cambiar de server
        		        if(ip[0]!=ipServer)
        		        {
        		        	
        		        	 
        		        	
        		        	
        		        	for(int i=0; i<tClient; i++)
        		        	{
        		        		
        		        		Thread.sleep(500);
        		        		InetAddress direccion= InetAddress.getByName(cip[i].substring(1));
        		        		InetAddress dir= InetAddress.getByName(clientAddresses[i].toString().substring(1));
        		        		
        		        		if((ip[0].equals(clientAddresses[i].toString())) && contador==0 )
        		        		{
        		        			System.out.println("client[i]:"+clientAddresses[i].toString().substring(1));
        		        			sender.enviarMensaje("||||||||||||Eres el nuevo servidor||||||||||||||||", dir, 5555);
         		        			 
        		        			contador++;
        		        		}
        		        		else if (!ip[0].equals(clientAddresses[i].toString()))
        		        		{
        		        			
        		        			sender.enviarMensaje("Se cambiara de server", dir, 5555);
       		        			 System.out.println("tercer mensaje cliente:"+clientAddresses[i].toString().substring(1));

        		        			
        		        			//sender.enviarMensaje(ip[i].toString(), direccion, 5432);
        		        			
        		        			 
        		        		}
        		        		 
        		        		
        		        	}
        		        	
        		        	System.out.println("|||||||||Se cambiara de server |||||||||||||");
        		        	 
        		        
        		        	host=ip[0].toString();
        		        	host=host.substring(1);
        		        	System.out.println(host);
        		        	
        		        	 
        		           condicion = false;
        		           puntuacion=1;
        		        	
        		        	 
        		        	
        		        	 
        		        	 
        		        	
        		        	
        		        	
        		        }
        		     
        		         
        		     sender.cerrar();  
        		         
        				
        				 
        			}
        			catch(Exception e)
        			{
        				e.printStackTrace();
        			}
        			finally
        			{
        				 for(int i=0; i<tClient; i++)
        				 {
        					   
        					 
        					  
        						 
        					 
        					 
        						 client[i].close();
        					 
        				 }
        				System.out.println("Conexion cerrada..."+condicion);
        				
        			}
             	
            	 
            	 
            	 
         
        Thread.sleep(5000); 
        ventana.setVisible(false);
		
		}catch (Exception e) {
			numax-=1;
			 for(int i=0; i<numax; i++)
			 {
				 
					 client[i].close();
				 
			 }
			 ss.close();
			if(puntuacion==0)
      	  {
      		  server(); 
      	  }
      	  else if(puntuacion==1)
      	  {
      		  //prueba2.Client_compu3.cliente();
      		  cliente();
      	  }
			
			
		}
		finally {
			ss.close();
		}
		
		if(condicion==false)
		{
			 
			 
			
			puntuacion=1;
			Thread.sleep(2000);
		
			
		}
		 
		
		 
			 
		 }
		 
  
		 
			
	}

	public static void cliente() throws Exception

	
        
	{
		 
		condicion=true;
		 
		 
		while(condicion==true )
		 {
			 
			System.out.println("Empezamos de nuevo");
			DatagramSocket socketCliente  = null;
		  
		 
		try
		{
			 
			
		   socketCliente = new DatagramSocket();
 
			 // Configurar la dirección de broadcast
            InetAddress direccionBroadcast = InetAddress.getByName("192.168.0.255");

            // Enviar un mensaje de solicitud de descubrimiento al servidor
            String solicitud = "¿Quién es el servidor?";
            byte[] bufferEnvio = solicitud.getBytes();
            DatagramPacket paqueteEnvio = new DatagramPacket(bufferEnvio, bufferEnvio.length, direccionBroadcast, 5432);
            socketCliente.send(paqueteEnvio);
		     
            byte[] bufferRecepcion = new byte[1024];
            DatagramPacket paqueteRecepcion = new DatagramPacket(bufferRecepcion, bufferRecepcion.length);
            socketCliente.receive(paqueteRecepcion);

            // Se recibió una respuesta del servidor, se estableció la conexión
            String respuesta = new String(paqueteRecepcion.getData(), 0, paqueteRecepcion.getLength());
            System.out.println("Conexion establecida con el servidor ");

            
           
            
            // Esperar la señal de avance del servidor
            
            
            boolean recibidoAvance = false;
            DatagramSocket socketEspera = new DatagramSocket(2222);

            while (!recibidoAvance) {
                byte[] bufferRecepcionn = new byte[1024];
                DatagramPacket paqueteAvanzar = new DatagramPacket(bufferRecepcionn, bufferRecepcionn.length);
                socketEspera.receive(paqueteAvanzar);
                String mensajeAvanzar = new String(paqueteAvanzar.getData(), 0, paqueteAvanzar.getLength());
                System.out.println(mensajeAvanzar);
                
                if (mensajeAvanzar.equals("S")) {
                    recibidoAvance = true;
                    socketEspera.close();
                }
            }
            
             
                
               
            
             
            System.out.println("Seguimos");
       
       
            		
            		
           	try
       		{

       			String nombre=System.getProperty("user.name");
       			
       			
       			 
       	       
       			
       			OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        		    
       			String osVersion = osBean.getName(); // Sistema operativo 
       		    
       			int processorNumber = osBean.getAvailableProcessors();
       			
       		 String processorName = getProcessor("name");
    		    
    		    
       		String valorStr = getProcessor("loadpercentage");
       		int memoria = 0; // Valor por defecto en caso de que la conversión falle
       		try {
       		    memoria = Integer.parseInt(valorStr);
       		} catch (NumberFormatException e) {
       		    //e.printStackTrace();
       			memoria = 5;
       		}
       		 
       		 
       		 
     		   double cpu = 0;
     		   
     		 try {
	    	    Process process = Runtime.getRuntime().exec("wmic OS get FreePhysicalMemory, TotalVisibleMemorySize /Value");
	    	    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

	    	    String line;
	    	    long freeMemory = 0;
	    	    long totalMemory = 0;

	    	    while ((line = reader.readLine()) != null) {
	    	        String[] parts = line.split("=");
	    	        if (parts.length >= 2) {
	    	            String key = parts[0].trim();
	    	            String value = parts[1].trim();

	    	            if (key.equals("FreePhysicalMemory")) {
	    	                freeMemory = Long.parseLong(value);
	    	            } else if (key.equals("TotalVisibleMemorySize")) {
	    	                totalMemory = Long.parseLong(value);
	    	            }
	    	        }
	    	    }

	    	    cpu = ((double) (totalMemory - freeMemory) / totalMemory) * 100;
	    	     

	    	    int exitCode = process.waitFor();
	    	    
	    	} catch (IOException | InterruptedException e) {
	    	    e.printStackTrace();
	    	}
	     
	  

       		    
       		    String processorSpeed = osBean.getArch(); //Velocidad
       		    
       		    long usableRam = Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory();
       			double usableRamGB = (double) usableRam / (1024*1024*1024);
       		    
       		 
       	        
       	        String processorModel = System.getenv("PROCESSOR_IDENTIFIER");//Modelo de procesador
        	        
       	        Long freespace=Runtime.getRuntime().totalMemory()/1000;
       	        int numCores = Runtime.getRuntime().availableProcessors(); //Numero de nucleos
       	        
       	        File[] roots = File.listRoots();
       	        long totalSpace = 0;
       	        for (File root : roots) {
       	            totalSpace += root.getTotalSpace();
       	        }
       	        long gbTotalSpace = totalSpace / (1024 * 1024 * 1024);
       	        
       	        File disco = new File("C:"); // Reemplaza "C:" con la letra de la unidad de disco que quieras calcular
       	        long espacioLibreGB = disco.getFreeSpace();
       	        double espacioLibre = (double) espacioLibreGB / (1024 * 1024 * 1024);
       		   
       	        
       	        
        			
       			 
       	        System.out.println(nombre+": "+processorName +", "+processorSpeed+"GHz , "+ usableRamGB+" GB, "+numCores+"nucleos, "+gbTotalSpace+"gb, Espacio libre: "+ espacioLibre+ ", "+osVersion
       	        		+ "cpu:"+memoria+"%,memoria:"+cpu+"%");
       			
       	        //AQUI EMPIEZA
       	        int score=0;
       	        
       	        //AQUI EMPIEZA
       		      //AQUI EMPIEZA
       		        
       		        //parte estatica
       		        
       		       
       		        
       		        switch(processorName)
       		        {
       		        	case "Intel(R) Core(TM) i7-9750H CPU @ 2.60GHz":
       		        		score+=500;
       		        		break;
       		        	case "AMD Ryzen 7 5700U with Radeon Graphics":
       		        		score+=450;
       		        		break;
       		        	case "ADM Ryzen 5 3500U with Radeon Vega Mobile Gfx":
       		        		score+=400;
       		        		break;
       		        	case "Intel(R) Core(TM) i5-7300HQ CPU @ 2.50GHz":
       		        		score+=300;
       		        		break;
       		        		
       		        }
       		        switch(numCores) {
       		        	case 4:
       		        		score+=400;
       		        		break;
       		        	case 6:
       		        		score+=600;
       		        		break;
       		        	case 8:
       		        		score+=800;
       		        		break;
       		        	case 12:
       		        		score+=1000;
       		        		break;
       		        	case 16:
       		        		score+=1200;
       		        		break;
       		        }
       		        
       		        switch(osVersion) {
       		        	case "Windows 10":
       		        		score+=500;
       		        		break;
       		        	case "Windows 11":
       		        		score+=700;
       		        		break;
       		        }
       		        
       		        switch(processorSpeed) {
       		        	case "amd64":
       		        		score+=500;
       		        		break;
       		        	case "x86":
       		        		score+=300;
       		        		break;
       		        		
       		        }
       		        
       		        
       		        
       		     //parte dinamica
       		     if(cpu>=0 && cpu<=7)
  		        {
  		        	score-=50;
  		        }
  		        else if(cpu>=8 && cpu<=15)
  		        {
  		        	score-=150;
  		        }
  		        else if(cpu>=16 && cpu<=30)
  		        {
  		        	score-=250;
  		        }
  		        else if(cpu>=30 && cpu<=50)
  		        {
  		        	score-=350;
  		        }
       		     else if(cpu>=51 && cpu<=70)
    		        {
    		        	score-=500;
    		        }
    		        else if(cpu>=71 && cpu<=80)
    		        {
    		        	score-=650;
    		        }
    		        else if(cpu>=81 && cpu<=100)
    		        {
    		        	score-=750;
    		        }
       		        
       		        
       		     if(memoria>=0 && memoria<=7)
    		        {
    		        	score+=700;
    		        }
    		        else if(memoria>=8 && memoria<=15)
    		        {
    		        	score+=500;
    		        }
    		        else if(memoria>=16 && memoria<=30)
    		        {
    		        	score+=300;
    		        }
    		        else if(memoria>=30 && memoria<=50)
    		        {
    		        	score+=200;
    		        }
    		     else if(memoria>=51 && memoria<=70)
   		        {
   		        	score+=100;
   		        }
   		        else if(memoria>=71 && memoria<=100)
   		        {
   		        	score+=50;
   		        }
    		        
    		        
       		        
       		        //score+=freespace*2;
       		      
       	        
    		    DatosCliente datosCliente = new DatosCliente(nombre, score, processorName, processorSpeed, memoria, cpu, numCores, gbTotalSpace, espacioLibre, osVersion);
       		    String prueba= "pruebapai" ;
       		    
       		    
       		   ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
       		
       		   
       		   
       		ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
       		objectStream.writeObject(datosCliente);
       		objectStream.flush();
       		
       		byte[] datosClienteBytes = byteStream.toByteArray();
       		 
       		
       	    DatagramPacket enviar = new DatagramPacket(datosClienteBytes, datosClienteBytes.length, direccionBroadcast, 5432);
       		socketCliente.send(enviar);
       		
       		
       		
       		
              		
       		    
       		    
       		    
       		 String[] mensaje = new String[3];
       		 
       		MensajeReceiver receiver = new MensajeReceiver(5555);
       		 
       		 for(int i=0 ; i<3; i++)
       		 {
       			 
       		     
       		     mensaje[i]= receiver.recibirMensaje();
       		 
        	     System.out.println("recibido:"+mensaje[i]);
        			 
       			 
       		 }
       		 
       		 
       		 
       	     receiver.cerrar();
       	 
       	    
    	     
    	       
     	     
           	     if(mensaje[1].equals("1"))
           	     {
           	    	 
           	       
          	      System.out.println(mensaje[2]);
          	      if(mensaje[2].equals("||||||||||||Eres el nuevo servidor||||||||||||||||"))
          	      {
          	    	  
          	    	  puntuacion=0;
          	    	  condicion=false;
          	    	  
          	    	  
          	    	  
          	    	 
          	      }
          	      if(mensaje[2].equals("Se cambiara de server"))
          	      {
          	    	  

          	    	  
          	     
          	    	  puntuacion=1;
          	    	  condicion=false;
          	    	  
          	    	  
          	      }   
           	     }
       	      
       	        
       	         
       			
       	        
       		}
       		catch(Exception e)
       		{
       			e.printStackTrace();
       		}
       		 finally
       		 {
       			socketCliente.close();
    			  
       		 }
           	System.out.println("Se termino el programa");
            
           	  
 	
           
		}catch (Exception e) {
			numax-=1;
			
			if(puntuacion==0)
     	  {
     		  server(); 
     	  }
     	  else if(puntuacion==1)
     	  {
     		 
     		  cliente();
     	  }
		}
		System.out.println("puntuacionn:"+puntuacion);
		System.out.println("condicionn:"+condicion);
		if(puntuacion==1 && condicion==false)
		{
			Thread.sleep(8000);
		}
		else if(puntuacion==0 && condicion==false)
		{
			Thread.sleep(1000);
		}
		else
		{
			Thread.sleep(8000);
		}
		 
		  
		
		 
		
		 }		 
	}

	
	private static DatosCliente deserialize(byte[] data) throws IOException, ClassNotFoundException {
	    ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
	    ObjectInputStream objectStream = new ObjectInputStream(byteStream);
	    DatosCliente objeto = (DatosCliente) objectStream.readObject();
	    objectStream.close();
	    return objeto;
	}
	
		
	private static String getProcessor(String requisito) {
	    String name = "";
	    StringBuilder sb = new StringBuilder();
	    try {
	    	 
	    	
	      Process process = Runtime.getRuntime().exec(new String[] { "wmic", "cpu", "get", requisito });
	      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	      String line;
	       reader.readLine();
	      while((line=reader.readLine()) !=null )
	      {
	    	  sb.append(line);
	      }
	      
	      
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    
	    return sb.toString().trim();
	  }

	 
	 
}
