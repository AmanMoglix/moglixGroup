package com.org.moglix.dao.impl;
import com.org.moglix.dao.EmployeeDao;
import com.org.moglix.domain.Employee;

public class EmployeeDaoImpl implements EmployeeDao {
	private static int counter = 0;
	Employee employees[] = new Employee[10];

	@Override
	public String addEmployee(Employee employee) {
		if (counter < employees.length) {
			employees[counter++] = employee;
			return "successfully added ..!";
		} else {
			return "Array is Full";
		}
	}

	@Override
	public Employee getEmployeeById(String empId) {
		for (Employee emp : employees) {
			if (emp != null && emp.getEmp_id().equalsIgnoreCase(empId)) {
				return emp;
			}
		}
		return null;
	}

	@Override
	public String updateEployee(Employee employee, String empId) {
		for (Employee emp : employees) {
			if (emp != null && emp.getEmp_id().equalsIgnoreCase(empId)) {
				emp.setEmp_firstName(employee.getEmp_firstName());
				emp.setEmpAddress(employee.getEmpAddress());
				emp.setEmpSalary(employee.getEmpSalary());
			}
		}
		return "updated Successfully";
	}

	@Override
	public Employee[] getList() {
		return employees;
	}

	@Override
	public String deleteEmployeById(String empId) {
		this.counter=0;
		for(Employee emp:employees) {
			if(emp!=null && emp.getEmp_id().equalsIgnoreCase(empId)) {
				
				System.out.println(counter);
	this.removeTheEmployee(employees, counter);
	counter++;

				return "Deleted SuccessFully";
			}else {
				counter++;
			}
		}
		
		return "Deleted successFully  ..!";
	}

	public static Employee[] removeTheEmployee(Employee[] arr, int index) {


		if (arr == null || index < 0 || index >= arr.length) {
			return arr;
		}
		Employee[] employeeUpdated = new Employee[arr.length - 1];

		for (int i = 0, k = 0; i < arr.length; i++) {


			if (i == index) {
				continue;
			}

			employeeUpdated[k++] = arr[i];
		}

		return employeeUpdated;
	}
}
