package ca.ezlock.it.ezpark;

public class ReadWriteUserDetails {
    public String fullname,email,phone,pwd;

    public ReadWriteUserDetails(String textfullname, String textEmail, String textPhone, String textPwd)
    {
        this.fullname=textfullname;
        this.email=textEmail;
        this.phone=textPhone;
        this.pwd=textPwd;
    }
    public void RegistrationScreen()
    {

    }
    public String getfullname()
    {
        return fullname;
    }
    public String getemail()
    {
        return email;
    }
    public String getphone()
    {
        return phone;
    }
    public String getpwd()
    {
        return pwd;
    }
}
