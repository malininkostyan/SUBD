package Entities;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Provider {
	private int id;
	private String company;
	private String surname;
	private String name;
	private String patronymic;
	
	public Provider(int id, String company, String surname, String name, String patronymic){
		this.id = id;
		this.company = company;
		this.surname = surname;
		this.name = name;
		this.patronymic = patronymic;
	}
	
	public Provider(String company, String surname, String name, String patronymic){
		this.company = company;
		this.surname = surname;
		this.name = name;
		this.patronymic = patronymic;
	}
	
	public Provider(){
		
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getSurname(){
		return surname;
	}
	
	public void setSurname(String surname){
		this.surname = surname;
	}
	
	public String getCompany(){
		return company;
	}
	
	public void setCompany(String company){
		this.company = company;
	}
	
	public String getPatronymic(){
		return patronymic;
	}
	
	public void setPatronymic(String patronymic){
		this.patronymic = patronymic;
	}
	
	public Vector<Object> setData(Connection conection) throws SQLException {
		Vector<Object> data = new Vector<Object>();
		data.add(id);
		data.add(company);
		data.add(surname);
		data.add(name);
		data.add(patronymic);
		return data;
	}

	public void addElement(String company, String surname, String name, String patronymic, Connection connection) throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("insert into provider values(nextval('seq_provider'),'" + company + "', '" + surname + "', '" + name + "', '" + patronymic + "');");
	}

	public void removeElement(int id, Connection connection) throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("delete from provider where provider_id = " + id + ";");
	}

	public void refreshElement(int id, String company, String surname, String name, String patronymic, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("update provider set company = '" + company + "', surname = '" + surname + "', name = '" + name + "', patronymic = '" + patronymic + "' where provider_id = " + id + ";");
	}

	public DefaultTableModel TableModel(Connection connection) throws SQLException {
		Vector<String> columnNames = null;
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		DefaultTableModel tableModel = new DefaultTableModel();
		columnNames = getTitles();
		for (int i = 0; i <= getTable(connection).size() - 1; i++) {
			data.add(getTable(connection).get(i).setData(connection));
		}
		tableModel.setDataVector(data, columnNames);
		return tableModel;
	}

	public Vector<String> getTitles() {
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("id");
		columnNames.add("Компания");
		columnNames.add("Фамилия");
		columnNames.add("Имя");
		columnNames.add("Отчество");
		return columnNames;
	}

	public ArrayList<Provider> getTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from provider;");
		ArrayList<Provider> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new Provider((int) rs.getObject(1), rs.getObject(2).toString(), rs.getObject(3).toString(), rs.getObject(4).toString(), rs.getObject(5).toString()));
		}
		return res;
	}

	
}
