package Pattern;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import util.DBhelper;
import util.FetchClass;

import java.lang.Class;

public class CompositePattern {
	void setTable() throws IllegalArgumentException, IllegalAccessException {
		Connection con = DBhelper.getConnection();
		PreparedStatement pt;
		CompositePattern cp = new CompositePattern();
		int count = 0;
		try {
			Class[] ans = FetchClass.getClasses("MVCPatternDesign");
			int i = 0;
			for (Class a : ans) {
				pt = con.prepareStatement("insert into mainelement(element_id,main_element_name) values(?,?)");
				pt.setInt(1, i);
				String name = a.getName().toString().substring(a.getName().toString().lastIndexOf(".") + 1);
				pt.setString(2, name);
				i++;
			}
			i = 0;
			int j = 2;
			for (Class a : ans) {
				Field[] F = a.getDeclaredFields();
				System.out.println("Class is" + a.getName());
				for (Field f : F) {
					if (f.getType().toString().contains("[")) {
						pt = con.prepareStatement(
								"insert into subelement(sub_element_id,sub_element_name,"+
						"subelement_datatype,pattern_main_element,pattern_id) values(?,?,?,?,?)");
						pt.setInt(1, j);
						pt.setString(2, f.getName());
						String type = f.getType().toString().substring(f.getType().toString().lastIndexOf(".") + 1);
						pt.setString(3, "variable");
						pt.setInt(4, i);
						pt.setInt(5, 1);
					pt.executeUpdate();
						j++;
					}}
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
				Field[] F = a.getDeclaredFields();
				System.out.println("Class is" + a.getName());
				int count=0;
				for (Field f : F) {

					if (f.getType().toString().contains("[")) {
					    count++;
						j++;
					}
				}
				pt=con.prepareStatement("update mainelement set composite = ? where element_id=?");
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

		CompositePattern cp = new CompositePattern();
		 cp.setTable();
		 cp.setSubElementPattern();
	
	
	}

}
