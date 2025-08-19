package com.example.pf4j.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Java Admin 实体类
 * 对应数据库表：java_admin
 * 
 * @author PF4J Framework
 * @version 1.0.0
 */
@Entity
@Table(name = "java_admin")
public class JavaAdmin {
    
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Size(max = 50, message = "用户名长度不能超过50个字符")
    @Column(name = "username", nullable = false, length = 50)
    private String username;
    
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(max = 255, message = "密码长度不能超过255个字符")
    @Column(name = "password", nullable = false)
    private String password;
    
    /**
     * 邮箱
     */
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    @Column(name = "email", length = 100)
    private String email;
    
    /**
     * 手机号
     */
    @Size(max = 20, message = "手机号长度不能超过20个字符")
    @Column(name = "phone", length = 20)
    private String phone;
    
    /**
     * 状态：0-禁用，1-启用
     */
    @Column(name = "status", nullable = false)
    private Integer status = 1;
    
    /**
     * 角色：admin-管理员，user-普通用户
     */
    @Size(max = 20, message = "角色长度不能超过20个字符")
    @Column(name = "role", length = 20)
    private String role = "user";
    
    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;
    
    /**
     * 备注
     */
    @Column(name = "remark", columnDefinition = "TEXT")
    private String remark;
    
    /**
     * 默认构造函数
     */
    public JavaAdmin() {
    }
    
    /**
     * 构造函数
     */
    public JavaAdmin(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * JPA生命周期回调：保存前
     */
    @PrePersist
    protected void onCreate() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * JPA生命周期回调：更新前
     */
    @PreUpdate
    protected void onUpdate() {
        this.updateTime = LocalDateTime.now();
    }
    
    // Getter and Setter methods
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JavaAdmin javaAdmin = (JavaAdmin) o;
        return Objects.equals(id, javaAdmin.id) &&
               Objects.equals(username, javaAdmin.username);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
    
    @Override
    public String toString() {
        return "JavaAdmin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", role='" + role + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}