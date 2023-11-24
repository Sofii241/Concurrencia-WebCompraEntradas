package tiqueto.model;

import tiqueto.EjemploTicketMaster;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FanGrupo extends Thread {

	final WebCompraConciertos webCompra;
	int numeroFan;
	private String tabuladores = "\t\t\t\t";
	int entradasCompradas = 0;

	public FanGrupo(WebCompraConciertos web, int numeroFan) {
		super();
		this.numeroFan = numeroFan;
		this.webCompra = web;
	}
	
	@Override
	public void run() {


		//SI EL FAN NO A COMPRADO EL MAXIMO DE ENTRADAS QUE TIENE PERMITIDO ENTRA A COMPRAR EN LA WEB
		while (entradasCompradas < EjemploTicketMaster.MAX_ENTRADAS_POR_FAN){
			mensajeFan("VOY A COMPRAR ENTRADAS");
			webCompra.comprarEntrada();

			//MUESTRO POR PANTALLA QUE HE PODIDO COMPRAR UN NUM DETERMINADO DE ENTRADAS Y QUE EL HILO VA A DORMIR
			mensajeFan("He comprado entradas... voy a dormir");

			//TRAS COMPRAR LAS ENTRADAS DUERME ENTRE 1 Y 3 SEGUNDOS
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

			//ACTUALIZO NUMERO DE ENTRADAS COMPRADAS POR CADA VUELTA DEL BUCLE PARA ALIMENTAR LA CONDICION DEL BUCLE
			//Y PODER MOSTRAR CUANTAS ENTRADAS A COMPRADO CUANDO TERMINE LA VENTA
			entradasCompradas++;

		}

            //FAN A COMPRADO EL MAXIMO DE ENTRADAS PERMITIDAS, SALE DEL BUCLE, NO PUEDE C0MPRAR MAS Y MUESTRA CUANTAS A COMPRADO
			mensajeFan("He comprado el maximo de entradas que tengo permitido... termino mi compra");
			dimeEntradasCompradas();
        }

	
	public void dimeEntradasCompradas() {
		mensajeFan("Sólo he conseguido: " + entradasCompradas);
	}
	
	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajeFan(String mensaje) {
		System.out.println(System.currentTimeMillis() + "|" + tabuladores +" Fan "+this.numeroFan +": " + mensaje);
	}
}
