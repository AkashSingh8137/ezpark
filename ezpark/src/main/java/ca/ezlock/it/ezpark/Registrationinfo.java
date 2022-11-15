package ca.ezlock.it.ezpark;

public class Registrationinfo {
    private String fullname, email, phone, pwd, username,ratingname,ratingemail,ratingphone,ratingcomments;
    Float ratingstar;

    public Registrationinfo() {

    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getphone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

    public String getpwd() {
        return pwd;
    }

    public void setpwd(String pwd) {
        this.pwd = pwd;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getRatingname() {
        return ratingname;
    }

    public void setRatingname(String ratingname) {
        this.ratingname = ratingname;
    }

    public String getRatingemail() {
        return ratingemail;
    }

    public void setRatingemail(String ratingemail) {
        this.ratingemail = ratingemail;
    }

    public String getRatingphone() {
        return ratingphone;
    }

    public void setRatingphone(String ratingphone) {
        this.ratingphone = ratingphone;
    }
    public String getRatingcomments() {
        return ratingcomments;
    }

    public void setRatingcomments(String ratingcomments) {
        this.ratingcomments = ratingcomments;
    }

    public Float getRatingstar() {
        return ratingstar;
    }

    public void setRatingstar(Float ratingstar) {
        this.ratingstar = ratingstar;
    }
}

