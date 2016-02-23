/*
 * Created on 05-jul-2003
 *
 */

package simple2.interfaz.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultStyledDocument;

import simple2.utilidades.Conversiones;

/**
 * @author Montserrat Sotomayor Gonzalez
 *
 */
/**
 * Clase para crear Panel Nuevo
 */
public class PanelNuevo extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Area donde se puede escribir el codigo
	 */
	public AreaTexto texto;

	/**
	 * Area donde se mostrar� el resultado de ensamblar el c�digo(si no hay
	 * errores)
	 */
	public AreaTexto resultado;

	/**
	 * Area donde se mostrar�n los errores si se han producido en el codigo
	 */
	JTextArea errores;

	/**
	 * Crea una instancia de la clase.En el panel colocamos tres Areas de Texto
	 * una donde se escribir� el c�digo, otra que contendr� el resultado si no
	 * tiene el c�digo errores y otra que contendr� los errores (si los hay) del
	 * c�digo.
	 *
	 */
	public PanelNuevo() {
		super();
		setLayout(new BorderLayout());
		// Creamos el estilo de documento para el area de texto
		DefaultStyledDocument estiloDefectoDoc = new DefaultStyledDocument();

		this.texto = new AreaTexto(estiloDefectoDoc);
		this.texto.setCaretPosition(0);
		this.texto.setMargin(new Insets(5, 5, 5, 5));

		JScrollPane editorScrollPane = new JScrollPane(this.texto);

		editorScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// panel de texto no editable para el codigo fuente codificado en
		// hexadecimal
		this.resultado = new AreaTexto();
		this.resultado.setEditable(false);
		this.resultado.setBackground(Color.lightGray);
		this.resultado.setMargin(new Insets(5, 5, 5, 5));

		JScrollPane hexadecimalScrollPane = new JScrollPane(this.resultado);
		hexadecimalScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// panel de texto no editable que contiene los posibles errores
		this.errores = new JTextArea();
		this.errores.setEditable(false);
		this.errores.setBackground(Color.lightGray);
		this.errores.setMargin(new Insets(5, 5, 5, 5));

		JScrollPane erroresScrollPane = new JScrollPane(this.errores);
		erroresScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JPanel statusPane = new JPanel(new GridLayout(1, 1));
		CaretTextListenerLabel barraPosicion = new CaretTextListenerLabel(" ");
		statusPane.add(barraPosicion);

		this.texto.addCaretListener(barraPosicion);
		this.texto.getDocument().addDocumentListener(barraPosicion);

		JPanel aux = new JPanel(new BorderLayout());
		aux.add(statusPane, BorderLayout.SOUTH);
		aux.add(editorScrollPane, BorderLayout.CENTER);

		// creamos un Splitpane superior que contiene el editor de codigo DLX y
		// el codigo en hexadecimal
		JSplitPane panelSuperior = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, aux, hexadecimalScrollPane);
		panelSuperior.setOneTouchExpandable(true);
		panelSuperior.setDividerLocation(400);

		// creamos un SplitPane vertical con el panel anterior y el panel de los
		// errores
		JSplitPane panelPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelSuperior, erroresScrollPane);
		panelPrincipal.setOneTouchExpandable(true);
		panelPrincipal.setDividerLocation(400);

		add(panelPrincipal, BorderLayout.CENTER);

	}

	/**
	 * Esta clase actualiza el texto que indica d�nde se encuentra el cursor:
	 * linea, columna, ...
	 */
	protected class CaretTextListenerLabel extends JLabel implements CaretListener, DocumentListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor de la clase.
		 * 
		 * @param label
		 *            - texto inicial.
		 */
		public CaretTextListenerLabel(String label) {
			super(label);
		}

		/**
		 * Actualiza el texto de acuerdo a la posici�n en al que se encuentra el
		 * cursor en la ventana de edici�n.
		 * 
		 * @param e
		 *            -evento.
		 */
		public void changedUpdate(DocumentEvent e) {
			caretUpdate(null);

		}

		/**
		 * Actualiza las acciones de rehacer y deshacer, cuando se inserta algo
		 * se puede deshacer y no rehacer.
		 * 
		 * @param e
		 *            -evento.
		 */
		public void insertUpdate(DocumentEvent e) {
			caretUpdate(null);
		}

		/**
		 * Actualiza las accciones de rehacer y deshcer, cuando se elimina algo
		 * se puede deshacer pero no rehacer.
		 * 
		 * @param e
		 *            -evento
		 */
		public void removeUpdate(DocumentEvent e) {
			caretUpdate(null);
		}

		/**
		 * Actualiza la informaci�n acerca de la posici�n del cursor.
		 * 
		 * @param e
		 *            -evento
		 */
		public void caretUpdate(CaretEvent e) {
			setText(PanelNuevo.this.texto.getLineInfo());
		}
	}

	/**
	 * Escribe en el area de texto resultado el c�digo ensamblado
	 * 
	 * @param codigo_limpio
	 *            c�digo del que partimos
	 * @param ensamblado
	 *            c�digo que ha sido ensamblado
	 * @param instruccionesTotales
	 *            N�mero de instrucciones totales
	 */
	public void escribir(Vector<String> codigo_limpio, short[] ensamblado, int instruccionesTotales) {
		this.resultado.setEditable(false);
		this.resultado.setBackground(Color.lightGray);
		StringBuilder codificado = new StringBuilder();
		for (int i = 0; i < instruccionesTotales; i++) {
			String cadena = Conversiones.toHexString(ensamblado[i]);
			codificado.append("(").append(codigo_limpio.elementAt(i)).append(")").append(cadena).append("\n");
		}
		this.resultado.setText(codificado.toString());

	}

	/**
	 * Escribe en el area de texto errores los fallos que se han producido en el
	 * codigo
	 * 
	 * @param error
	 *            cadena de caracteres con los errores que se han producido
	 */
	public void errores(String error) {
		this.resultado.setEditable(false);
		this.resultado.setBackground(Color.lightGray);
		this.errores.setText(error);
	}

}
