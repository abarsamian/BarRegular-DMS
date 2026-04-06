package DBHelper;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class regulars extends DBHelper {
	private final String TABLE_NAME = "regulars";
	public static final String id = "id";
	public static final String name = "name";
	public static final String favorite_drink = "favorite_drink";
	public static final String visits = "visits";
	public static final String total_spent = "total_spent";
	public static final String vip_status = "vip_status";


	public regulars(String dbPath) {
		super(dbPath);
	}

	private String prepareSQL(String fields, String whatField, String whatValue, String sortField, String sort) {
		String query = "SELECT ";
		query += fields == null ? " * FROM " + TABLE_NAME : fields + " FROM " + TABLE_NAME;
		query += whatField != null && whatValue != null ? " WHERE " + whatField + " = \"" + whatValue + "\"" : "";
		query += sort != null && sortField != null ? " order by " + sortField + " " + sort : "";
		return query;
	}

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

	public void delete(String whatField, String whatValue) {
		super.execute("DELETE from " + TABLE_NAME + " where " + whatField + " = " + whatValue + ";");
	}

	public void update(String whatField, String whatValue, String whereField, String whereValue) {
		super.execute("UPDATE " + TABLE_NAME + " set " + whatField + " = \"" + whatValue + "\" where " + whereField + " = \"" + whereValue + "\";");
	}

	public ArrayList<ArrayList<Object>> select(String fields, String whatField, String whatValue, String sortField, String sort) {
		return super.executeQuery(prepareSQL(fields, whatField, whatValue, sortField, sort));
	}

	public ArrayList<ArrayList<Object>> getExecuteResult(String query) {
		return super.executeQuery(query);
	}

	public void execute(String query) {
		super.execute(query);
	}

	public DefaultTableModel selectToTable(String fields, String whatField, String whatValue, String sortField, String sort) {
		return super.executeQueryToTable(prepareSQL(fields, whatField, whatValue, sortField, sort));
	}

}