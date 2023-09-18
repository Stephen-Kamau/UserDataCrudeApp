package com.userdata.userdatacrud.controlers;

import com.userdata.userdatacrud.models.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private  UserRepository userRepository;
    @GetMapping("")
    public List<UserData> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("")
    public ResponseEntity<UserData> addUser(@RequestBody UserData user) {
        UserData savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserData> getUserById(@PathVariable("id") Long id) {
        UserData user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        UserData user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserData> updateUserById(@PathVariable("id") Long id,  @RequestBody UserData updateUser){
//        first we need to get the user present
        UserData user = userRepository.findById(id).orElse(null);

        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            user.setEmail(updateUser.getEmail());
            user.setName(updateUser.getName());
            user.setPassword(user.getPassword());
            UserData updatedUser = userRepository.save(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
    }



}
