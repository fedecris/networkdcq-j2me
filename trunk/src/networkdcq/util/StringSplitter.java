package networkdcq.util;

import java.util.Vector;

public class StringSplitter {

	/**
	 * Splits the original string around matches of the given regular expression. 
	 * @param original 
	 * 			String to be splitted
	 * @param regex 
	 * 			Regular expression 
	 * @return the array of strings computed by splitting the original string
	 *    
	 */
	public static String[] split(String original, String regex) {
		
		Vector cadenas = new Vector();
		char separador;
		int index;
		
		// Por cada simbolo en la expresion regular
		for(int i=0; i < regex.length(); i++)
		{
			separador = regex.charAt(i);
			// separo el nodo actual
			index = original.indexOf(separador);
			cadenas.addElement(original.substring(0, index));
			original = original.substring(index + 1);
			index = original.indexOf(separador);			
		}
		
		// Recupero el ultimo nodo
		cadenas.addElement(original);

		// Creo el vector de Strings
		String[] result = new String[cadenas.size()];
		if( cadenas.size() > 0 ) 
		{
			for(int j=0; j < cadenas.size(); j++)
				result[j] = (String)cadenas.elementAt(j);
		}

		return result;
	} 		
}
