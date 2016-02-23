/*
 * Created on 01-ago-2003
 *
 */

package simple2.representacionruta;

import java.awt.Color;

/**
 * @author Montserrat Sotomayor Gonzalez
 *
 */
public interface InterfaceDibujo {
	/**
	 * Vuelca el contenido del dibujo en la pantalla.
	 *
	 */
	void refresh();

	/**
	 * Limpia el contenido del dibujo (no lo vuelca en pantalla).
	 *
	 */
	void clean();

	/**
	 * Limpia un área rectangular determinada
	 * 
	 * @param x
	 * @param y
	 * @param ancho
	 * @param alto
	 */
	void clean(int x, int y, int ancho, int alto);

	/**
	 * Dibuja una recta.
	 * 
	 * @param c
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	void dibujarRecta(Color c, int x1, int y1, int x2, int y2);

	/**
	 * Dibuja un texto
	 * 
	 * @param c
	 * @param x
	 * @param y
	 * @param texto
	 */
	void dibujarTexto(Color c, int x, int y, String texto);

	/**
	 * Dibuja un rectángulo de un color.
	 * 
	 * @param c
	 * @param x1
	 * @param y1
	 * @param ancho
	 * @param alto
	 */
	void dibujarRectangulo(Color c, int x1, int y1, int ancho, int alto);

	// para el cambio de tamaño, notifica a el encargado de dibujar
	// public void SetRepresentacionRDD (RepresentacionRDD rdd);

	/**
	 * Dar la representación
	 */
	void setRepresentacionRDD(RepresentacionRDD r);

}
