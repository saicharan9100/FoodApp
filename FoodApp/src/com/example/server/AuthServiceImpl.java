package com.example.server;

import com.example.client.service.AuthService;
import com.example.daoimpl.UserDaoImpl;
import com.example.domain.User;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import javax.servlet.http.HttpSession;

public class AuthServiceImpl extends RemoteServiceServlet implements AuthService{
	private UserDaoImpl userDao;
	@Override
	public User authenticateUser(String username, String password) {
		// TODO Auto-generated method stub
		userDao=(UserDaoImpl) ApplicationContextListener.applicationContext.getBean("userDao");
		
		User user=userDao.findByUsername(username);
		if(user!=null&&user.getPassword().equals(password)){
			HttpSession session = getThreadLocalRequest().getSession();
	        session.setAttribute("currentUser", user);
	        return user;
		}
		return null;
	}
	@Override
	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		userDao=(UserDaoImpl) ApplicationContextListener.applicationContext.getBean("userDao");
		User user=userDao.getUserById(id);
		return user;
	}
	@Override
	public User getCurrentUser() {
		// TODO Auto-generated method stub
		HttpSession session = getThreadLocalRequest().getSession();
        if (session != null) {
            return (User) session.getAttribute("currentUser");
        }
		return null;
	}
	@Override
	public void signupUser(String username, String password, String email) {
		// TODO Auto-generated method stub
		userDao=(UserDaoImpl) ApplicationContextListener.applicationContext.getBean("userDao");
		User user = new User();
	    user.setUsername(username);
	    user.setPassword(password);
	    user.setEmail(email);
	    user.setRoleId(2);
		userDao.saveUser(user);
	}
	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		userDao=(UserDaoImpl) ApplicationContextListener.applicationContext.getBean("userDao");
		userDao.updateUser(user);
	}
}

