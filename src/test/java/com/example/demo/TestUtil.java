package com.example.demo;

import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;

public class TestUtil {

    public static CreateUserRequest createUser() {
        return new CreateUserRequest("abc", "abcd1234", "abcd1234");
    }

    public static ModifyCartRequest createModifyCartRequest(){
        return new ModifyCartRequest("test",1L,1);
    }
}
