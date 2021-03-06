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
 * Esta clase es la encargada de verificar si las instrucciónes sin parámetros,
 * tienen el formato correcto y las codifica.
 */
public class InstruccionSinParametros extends InstruccionGeneral {

	/**
	 * Crea una instancia de la clase.
	 */
	public InstruccionSinParametros() {
	}

	/**
	 * Comprueba que la instrucción de salto que se va a codificar tenga el
	 * formato correcto.
	 *
	 * @return Cadena vacia si no se han producido errores. Cadena con un
	 *         mensaje que indica el motivo del error en la sintaxis.
	 * @param instruccion
	 *            Instrucción con su nombre y parametros.
	 * @param linea
	 *            Linea en la que aparece la instrucción.
	 * @throws ErrorCodigoException
	 *             si ocurre algun error en el código, la excepcion contiene el
	 *             mensaje de error
	 */

	@Override
	public String validar(String instruccion, int linea) throws ErrorCodigoException {
		if ("PUSH".equals(instruccion) || "POP".equals(instruccion) 
				|| "RETN".equals(instruccion) || "HALT".equals(instruccion)) {
			return "";
		}
		throw new ErrorCodigoException("Esta instrucción no lleva parámetros");
	}

	/**
	 * Traduce la instrucción sin parametros. La instrucion debe estar validada
	 * previamente con el metodo
	 *
	 * @param instruccion
	 *            Instrucción con su nombre y parametros.
	 * @param linea
	 *            Linea en la que aparece la instrucción.
	 *
	 * @return La codificación de la instrucción.
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
