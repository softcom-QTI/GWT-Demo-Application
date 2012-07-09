package pro.softcom.archetype.gwt.server.rpc;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Der GwtRpcContext ermoeglicht es Objekte zwischen dem GwtRpcDispatcher und
 * den GwtServices zu uebergeben.
 * 
 */
public class GwtRpcContext {

	// ThreadLocal mit einer Map mit dem Context Infos pro thread.
	private static ThreadLocal<Map<String, Object>> threadContext = new ThreadLocal<Map<String, Object>>();

	// konstanten fuer context Infos
	private static final String CTX_INFO_LANGUAGE = "languageKey";

	private static final String CTX_INFO_PERMUTATION_STRONG_NAME = "strongName";

	/**
	 * Liest den PermutationStrongName vom aktuellen Thread.
	 * 
	 * @return der PermutationStrongName.
	 */
	public static String getPermutationStrongName() {
		return (String) getContextInfo(CTX_INFO_PERMUTATION_STRONG_NAME);
	}

	/**
	 * Setzt den PermutationStrongName fuer den aktuellen Thread.
	 * 
	 * @param permutationStrongName
	 *            der permutationStrongName.
	 */
	public static void setPermutationStrongName(String permutationStrongName) {
		setContextInfo(CTX_INFO_PERMUTATION_STRONG_NAME, permutationStrongName);
	}

	/**
	 * Liest die Sprache fuer den aktuellen Thread zurueck
	 * 
	 * @return
	 */
	public static Locale getLanguage() {
		return (Locale) getContextInfo(CTX_INFO_LANGUAGE);
	}

	/**
	 * Setzt die Sprache fuer den aktuellen Thread
	 * 
	 * @param language
	 */
	public static void setLanguage(Locale language) {
		setContextInfo(CTX_INFO_LANGUAGE, language);
	}

	/**
	 * holt ein Objekt aus dem Context
	 * 
	 * @param key
	 * @return
	 */
	public static Object getContextInfo(String key) {
		return getContext().get(key);
	}

	/**
	 * setzt ein Objekt im context
	 * 
	 * @param key
	 * @return
	 */
	public static void setContextInfo(String key, Object info) {
		getContext().put(key, info);
	}

	/**
	 * Gibt den Context fuer den aktuellen Thread zurueck. Falls noch keiner
	 * besteht wird ein neuer erstellt
	 * 
	 */
	private static Map<String, Object> getContext() {
		Map<String, Object> context = threadContext.get();
		if (context == null) {
			context = new HashMap<String, Object>();
			threadContext.set(context);
		}
		return context;
	}

	/**
	 * Inhalt des Contexts fuer den aktuellen Thread loeschen
	 * 
	 */
	public static void clearContextInfo() {
		if (threadContext.get() != null) {
			threadContext.get().clear();
		}
	}

}
