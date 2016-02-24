/*
 * Created on 28-jul-2003
 *
 */

package simple2.ensamblador;

import java.util.Hashtable;

/**
 * @author Montserrat Sotomayor Gonzalez
 *
 */

/**
 * Esta clase es la encargada de verificar si las intrucciones de salto tienen
 * el formato correcto y las codifica.
 */
public class InstruccionSalto extends InstruccionGeneral {

	private static final String LINEA = "Linea: ";
	/**
	 * Contiene las instrucciones que maneja la clase con su c�digo de
	 * operaci�n.
	 */
	private Hashtable<String, Integer> tabla;

	/**
	 * Crea una instancia de la clase e inicializa el atributo tabla con las
	 * instrucciones .
	 */
	public InstruccionSalto() {
		this.tabla = new Hashtable<>();

		this.tabla.put("JNEG", Integer.valueOf(0x0A));
		this.tabla.put("JZER", Integer.valueOf(0x0B));
		this.tabla.put("JCAR", Integer.valueOf(0x0C));
		this.tabla.put("JUMP", Integer.valueOf(0x0D));
		this.tabla.put("CALL", Integer.valueOf(0x0E));
	}

	/**
	 * Traduce la instrucci�n de salto. La instrucion debe estar validada
	 * previamente con el metodo
	 *
	 * @param instruccion
	 *            Instrucci�n con su nombre y parametros.
	 * @param linea
	 *            Linea en la que aparece la instrucci�n.
	 *
	 * @return La codificaci�n de la instrucci�n.
	 */
	@Override
	public short codificar(String instruccion, int linea) {
		int codigo;
		String[] cadena = separarOperandos(instruccion);
		int inmediato = Integer.parseInt(cadena[1]);
		Object c = this.tabla.get(cadena[0]);
		codigo = ((Integer) c).intValue();
		codigo = codigo << 11;

		return ((short) (codigo + inmediato));
	}

	/**
	 * Comprueba que la instrucci�n de salto que se va a codificar tenga el
	 * formato correcto.
	 *
	 * @return Cadena vacia si no se han producido errores. Cadena con un
	 *         mensaje que indica el motivo del error en la sintaxis.
	 * @param instruccion
	 *            Instrucci�n con su nombre y parametros.
	 * @param linea
	 *            Linea en la que aparece la instrucci�n.
	 * @throws ErrorCodigoException
	 *             si ocurre algun error en el c�digo, la excepcion contiene el
	 *             mensaje de error
	 */
	@Override
	public String validar(String instruccion, int linea) throws ErrorCodigoException {
		if (contieneCaracteresNoValidos(instruccion)) {
			return LINEA + linea + ". " + CONTIENE_CARACTERES_INVALIDOS;
		}
		String[] cadena = separarOperandos(instruccion);
		Object c = this.tabla.get(cadena[0]);
		if (c == null) {
			return LINEA + linea + ". No se reconoce la instruccion " + cadena[0] + "\n";
		}
		if (cadena.length != 2) {
			return LINEA + linea + ". N�mero de par�metros incorrectos.\n";
		}
		try {
			Integer.parseInt(cadena[1]);
		} catch (Exception e) {
			return LINEA + linea + ". El segundo parametro tiene que ser un n�mero\n";
		}

		return "";
	}

}
