package core.packet;

import core.pojos.UserClient;

import java.io.Serializable;

public class LoginPack extends InfoPack implements Serializable {
    //0 - fine, log as
    //1 - shit
    protected int retCode = 0;
    protected UserClient.UserServer user;

    public UserClient.UserServer getUser() {
        return user;
    }

    public void setUser(UserClient.UserServer user) {
        this.user = user;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }
}
