package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		DepartmentDAO departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("\n==== TEST 1: Department Insert ====");
		
		Department dep = new Department(0,"Clothes");
		
		departmentDao.insert(dep);
		
		System.out.println("Novo departamento gerado: " + dep);
		
		
		System.out.println("\n==== TEST 2: Department Update ====");
		dep = departmentDao.findById(1);
		dep.setName("Food");		
		departmentDao.update(dep);
		System.out.println("Update realizado!");
		
		
		System.out.println("\n==== TEST 3: Department findById ====");
		dep = departmentDao.findById(3);
		
		System.out.println(dep);
		
		System.out.println("\n==== TEST 4: Department deleteById ====");
		System.out.print("Enter id for delete: ");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		
		System.out.println("Delete completed!");
		
		System.out.println("\n==== TEST 5: Department findAll ====");
		List<Department> list = departmentDao.findAll();
		
		for(Department obj : list) {
			System.out.println(obj);
		}
		
		
	}

}
