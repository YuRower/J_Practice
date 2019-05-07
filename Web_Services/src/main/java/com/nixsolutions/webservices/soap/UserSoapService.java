package com.nixsolutions.webservices.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.nixsolutions.exception.UserSoapFail;
import com.nixsolutions.model.User;

@WebService
public interface UserSoapService {

	@WebMethod
	public String create(@WebParam(name = "user") User user) throws UserSoapFail;

	@WebMethod
	public String update(@WebParam(name = "user") User user) throws UserSoapFail;

	@WebMethod
	public String remove(@WebParam(name = "login") String login) throws UserSoapFail;

	@WebMethod
	@WebResult(name = "user")
	public User findByLogin(@WebParam(name = "login") String login) throws UserSoapFail;

	@WebMethod
	@WebResult(name = "usersList")
	public List<User> findAll();

}
