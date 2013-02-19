package networkdcq.util;

/**
 * A ConcurrentHashMap which mantains a separate keyList and valueList in order to avoid
 * creating new instances when using tratidional methods like keySet() or values(), hence
 * this type is useful for cases in which iterations over a hashmap is frequently called.
 */

import java.util.Hashtable;
import java.util.Vector;

//public class IterateableConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V> {
public class IterateableConcurrentHashMap extends Hashtable {
	/**	  */
	private static final long serialVersionUID = 1L;
	/** */
	protected Vector keyList = new Vector();
	/** */
	protected Vector valueList =  new Vector();
	

	public synchronized Object put(Object key, Object value) {
		super.put(key, value);
		keyList.addElement(key);
		valueList.addElement(value);
		return value;
		
	}
	
	public synchronized Object replace(Object key, Object value) {
		if (containsKey(key)) {
			valueList.setElementAt(value, keyList.indexOf(key));
			return put(key, value);
		}
		return null;
	}

	public synchronized boolean replace(Object key, Object oldValue, Object newValue) {
		if (containsKey(key) && get(key).equals(oldValue)) {
			replace(key, newValue);
			return true;
		}
		return false;
	}
	

	public synchronized Object remove(Object key) {
		Object value = super.remove(key);
		keyList.removeElement(key);
		valueList.removeElement(value);
		return value;
	}


	public synchronized boolean remove(Object key, Object value) {
		if (containsKey(key) && get(key).equals(value)) {
			remove(key);
			return true;
		}
		return false;
	}

	public synchronized void clear() {
		super.clear();
		keyList.removeAllElements();
		valueList.removeAllElements();
	}
	
	public Object putIfAbsent(Object key, Object value) {
		if (!containsKey(key)) {
			return put(key, value);
		}
		return get(key);
	};
	
	
	public Vector getKeyList() {
		return keyList;
	}

	public Vector getValueList() {
		return valueList;
	}

	public Object getKeyAt(int pos) {
		return keyList.elementAt(pos);
	}
	
	public Object getValueAt(int pos) {
		return valueList.elementAt(pos);
	}

}
