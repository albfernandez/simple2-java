/*
 * Created on 28-jul-2003
 *
 */
package simple2.representacionruta;

/**
 * @author Montserrat Sotomayor Gonzalez
 *
 */

/**
 * Interface que deben implementar todos los objetos que se puedan dibujar.
 */
public interface IElementoDibujable {
	/**
	 * Dibuja el objeto en su estado inactivo.
	 */
	void apagar();

	/**
	 * Dibuja el objeto en su estado activo.
	 */
	void encender();

	/**
	 * Redibuja el objeto en su estado actual.
	 */
	void repintar();

	/**
	 * El texto que se desea escribir
	 * 
	 * @param texto
	 *            La cadena que se le pasa para escribir
	 */
	void setText(String texto);

	/**
	 * Devuelve el texto escrito
	 * 
	 * @return El texto escrito
	 */
	String getText();
}
