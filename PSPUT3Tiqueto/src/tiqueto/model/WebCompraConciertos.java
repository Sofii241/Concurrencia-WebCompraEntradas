package tiqueto.model;

import tiqueto.EjemploTicketMaster;
import tiqueto.IOperacionesWeb;

public class WebCompraConciertos implements IOperacionesWeb{

	boolean ventaCerrada = false;
	public WebCompraConciertos() {
		super();
	}


	@Override
	public synchronized boolean comprarEntrada() {

		//MIENTRAS NO HAYA ENTRADAS QUE COMPRAR ESPERO
		while (!hayEntradas()) {
			try {
                wait();
            } catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println("Thread interrupted: " +e);
            }
		}
		//SI HAY ENTRADAS DISPONIBLES (PORQUE SE HAN RESPUESTO) ENTRO A COMPRAR
			EjemploTicketMaster.ENTRADAS_DISPONIBLES -= 1;

            //NOTIFICO QUE HE COMPRADO ENTRADAS
            notifyAll();
            mensajeWeb("¡ENTRADA COMPRADA!");

			//NOTIFICO LAS ENTRADAS QUE AUN QUEDA DISPONIBLES PARA COMPRAR
			mensajeWeb("Quedan " + entradasRestantes() + " entradas disponibles");
			return true;

	}


	@Override
	public synchronized int reponerEntradas(int numeroEntradas) {
		//SI AUN HAY ENTRADAS EN VENTA ESPERO PARA REPONERLAS
		while (hayEntradas()) {
			try {
                wait();
            } catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println("Thread interrupted: " +e);
            }
		}
		//SI NO HAY RNTRADAS PARA VENDER REPONGO ENTRADAS
			EjemploTicketMaster.ENTRADAS_DISPONIBLES = EjemploTicketMaster.ENTRADAS_DISPONIBLES + numeroEntradas;

			//NOTIFICO QUE HE REPUESTO ENTRADAS
			notifyAll();
			mensajeWeb("¡ENTRADAS REPUESTAS!");

		//DEVUELVO NUMERO DE ENTRADAS DISPONIBLES
		return entradasRestantes();
	}


	@Override
	public synchronized void cerrarVenta() {
		// SE CIERRA LA VENTA EN EL MOMENTO QUE SE HAYA VENDIDO EL TOTAL DE ENTRADAS QUE ESTA FIJADO PARA LA VENTA
		mensajeWeb("SE HAN AGOTADO LAS ENTRADAS");

			 //NOTIFICO QUE SE CIERRA LA VENTA
			ventaCerrada=true;
			 notifyAll();
			 mensajeWeb("¡VENTA CERRADA!");
	}


	@Override
	public synchronized boolean hayEntradas() {
		//SI LAS ENTRADAS DISPONIBLES PARA VENDER NO SON 0, AUN ME QUEDAN ENTRADAS POR VENDER
		if (EjemploTicketMaster.ENTRADAS_DISPONIBLES!=0) {
			return true;
		}
		//SI TENGO 0 ENTRADAS PARA VENDER YA NO HAY MAS ENTRADAS
		else {
			return false;
		}
	}


	@Override
	public synchronized int entradasRestantes() {
		//NUMERO DE ENTRADAS QUE QUEDAN DISPONIBLES PARA VENDER
		return EjemploTicketMaster.ENTRADAS_DISPONIBLES;
	}


	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajeWeb(String mensaje) {
		System.out.println(System.currentTimeMillis() + "| WebCompra: " + mensaje);
		
	}

}
