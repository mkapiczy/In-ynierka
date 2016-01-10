package pl.mkapiczynski.dron.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UserAccount")
public class UserAccount {
	@Id
	@GeneratedValue
	private Long accountId;

	private String login;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	

}
