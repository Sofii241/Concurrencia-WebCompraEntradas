package tiqueto.model;

import tiqueto.EjemploTicketMaster;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PromotoraConciertos extends Thread {

	final WebCompraConciertos webCompra;

	//CREO UNA VARIABLE ACUMULADORA QUE SUME LAS ENTRADAS QUE SE VA REPONIENDO PARA COMPARARLAS CON EL TOTAL QUE VOY A VENDER
	static int entradasAcumuladas=0;

	public PromotoraConciertos(WebCompraConciertos webCompra) {
		super();
		this.webCompra = webCompra;
	}

	@Override
	public void run() {


		//MIENTRAS LAS ENTRADAS QUE VOY REPONIENDO (SUMADAS EN VARIABLE ENTRADAS ACUMULADAS) SEA MENOR QUE EL TOTAL QUE VOY A VENDER
		//Y MIENTRAS NO HAYA ENTRADAS --> REPONGO ENTRADAS EN LA WEB
		while (entradasAcumuladas<EjemploTicketMaster.TOTAL_ENTRADAS && !webCompra.hayEntradas()){
			mensajePromotor("EMPIEZO LA REPOSICION DE ENTRADAS");
			webCompra.reponerEntradas(EjemploTicketMaster.REPOSICION_ENTRADAS);

			//TRAS REPONER ENTRADAS DUERME ENTRE 3 Y 8 SEGUNDOS
			mensajePromotor("Entradas lanzadas...voy a dormir...");
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(3000, 8000));
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			//SUMO A MI VARIABLES ACUMULADORA EL NUMERO DE REPOSICION DE ENTRADAS PARA CONTROLAR EL TOTAL QUE PUEDO VENDER
			//Y ALIMENTAR LA CONDICION WHILE
			entradasAcumuladas+= EjemploTicketMaster.REPOSICION_ENTRADAS;
		}

		//CUANDO SE LLEGUE AL MAXIMO DE ENTRADAS TOTALES QUE SE PUEDE VENDER SE CIERRA LA VENTA
		mensajePromotor("NO HAY MAS ENTRADAS PARA REPONER");
		webCompra.cerrarVenta();


	}
	
	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajePromotor(String mensaje) {
		System.out.println(System.currentTimeMillis() + "| Promotora: " + mensaje);
		
	}
}
