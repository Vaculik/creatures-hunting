package cz.muni.fi.pa165.hateoas;

import java.sql.Date;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import cz.muni.fi.pa165.dto.UserSystemDTO;
import cz.muni.fi.pa165.enums.SexType;
import cz.muni.fi.pa165.enums.UserType;

@Relation(value = "user", collectionRelation = "users")
@JsonPropertyOrder(value = {"id", "name", "type", "sex", "dateOfBirth", "userName", "password"})
public class UserSystemResource extends ResourceSupport {
	
	@JsonProperty("id")
	private long id;
	
    private String name;
    private UserType type;
    private SexType sex;
    private Date dateOfBirth;
    private String userName;
    private Integer password;
    
    public UserSystemResource(UserSystemDTO userDTO) {
    	this.id = userDTO.getId();
    	this.name = userDTO.getName();
    	this.type = userDTO.getType();
    	this.sex = userDTO.getSex();
    	this.dateOfBirth = userDTO.getDateOfBirth();
    	this.userName = userDTO.getUserName();
    	this.password = userDTO.getPassword();
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public SexType getSex() {
		return sex;
	}

	public void setSex(SexType sex) {
		this.sex = sex;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getPassword() {
		return password;
	}

	public void setPassword(Integer password) {
		this.password = password;
	}
}
