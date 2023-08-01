package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDAO {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO department " + "(Name) " + "VALUES " + "(?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}

				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?", Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ?", Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, id);
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected == 0) {
				
				throw new DbException("Id does not exist!");
			}
			
		}
		catch(SQLException e) {
			
			throw new DbException(e.getMessage());
		}
		finally {
			
			DB.closeStatement(st);
		}

	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT department.* "
					+ "FROM department "
					+ "WHERE department.Id = ? " 
					+ "ORDER BY Name", Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				int departmentId = rs.getInt("Id");
				String departmentName = rs.getString("Name");
				
				Department department = new Department(departmentId, departmentName);
				return department;
			}
			
			return null;
			
		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	
	@Override
	public List<Department> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			
			st = conn.prepareStatement(
					"SELECT department.*,department.Name as DepName "
					+"FROM department "
					+ "ORDER BY Id", Statement.RETURN_GENERATED_KEYS);
			
			rs = st.executeQuery();

			List<Department> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap();

			while (rs.next()) {

				Department dep = map.get(rs.getInt("Id"));
				
					
					 int departmentId = rs.getInt("Id");
			         String departmentName = rs.getString("DepName");
			         
			         if (!map.containsKey(departmentId)) {
			                dep = new Department(departmentId, departmentName);
			                map.put(departmentId, dep);
			            }	

				
				list.add(map.get(departmentId));

			}
			
			return list;
		} catch (SQLException e) {

			throw new DbException(e.getMessage());
		} finally {

			DB.closeStatement(st);
			DB.closeResultSet(rs);
		
		}
	}

}
