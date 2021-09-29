package fr.eservices.drive.dao;

import fr.eservices.drive.model.User;
import fr.eservices.drive.util.PasswordChecker;

public abstract class UserDao implements IUserDao {
	
	PasswordChecker pwdCheck;
	
	public void setPwdCheck(PasswordChecker pwdCheck) {
		this.pwdCheck = pwdCheck;
	}
	
	protected abstract void doChangePassword(String login, String password);
	
	@Override
	public void setPassword(String login, String password) {
		doChangePassword( login, pwdCheck.encode(login, password) );
	}
	
	@Override
	public boolean tryLogin(String login, String password) {
		User u = find(login);
		String pwd = u.getPassword();
		if ( pwd == null ) return false;
		return pwd.equals( pwdCheck.encode(login, password) );
	}
	

}
