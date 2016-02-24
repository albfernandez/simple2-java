/*
 * Created on 03-sep-2003
 *
 */
package simple2.interfaz.swing;

import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * @author Montserrat Sotomayor Gonzalez
 *
 */
/**
 * Esta clase es un campo de edici�n de texto que s�lo acepta numeros. Cualquier
 * car�cter introducido que no sea num�rico ser� rechazado. Tambi�n limita el
 * tama�o del campo de edici�n, no permitiendo introducir un n�mero de mayor
 * n�mero de digitos que los especificados en el constructor del objeto.
 */
public class CampoNumerico extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -607499991012895863L;
	/**
	 * Valor en el que se almacena el valor del ancho del campo de edici�n. La
	 * clase almacena internamente el ancho del campo en car�cteres. Adem�s, es
	 * el m�ximo y no permite introducir un n�mero con m�s d�gitos que <font
	 * size=-1> cols </font>.
	 */
	private int cols;

	/**
	 * Constructor de la clase
	 * 
	 * @param columnas
	 *            Numero m�ximo de d�gitos que admite
	 */
	public CampoNumerico(int columnas) {
		super();
		this.cols = columnas;
	}

	public CampoNumerico(int valor_inicial, int columnas) {
		super(columnas);
		this.cols = columnas;
		setValue(valor_inicial);
	}

	/**
	 * Devuelve el valor almacenado en el campo de edici�n como un entero.
	 *
	 * @return El valor almacenado actualmente en el campo.
	 */

	public int getValue() {
		int retVal = 0;
		if (!"".equals(getText())) {
			retVal = Integer.parseInt(getText());
		}
		return retVal;
	}

	/**
	 * Establece el valor del campo en un entero.
	 *
	 * @param valor
	 *            El valor que tomar� el campo.
	 */
	public void setValue(int valor) {
		setText(String.valueOf(valor));
	}

	/**
	 * Funci�n que crea un nuevo modelo de documento. Determina de que forma
	 * ser�n tratados los datos que se introduzcan en el campo de edici�n.
	 *
	 * @return El nuevo modelo de documento.
	 */
	@Override
	protected Document createDefaultModel() {
		return new CampoNumericoDoc();
	}

	/**
	 * Clase del tipo de documento del campo de edici�n. Es la que nos permite
	 * rechazar aquellos car�cteres que no est�n en los rangos 0-9 as� como
	 * aquellos que hagan que el contenido exceda de la longitud m�xima.
	 */
	protected class CampoNumericoDoc extends PlainDocument {

		/**
		 * 
		 */
		private static final long serialVersionUID = 803699531167972655L;

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if (str == null) {
				return;
			}
			char[] source = str.toCharArray();

			char[] result = new char[source.length];
			int j = 0;
			for (int i = 0; i < result.length; i++) {
				if (Character.isDigit(source[i])) {
					result[j++] = source[i];
				}
				else {
					Toolkit.getDefaultToolkit().beep();
				}
			}

			if ((getLength() + source.length) <= CampoNumerico.this.cols){
				super.insertString(offs, new String(result, 0, j), a);
			}
			else {
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}
}
