package net.edrop.edrop_user.entity;

/**
 * @ClassName: Contacts
 * @Description: 
 * @author 13071
 * @date 2019年12月17日
 *
 */
public class Contacts {
	private Integer id;
	private Integer userId;
	private Integer employeeId;
	private User user;
	private Employee employee;
	
	public Contacts() {
		super();
	}

	public Contacts(Integer id, Integer userId, Integer employeeId, User user, Employee employee) {
		super();
		this.id = id;
		this.userId = userId;
		this.employeeId = employeeId;
		this.user = user;
		this.employee = employee;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "Contacts [id=" + id + ", userId=" + userId + ", employeeId=" + employeeId + ", user=" + user
				+ ", employee=" + employee + "]";
	}
}
