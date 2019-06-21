package com.fate.spring.jndi.entity;

import javax.persistence.*;

@Table(name = "`user`")
public class User {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`username`")
    private String username;

    @Column(name = "`psw`")
    private String psw;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return psw
     */
    public String getPsw() {
        return psw;
    }

    /**
     * @param psw
     */
    public void setPsw(String psw) {
        this.psw = psw;
    }
}