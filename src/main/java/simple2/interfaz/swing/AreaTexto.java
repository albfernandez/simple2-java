/*
 * Created on 31-jul-2003
 *
 */
package simple2.interfaz.swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.JPopupMenu;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 * @author Montserrat Sotomayor Gonzalez
 *
 */
/**
 * Es un area de texto plano que permite editar m�ltiples lineas.
 */
public class AreaTexto extends javax.swing.JTextArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6863593338645279451L;

	/**
	 * Indica si el texto que contiene el area de texto ha sufrido cambios.
	 */
	private boolean modificado;

	/**
	 * Administrador de acciones necesario para trabajar con las acciones
	 * deshacer y rehacer.
	 */
	protected UndoManager undo = new UndoManager();

	/**
	 * Nombre del archivo con el que se est� trabajando.
	 */
	public File nombreFichero = null;

	/**
	 * Menu emergente. Aparece cuando se pulsa el bot�n derecho del rat�n sobre
	 * el area de texto.
	 */
	private JPopupMenu popup = null;

	/**
	 * Esta clase gestiona las acciones que se pueden deshacer y rehacer.
	 */
	public class MyUndoableEditListener implements UndoableEditListener {
		/**
		 * Cuando ocurre una nueva acci�n se la a�ade al administrador de
		 * acciones.
		 * 
		 * @param e
		 *            evento
		 */
		@Override
		public void undoableEditHappened(UndoableEditEvent e) {
			AreaTexto.this.undo.addEdit(e.getEdit());
		}
	}

	/**
	 * Descarta todas las acciones para deshacer que ten�a el Administrador de
	 * acciones
	 */
	public void descartarDeshacer() {
		this.undo.discardAllEdits();
	}

	/**
	 * Deshace la �ltima acci�n realizada.
	 */
	public void deshacer() { 
		try {
			this.undo.undo();

		} catch (CannotUndoException ex) { //NOPMD
			// Simplemente no hacemos nada.
		}
	}

	/**
	 * Vuelve a hacer la �ltima acci�n deshecha.
	 */
	public void rehacer() {
		try {
			this.undo.redo();
		} catch (CannotRedoException ex) { //NOPMD
			// Simplemente no hacemos nada
		}

	}

	/**
	 * Indica si hay acciones que se puedan rehacer.
	 * 
	 * @return true - si se puede rehacer. false - si no se puede rehacer.
	 */
	public boolean puedeRehacer() {
		return this.undo.canRedo();
	}

	/**
	 * Indica si hay acciones que se puedan deshacer.
	 * 
	 * @return true - si se puede deshacer. false - si no se puede deshacer.
	 */
	public boolean puedeDeshacer() {
		return this.undo.canUndo();
	}

	/**
	 * Actualiza el atributo estado.
	 * 
	 * @param estado
	 *            - deber�a ser: true si se ha modificado el texto. false si no
	 *            se ha modificado el texto.
	 */
	public void setModificado(boolean estado) {
		this.modificado = estado;
		fireCaretUpdate(null);
	}

	/**
	 * Devuelve el atributo estado del texto.
	 * 
	 * @return true si se ha modificado el texto. false si no se ha modificado
	 *         el texto.
	 */
	public boolean isModificado() {
		return this.modificado;
	}

	/**
	 * Crea una instancia de la clase con un estilo por defecto de documento.
	 * 
	 * @param doc
	 *            - Estilo que tendr� el �rea de texto.
	 */
	public AreaTexto(DefaultStyledDocument doc) {
		super(doc);
		addMouseListener(new PopupListener());
		this.modificado = false;
		getDocument().addDocumentListener(new listener());
		getDocument().addUndoableEditListener(new MyUndoableEditListener());
		this.setTabSize(2);
	}

	/**
	 * Crea una instancia de la clase. Crea un objeto de la clase con los
	 * valores por defecto.
	 */

	public AreaTexto() {
		super();
	}

	/**
	 * Cuenta el numero de lineas y caracteres que contiene el area de texto.
	 * 
	 * @return el numero de lineas de texto de la ventana de edici�n.
	 */
	@Override
	public int getLineCount() {
		int lineas = super.getLineCount();
		int caracteres = this.getDocument().getLength();
		if (caracteres < 1) {
			return 1;
		}

		return lineas;
	}

	/**
	 * Obtiene el texto contenido en linea. Cuidado, empieza a contar en 0.
	 *
	 * @param linea
	 *            el n�mero de la linea de la que queremos obtener el texto.
	 * @return El texto contenido en linea, una cadena vac�a en caso de no
	 *         existir dicha linea.
	 */
	public String getLine(int linea) {
		int inicio, fin;
		try {
			inicio = this.getLineStartOffset(linea);
			fin = this.getLineEndOffset(linea);
			return this.getText(inicio, fin - inicio);
		} catch (javax.swing.text.BadLocationException e) {
			return "";
		}
	}

	/**
	 * Devuelve el texto que contiene el editor en un vector. Cada elemento del
	 * vector corresponde a una linea del editor.
	 * 
	 * @return El vector con el texto.
	 */
	public Vector<String> getTextAsVector() {
		int inicio, fin;
		Vector<String> v = new Vector<String>();
		int lineas = this.getLineCount();
		for (int i = 0; i < lineas; i++) {
			try {
				inicio = this.getLineStartOffset(i);
				fin = this.getLineEndOffset(i);

				v.add(this.getText(inicio, (fin - inicio)));
			} catch (javax.swing.text.BadLocationException e) {
				v.add("");
			}

		}
		return v;
	}

	/**
	 * Devuelve el texto que contiene el editor en un array de String. Cada
	 * elemento del array corresponde a una linea del editor.
	 * 
	 * @return El array con el texto.
	 */
	public String[] getTextAsArrayString() {
		int inicio, fin;
		int lineas = this.getLineCount();
		String[] t = new String[lineas];
		for (int i = 0; i < lineas; i++) {
			try {
				inicio = this.getLineStartOffset(i);
				fin = this.getLineEndOffset(i);
				t[i] = this.getText(inicio, (fin - inicio));
			} catch (javax.swing.text.BadLocationException e) {
				t[i] = "";
			}
		}
		return t;
	}

	/**
	 * Muestra informaci�n sobre la posici�n en la que esta el cursor, linea,
	 * lineas totales del fichero, porcentaje de texto antes del cursor y la
	 * columna.
	 * 
	 * @return Una cadena con todos esos datos.
	 */
	public String getLineInfo() {
		try {
			String textoIndicaModificacion = "  ";
			int dot = getCaretPosition();
			int linea = getLineOfOffset(dot);
			int columna = 1 + dot - getLineStartOffset(linea);
			int lineastotales = getLineCount();
			int caracter = dot + 1;
			int total_caracteres = this.getDocument().getLength();
			if (isModificado()) {
				textoIndicaModificacion = " *";
			}

			return ("L�nea " + (linea + 1) + " de " + lineastotales + "  -  Columna " + columna + "  car�cter "
					+ caracter + "(" + 100 * caracter / (total_caracteres + 1) + "%) " + textoIndicaModificacion);
		} catch (javax.swing.text.BadLocationException e) {
			return " ";
		}
	}

	/**
	 * Asigna un menu al menu emergente.
	 * 
	 * @param menu
	 *            Un men� emergente.
	 * @return El men� emergente a�adido.
	 */
	public JPopupMenu anadirPopup(JPopupMenu menu) {
		this.popup = menu;
		return this.popup;
	}

	/**
	 * Esta clase chequea constantemente si se modifica el documento para
	 * actualizar el atributo modificado.
	 */
	private class listener implements DocumentListener {
		/**
		 * Crea una instancia de la clase.
		 */
		public listener() {

		}

		/**
		 * Se activa si cambia el contenido de documento.
		 * 
		 * @param e
		 *            Un evento.
		 */
		@Override
		public void changedUpdate(DocumentEvent e) {
			AreaTexto.this.modificado = true;
		}

		/**
		 * Se activa si se inserta algo en el documento.
		 * 
		 * @param e
		 *            Un evento.
		 */
		@Override
		public void insertUpdate(DocumentEvent e) {
			AreaTexto.this.modificado = true;
		}

		/**
		 * Se activa si elimina algo del documento.
		 * 
		 * @param e
		 *            Un evento.
		 */
		@Override
		public void removeUpdate(DocumentEvent e) {
			AreaTexto.this.modificado = true;
		}
	}

	/**
	 * Se mantiene alerta por si se pide mostrar el menu emergente.
	 */
	private class PopupListener extends MouseAdapter {
		/**
		 * Cuando se presiona un bot�n del rat�n puede que se muestre el men�
		 * emergente. Depende del bot�n pulsado.
		 * 
		 * @param e
		 *            Evento de rat�n.
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			mostrarPopup(e);
		}

		/**
		 * Cuando un bot�n del rat�n es liberado puede que se muestre el men�
		 * emergente. Depende del bot�n pulsado.
		 * 
		 * @param e
		 *            Evento de rat�n.
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			mostrarPopup(e);
		}

		/**
		 * Muestra el men� emergente asociado al editor. Si el menu emergente no
		 * existe no se hace nada. Si el menu emergente exite se muestra en
		 * pantalla en la posici�n del puntero del rat�n.
		 *
		 * @param e
		 *            Evento.
		 */
		private void mostrarPopup(MouseEvent e) {
			// Si no tenemos popup volvemos
			if (AreaTexto.this.popup == null) {
				return;
			}
			// Si tenemos popup, y el es el bot�n derecho o central, mostramos
			// el popup.
			if (e.isPopupTrigger()) {
				AreaTexto.this.popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

}
