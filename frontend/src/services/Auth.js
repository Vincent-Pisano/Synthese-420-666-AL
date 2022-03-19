import {ADMIN_EMAIL, SESSION_STORAGE_USER_NAME} from "../utils/SECURITY"

class Auth {
    constructor() {
      if (sessionStorage.getItem(SESSION_STORAGE_USER_NAME) !== null) {
        this.user = JSON.parse(sessionStorage.getItem(SESSION_STORAGE_USER_NAME));
        this.authenticated = true;
      } else {
        this.user = undefined;
        this.authenticated = false;
      }
    }
  
    login(cb, user) {
      this.authenticated = true;
      this.user = user;
      sessionStorage.setItem(SESSION_STORAGE_USER_NAME, JSON.stringify(this.user));
      cb();
    }
  
    logout(cb) {
      this.authenticated = false;
      this.user = undefined;
      sessionStorage.removeItem(SESSION_STORAGE_USER_NAME);
      cb();
    }
  
    updateUser(user) {
      this.user = user;
      sessionStorage.setItem(SESSION_STORAGE_USER_NAME, JSON.stringify(this.user));
    }
  
    isAuthenticated() {
      return this.authenticated;
    }
  
    isAdministrator() {
      return this.authenticated ? this.user.email === ADMIN_EMAIL : false;
    }

    isClient() {
      return this.authenticated ? this.user.email !== ADMIN_EMAIL : false;
    }
}

export default new Auth();