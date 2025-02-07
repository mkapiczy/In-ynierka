package pl.mkapiczynski.dron.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Konto użytkownika aplikacji DronVision
 * @author Michal Kapiczynski
 *
 */
@Entity
public class UserAccount {
	@Id
	@GeneratedValue
	@Column(name="account_id")
	private Long accountId;

	private String login;

	private String password;

	private String firstName;

	private String lastName;

	private String email;

	@OneToOne(mappedBy="userAccount")
	private ClientUser accountUser;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ClientUser getCustomerUser() {
		return accountUser;
	}

	public void setCustomerUser(ClientUser customerUser) {
		this.accountUser = customerUser;
	}

}
