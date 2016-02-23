package simple2.ensamblador;

/*
 * Created on 28-jul-2003
 *
 */

/**
 * @author Montserrat Sotomayor Gonzalez
 *
 */

/**
 * Esta clase es la encargada de verificar si las instrucci�nes sin par�metros,
 * tienen el formato correcto y las codifica.
 */
public class InstruccionSinParametros extends InstruccionGeneral {

	/**
	 * Crea una instancia de la clase.
	 */
	public InstruccionSinParametros() {
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
		if ("PUSH".equals(instruccion) || "POP".equals(instruccion) 
				|| "RETN".equals(instruccion) || "HALT".equals(instruccion)) {
			return "";
		}
		throw new ErrorCodigoException("Esta instrucci�n no lleva par�metros");
	}

	/**
	 * Traduce la instrucci�n sin parametros. La instrucion debe estar validada
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
		if ("PUSH".equals(instruccion)) {
			codigo = 16384; // PUSH
		} else if ("POP".equals(instruccion)) {
			codigo = 18432; // POP
		} else if ("RETN".equals(instruccion)) {
			codigo = 30720; // RETN
		} else {
			codigo = 63488; // HALT
		}
		return ((short) (codigo));
	}

}
