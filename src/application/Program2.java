package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		DepartmentDAO departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("\n==== TEST 1: Department Insert ====");
		
		Department dep = new Department(0,"Clothes");
		
		departmentDao.insert(dep);
		
		System.out.println("Novo departamento gerado: " + dep);

	}

}
