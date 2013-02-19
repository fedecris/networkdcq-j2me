package networkdcq;


public interface NetworkSerializable {
	
	/** Separador entre miembros de la variable al serializar a String */
	public static String VARIABLE_MEMBER_SEPARATOR = "&";
	/** Flag de finalizacion de variables serializadas */
	public static String VARIABLE_END_OF_VARIABLES = "%";
	
	/**
	 * Devuelve un String a partir del objeto NetworkApplicationData
	 */
    public String networkSerialize();

	/**
	 * Devuelve una nueva instancia de NetworkApplicationData creada a partir del String
	 */
    public Object networkDeserialize(String data);
}
