 package client;

 import java.io.Serializable;

 public class DatosCliente implements Serializable {
		
		private String nombre;
	    private int puntuacion;
	    private String prName;
	    private String prSpeed;
	    private int ram;
	     
		private int cores;
	    private long gbTotales;
	    private double gbLibres;
	    private String sistemaOperativo;
	    private double mem;
	  

		public DatosCliente(String nombre, int puntuacion, String prName, String prSpeed, int ram, double mem,int cores,
				long gbTotales, double gbLibres, String sistemaOperativo) {
			super();
			this.nombre = nombre;
			this.puntuacion = puntuacion;
			this.prName = prName;
			this.prSpeed = prSpeed;
			this.ram = ram;
			this.mem=mem;
			this.cores = cores;
			this.gbTotales = gbTotales;
			this.gbLibres = gbLibres;
			this.sistemaOperativo = sistemaOperativo;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public int getPuntuacion() {
			return puntuacion;
		}

		public void setPuntuacion(int puntuacion) {
			this.puntuacion = puntuacion;
		}

		public String getPrName() {
			return prName;
		}

		public void setPrName(String prName) {
			this.prName = prName;
		}

		public String getPrSpeed() {
			return prSpeed;
		}

		public void setPrSpeed(String prSpeed) {
			this.prSpeed = prSpeed;
		}

		public int getRam() {
			return ram;
		}

	 

		public int getCores() {
			return cores;
		}

		public void setCores(int cores) {
			this.cores = cores;
		}

		public long getGbTotales() {
			return gbTotales;
		}

		public void setGbTotales(long gbTotales) {
			this.gbTotales = gbTotales;
		}

		public double getGbLibres() {
			return gbLibres;
		}

		public void setGbLibres(double gbLibres) {
			this.gbLibres = gbLibres;
		}

		public String getSistemaOperativo() {
			return sistemaOperativo;
		}

		public void setSistemaOperativo(String sistemaOperativo) {
			this.sistemaOperativo = sistemaOperativo;
		}
	    
		
		public double getMemoria() {
			return mem;
		}

		public void setMem(double mem) {
			this.mem = mem;
		}

		public void setRam(int ram) {
			this.ram = ram;
		}

	    
	    

	}
