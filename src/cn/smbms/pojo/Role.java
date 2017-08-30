package cn.smbms.pojo;

import java.sql.Date;

/**
 * 角色实体类
 * @author Administrator
 *
 */
public class Role {
   private  Integer id;//Id
   private String roleCode;//角色编码
   private String roleName;//角色名称
   private Integer createdBy;//创建者
   private Date creationDate;//创建时间
   private Integer modifyBy;//修改者
   private Date modifeDate;//修改时间
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getRoleCode() {
	return roleCode;
}
public void setRoleCode(String roleCode) {
	this.roleCode = roleCode;
}
public String getRoleName() {
	return roleName;
}
public void setRoleName(String roleName) {
	this.roleName = roleName;
}
public Integer getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(Integer createdBy) {
	this.createdBy = createdBy;
}
public Date getCreationDate() {
	return creationDate;
}
public void setCreationDate(Date creationDate) {
	this.creationDate = creationDate;
}
public Integer getModifyBy() {
	return modifyBy;
}
public void setModifyBy(Integer modifyBy) {
	this.modifyBy = modifyBy;
}
public Date getModifeDate() {
	return modifeDate;
}
public void setModifeDate(Date modifeDate) {
	this.modifeDate = modifeDate;
}

}
