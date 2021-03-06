/*
 * Created on 01-ago-2003
 *
 */

package simple2.interfaz.swing;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Montserrat Sotomayor Gonzalez
 *
 */

/**
 * Clase que se va a utilizar para dibujar en el panel
 */
public class PanelEsquema extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -628485690033905931L;

	/**
	 * Panel donde dibujamos
	 */
	PanelDibujo pdibujo;

	/**
	 * Panel superior
	 */
	JPanel panel;

	/**
	 * Etiqueta que usamos como titulo
	 */
	JLabel l1;

	/**
	 * Instancia de la clase. Dibuja la ruta de datos.
	 *
	 */
	public PanelEsquema() {
		super();
		setLayout(new BorderLayout());
		this.pdibujo = new PanelDibujo();
		this.panel = new JPanel();
		this.l1 = new JLabel("RUTA DE DATOS DE SIMPLE2");

		this.panel.add(this.l1);
		add(this.panel, BorderLayout.NORTH);
		add(this.pdibujo, BorderLayout.CENTER);
	}

}
