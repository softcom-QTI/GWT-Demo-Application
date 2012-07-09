package pro.softcom.archetype.repository.util;

/**
 * Utilities for query building
 */
public class QueryUtil {

	public static final String WHERE = "WHERE";
	public static final String AND = "AND";
	public static final String OR = "OR";
	public static final String SPACE = " ";
	public static final char WILDCARD = '%';

	/**
	 * Add a condition to a select query. It automatically adds the "WHERE" if
	 * necessary.
	 * 
	 * @param selectQuery
	 *            The base select query to add the condition onto.
	 * @param condition
	 *            The condition to add (e.g. "cg.name LIKE '%MOD%'", or
	 *            "cg.id = 4").
	 * @param and
	 *            AND if true, OR if false.
	 * @return The select query with the condition added.
	 */
	public static String addCondition(String selectQuery, String condition,
			boolean and) {
		if (selectQuery.toUpperCase().indexOf(WHERE) == -1) {
			selectQuery += SPACE + WHERE + SPACE + condition;
		} else {
			selectQuery += SPACE + (and ? AND : OR) + SPACE + condition;
		}
		return selectQuery;
	}

	/**
	 * Adds wild card before and after the parameter. The parameter is also
	 * transformed in lower case.
	 * 
	 * Example : "Test" will be transformed into "%test%".
	 * 
	 * @param parameter
	 *            The parameter to transform.
	 * @return The transformed parameter.
	 */
	public static String getLowerCaseParameterWithWildcards(String parameter) {
		return WILDCARD + parameter.toLowerCase() + WILDCARD;
	}

	/**
	 * The parameter is transformed in lower case.
	 * 
	 * @param parameter
	 *            The parameter to transform.
	 * @return The transformed parameter.
	 */
	public static String getLowerCaseParameter(String parameter) {
		return parameter.toLowerCase();
	}
}
