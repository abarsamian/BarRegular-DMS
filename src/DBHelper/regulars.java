package DBHelper;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Creates sql functions for table regulars
 */
public class regulars extends DBHelper {
	private final String TABLE_NAME = "regulars";
	public static final String id = "id";
	public static final String name = "name";
	public static final String favorite_drink = "favorite_drink";
	public static final String visits = "visits";
	public static final String total_spent = "total_spent";
	public static final String vip_status = "vip_status";

	/**
	 * Creates a helper method for interacting with the regulars table.
	 * @param dbPath the file path of the database.
	 */

	public regulars(String dbPath) {
		super(dbPath);
	}

	/**
	 * This method prepares the database queries and creates variables for the functionality of the different fields.
	 * It makes database query statements and returns the variable query every time.  Also has filtering and sorting
	 * @param fields the fields to select, or null to select all fields
	 * @param whatField the field to filter by
	 * @param whatValue the value to match in the filter field
	 * @param sortField the field used for sorting
	 * @param sort the sort order, such as ASC or DESC
	 * @return the completed SQL SELECT query string
	 */
	private String prepareSQL(String fields, String whatField, String whatValue, String sortField, String sort) {
		String query = "SELECT ";
		query += fields == null ? " * FROM " + TABLE_NAME : fields + " FROM " + TABLE_NAME;
		query += whatField != null && whatValue != null ? " WHERE " + whatField + " = \"" + whatValue + "\"" : "";
		query += sort != null && sortField != null ? " order by " + sortField + " " + sort : "";
		return query;
	}

	/**
	 * This method calls the sql statement for inserting fields into the database.
	 * @param id the customer's id
	 * @param name the customer's name
	 * @param favorite_drink the customer's favorite drink
	 * @param visits number of visits per month
	 * @param total_spent total spent in one month
	 * @param vip_status whether or not the customer is a vip
	 */
	public void insert(Integer id, String name, String favorite_drink, Integer visits, Double total_spent, Integer vip_status) {
		name = name != null ? "\"" + name + "\"" : null;
		favorite_drink = favorite_drink != null ? "\"" + favorite_drink + "\"" : null;
		
		Object[] values_ar = {id, name, favorite_drink, visits, total_spent, vip_status};
		String[] fields_ar = {regulars.id, regulars.name, regulars.favorite_drink, regulars.visits, regulars.total_spent, regulars.vip_status};
		String values = "", fields = "";
		for (int i = 0; i < values_ar.length; i++) {
			if (values_ar[i] != null) {
				values += values_ar[i] + ", ";
				fields += fields_ar[i] + ", ";
			}
		}
		if (!values.isEmpty()) {
			values = values.substring(0, values.length() - 2);
			fields = fields.substring(0, fields.length() - 2);
			super.execute("INSERT INTO " + TABLE_NAME + "(" + fields + ") values(" + values + ");");
		}
	}

	/**
	 * This method is the delete function that calls the field and what value needs to be removed, and then removes that value from the table.
	 * @param whatField the field used in the condition
	 * @param whatValue the value to be deleted
	 */
	public void delete(String whatField, String whatValue) {
		super.execute("DELETE from " + TABLE_NAME + " where " + whatField + " = " + whatValue + ";");
	}

	/**
	 * This method is used for updating the fields. It calls the fields and replaces the fields with new values.
	 * @param whatField the field to update
	 * @param whatValue the new value to place in the field
	 * @param whereField the field used in the WHERE condition
	 * @param whereValue the value used in the WHERE condition
	 */
	public void update(String whatField, String whatValue, String whereField, String whereValue) {
		super.execute("UPDATE " + TABLE_NAME + " set " + whatField + " = \"" + whatValue + "\" where " + whereField + " = \"" + whereValue + "\";");
	}

	/**
	 * Executes a SELECT query on the regulars table and returns the results
	 * as an ArrayList. This helps when you need to return all the rows of an sql table or if you need to iterate through to find something specific
	 * @param fields the fields to select, or null to select all fields
	 * @param whatField the field to filter by
	 * @param whatValue the value to match in the filter field
	 * @param sortField the field used for sorting
	 * @param sort the sort order, such as ASC or DESC
	 * @return the query results as an ArrayList of rows
	 */
	public ArrayList<ArrayList<Object>> select(String fields, String whatField, String whatValue, String sortField, String sort) {
		return super.executeQuery(prepareSQL(fields, whatField, whatValue, sortField, sort));
	}

	/**
	 * Executes a custom SQL query and returns the results
	 * @param query the sql query you want to execute
	 * @return the results as an arraylist
	 */
	public ArrayList<ArrayList<Object>> getExecuteResult(String query) {
		return super.executeQuery(query);
	}

	/**
	 * executes a custom sql query but returns only the result, not a whole array.
	 * @param query the result.
	 */
	public void execute(String query) {
		super.execute(query);
	}

	/**
	 * Returns results as a default table method.
	 * @param fields the fields to select, or null to select all fields
	 * @param whatField the field to filter by
	 * @param whatValue the value to match in the filter field
	 * @param sortField the field used for sorting
	 * @param sort the sort order, such as ASC or DESC
	 * @return the query results as a DefaultTableModel
	 */
	public DefaultTableModel selectToTable(String fields, String whatField, String whatValue, String sortField, String sort) {
		return super.executeQueryToTable(prepareSQL(fields, whatField, whatValue, sortField, sort));
	}

}