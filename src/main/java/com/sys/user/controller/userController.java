package com.sys.user.controller;


import com.sys.user.model.UserData;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")

public class userController {

    public static List<UserData> userList= new ArrayList<>();

  static {
      UserData d1 = new UserData();
      d1.setUserName("karthi");
      d1.setAddress("502 north park lane");
      UserData d2 = new UserData();
      d2.setUserName("navi");
      d2.setAddress("5107 schindler drive");
      userList.add(d1);
      userList.add(d2);

  }


    @GetMapping("/getUsers")
    public ResponseEntity<List<UserData>> getAllUsers() {


        if (userList.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.ok(userList); // 200
    }

    @GetMapping("/userdetails/{name}")
    public ResponseEntity<UserData> getUserByName(@PathVariable String name) {

      UserData d= userList.stream().filter( e -> e.getUserName().equals(name)).findFirst().orElseGet(() -> new UserData("Unknown", "N/A"));


      return ResponseEntity.ok(d);
    }

    @PostMapping("/updatedata")
    public  ResponseEntity<UserData> updateDetails(@RequestParam String name,@RequestBody UserData data){
        UserData d= userList.stream().filter( e -> e.getUserName().equals(name)).findFirst().orElseGet(() -> new UserData("Unknown", "N/A"));
        UserData user = userList.stream()
                .filter(u -> u.getUserName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build(); // 404 if user not found
        }
        if (data.getAddress() != null) {
            user.setAddress(data.getAddress());
        }

        return ResponseEntity.ok(user);
    }
}




