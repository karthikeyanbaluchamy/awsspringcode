package com.sys.user.controller;


import com.sys.user.model.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class userController {

    // Add logger
    private static final Logger logger = LoggerFactory.getLogger(userController.class);

    public static List<UserData> userList = new ArrayList<>();

    static {
        logger.info("Initializing user data...");
        UserData d1 = new UserData();
        d1.setUserName("karthi");
        d1.setAddress("502 north park lane");
        UserData d2 = new UserData();
        d2.setUserName("navi");
        d2.setAddress("5107 schindler drive");
        userList.add(d1);
        userList.add(d2);
        logger.info("User data initialized with {} users", userList.size());
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserData>> getAllUsers() {
        logger.info("GET /users/getUsers - Fetching all users");

        if (userList.isEmpty()) {
            logger.warn("No users found in the list");
            return ResponseEntity.noContent().build(); // 204
        }

        logger.info("Returning {} users", userList.size());
        return ResponseEntity.ok(userList); // 200
    }

    @GetMapping("/userdetails/{name}")
    public ResponseEntity<UserData> getUserByName(@PathVariable String name) {
        logger.info("GET /users/userdetails/{} - Fetching user by name", name);

        UserData d = userList.stream()
                .filter(e -> e.getUserName().equals(name))
                .findFirst()
                .orElseGet(() -> {
                    logger.warn("User not found: {}", name);
                    return new UserData("Unknown", "N/A");
                });

        logger.info("User details retrieved for: {}", name);
        return ResponseEntity.ok(d);
    }

    @PostMapping("/updatedata")
    public ResponseEntity<UserData> updateDetails(@RequestParam String name, @RequestBody UserData data) {
        logger.info("POST /users/updatedata - Updating user: {}", name);
        logger.debug("Update payload: {}", data);

        UserData user = userList.stream()
                .filter(u -> u.getUserName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);

        if (user == null) {
            logger.error("Update failed - User not found: {}", name);
            return ResponseEntity.notFound().build(); // 404
        }

        if (data.getAddress() != null) {
            String oldAddress = user.getAddress();
            user.setAddress(data.getAddress());
            logger.info("User {} address updated from '{}' to '{}'", name, oldAddress, data.getAddress());
        }

        return ResponseEntity.ok(user);
    }
}




