package cn.jbit.smbms.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.sun.istack.internal.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Past;
import java.util.Date;

public class User {
    private int id; //主键ID,
//    @NotNull()
    private String userCode;// 用户编码,
    private String userName;// 用户名称,
    private String userPassword;// 用户密码,
    private int gender;// 性别（1:女、 2:男）,
    @Past(message = "必须是一个过去的时间")
    @DateTimeFormat(pattern = "YYYY-MM-dd")    //如果通过文本框输入或者选择之后保存在数据库通过注解来转化
    @JSONField(format="YYYY-MM-dd")   //默认通过ajax json解析后转化为的格式
    private Date birthday;// 出生日期,
    private String phone;// 手机,
    private String address;// 地址,
    private int userRole;// 用户角色（取自角色表-角色id）,
    private int createdBy;// 创建者（userId）,
    private int modifyBy;// 更新者（userId）,
    private Date creationDate;// 创建时间,
    private Date modifyDate;
    private Role role;
    private  int age;
    private String idPhoto;  //证件照

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    //    private String userRoleName;  //用户角色名
//
//    public String getUserRoleName() {
//        return userRoleName;
//    }
//    public void setUserRoleName(String userRoleName) {
//        this.userRoleName = userRoleName;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(int modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", userCode='" + userCode + '\'' +
            ", userName='" + userName + '\'' +
            ", userPassword='" + userPassword + '\'' +
            ", gender=" + gender +
            ", birthday=" + birthday +
            ", phone='" + phone + '\'' +
            ", address='" + address + '\'' +
            ", userRole=" + userRole +
            ", createdBy=" + createdBy +
            ", creationDate=" + creationDate +
            ", modifyBy=" + modifyBy +
            ", modifyDate=" + modifyDate +
            '}';
}

    public User(String userCode, String userPassword) {
        this.userCode = userCode;
        this.userPassword = userPassword;
    }

    public User() {
    }
}
