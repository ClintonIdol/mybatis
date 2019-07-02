/**
 * Author:   LiXiaoPeng
 * Date:     2019/6/29 18:55
 * Description:
 */
package com.test.mybatis.po;

public class User {
    private Integer id;

    private String name;

    private String address;

    private String fav;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public User(Integer id) {
        this.id = id;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", fav='" + fav + '\'' +
                '}';
    }
}
