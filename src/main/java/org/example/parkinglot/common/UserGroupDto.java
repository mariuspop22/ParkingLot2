package org.example.parkinglot.common;

public class UserGroupDto {
    private Long id;
    private String username;
    private String User_group;
    public UserGroupDto() {

    }
    public UserGroupDto(Long id, String username, String User_group) {
        this.id = id;
        this.username = username;
        this.User_group = User_group;
    }
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
    public String getUser_group() {
        return User_group;
    }
    public void setUser_group(String user_group) {
        User_group = user_group;
    }


}
