package com.example.dell.chatdemoreally.javabean;

/**
 * Created by dell on 2017/12/13.
 */

public class chatter {
    private Boolean isStudent;//区分学生和创建者(false为创建者，true为学生)
    private String message;//聊天信息

    public Boolean getIsStudent(){
        return isStudent;
    }
    public void setIsStudent(Boolean isStudent){
        this.isStudent = isStudent;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
}
