package Pattern;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBhelper;
import util.FetchClass;

public class ObserverPattern {
	void setTable() throws IllegalArgumentException, IllegalAccessException {
		Connection con = DBhelper.getConnection();
		PreparedStatement pt;
		CompositePattern cp = new CompositePattern();
		int count = 0;
		try {
			Class[] ans = FetchClass.getClasses("MVCPatternDesign");
			int i = 0;
			int j = 0;
			pt = con.prepareStatement("select max(sub_element_id) from subelement");
			ResultSet rs = pt.executeQuery();
			while (rs.next()) {
				j = rs.getInt(1);			}
			j++;
			System.out.println("j " + j);
			for (Class a : ans) {
				Method[] M = a.getDeclaredMethods();
				Field[] F = a.getDeclaredFields();
				System.out.println("Class is" + a.getName());
				for (Method m : M) {

					if (m.getName().toString().equals("update") || !m.getName().toString().contains("update")) {
						pt = con.prepareStatement(
								"insert into subelement(sub_element_id,sub_element_name"+
						",subelement_datatype,pattern_main_element,pattern_id) values(?,?,?,?,?)");
						pt.setInt(1, j);
						pt.setString(2, m.getName());
						pt.setString(3, "method");
						pt.setInt(4, i);
						pt.setInt(5, 2);
						 pt.executeUpdate();
						System.out.println("inserted");
						j++;}}
				for (Field f : F) {
					if (!f.getType().toString().contains("[")) {
						pt = con.prepareStatement(
								"insert into subelement(sub_element_id,sub_element_name,subelement_datatype"
						+",pattern_main_element,pattern_id) values(?,?,?,?,?)");
						pt.setInt(1, j);
						pt.setString(2, f.getName());
						pt.setString(3, "variable");
						pt.setInt(4, i);
						pt.setInt(5, 2);
						 pt.executeUpdate();
						System.out.println("inserted");
						j++;
					}
				}

				System.out.println();
				i++;
			}

			/*
			 * 
			 * Field[] var = ans[1].getDeclaredFields(); Object obj = new
			 * Object(); for (Field m : var) { String s =
			 * m.getType().toString(); if (s.contains("[")) count++; }
			 * System.out.println("count " + count); System.out.println(
			 * "Fields " + var.length);
			 */
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void setSubElementPattern() throws IllegalArgumentException, IllegalAccessException {

		Connection con = DBhelper.getConnection();
		PreparedStatement pt;

		CompositePattern cp = new CompositePattern();

		try {
			Class[] ans = FetchClass.getClasses("MVCPatternDesign");

			int i = 0;
			int j = 1;
			for (Class a : ans) {
				Method[] M = a.getDeclaredMethods();
				Field[] F = a.getDeclaredFields();
				System.out.println("Class is" + a.getName());
				int count = 0;
				for (Method m : M) {

					if (m.getName().toString().equals("update") || !m.getName().toString().contains("update")) {
						count++;

					}
				}
				for (Field f : F) {
					if (!f.getType().toString().contains("[")) {
						count++;
					}
				}

				pt = con.prepareStatement("update mainelement set observer = ? where element_id=?");
				pt.setInt(1, count);
				pt.setInt(2, i);
				pt.executeUpdate();
				System.out.println("updated");
				i++;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String args[]) throws IllegalArgumentException, IllegalAccessException {

		ObserverPattern op = new ObserverPattern();
		op.setTable();
		op.setSubElementPattern();

	}

}
