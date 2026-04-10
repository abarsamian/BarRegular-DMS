package DBHelper;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

/**
 * This class provides helper methods for connecting to an SQLite database
 * and executing SQL statements and queries.
 */
public class DBHelper {
	private final String DATABASE_NAME;
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	/**
	 * This is the method that helps with the gui providing a place where the user can import a database.
	 * @param dbPath path file of the sqlite database
	 */
	public DBHelper(String dbPath) {
		connection = null;
		statement = null;
		resultSet = null;
		this.DATABASE_NAME = dbPath;
	}

	private void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_NAME);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void close() {
		try {
			connection.close();
			statement.close();
			if (resultSet != null)
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Object[][] arrayListTo2DArray(ArrayList<ArrayList<Object>> list) {
		Object[][] array = new Object[list.size()][];
		for (int i = 0; i < list.size(); i++) {
			ArrayList<Object> row = list.get(i);
			array[i] = row.toArray(new Object[row.size()]);
		}
		return array;
	}

	/**
	 * Executes the given sql statement.
	 * @param sql creates a place to store the sql statement.
	 */
	protected void execute(String sql) {
		try {
			connect();
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close();
		}
	}

	/**
	 * Executes the given SQL query and returns the results
	 * in a DefaultTableModel for use in a table.
	 * @param sql holds the query data
	 * @return 2d array with data - the Default table method.
	 */
	protected DefaultTableModel executeQueryToTable(String sql) {
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> columns = new ArrayList<Object>();
		connect();
		try {
			resultSet = statement.executeQuery(sql);
			int columnCount = resultSet.getMetaData().getColumnCount();
			for (int i = 1; i <= columnCount; i++)
			columns.add(resultSet.getMetaData().getColumnName(i));
			while (resultSet.next()) {
				ArrayList<Object> subresult = new ArrayList<Object>();
				for (int i = 1; i <= columnCount; i++)
				subresult.add(resultSet.getObject(i));
				result.add(subresult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return new DefaultTableModel(arrayListTo2DArray(result), columns.toArray());
	}

	/**
	 * Creates arraylist to put query data into so it can be inserted into table
	 * @param sql holds query data
	 * @return ArrayList containing query results.
	 */
	protected ArrayList<ArrayList<Object>> executeQuery(String sql) {
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
		connect();
		try {
			resultSet = statement.executeQuery(sql);
			int columnCount = resultSet.getMetaData().getColumnCount();
			while (resultSet.next()) {
				ArrayList<Object> subresult = new ArrayList<Object>();
				for (int i = 1; i <= columnCount; i++) {
					subresult.add(resultSet.getObject(i));
				}
				result.add(subresult);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		close();
		return result;
	}

}