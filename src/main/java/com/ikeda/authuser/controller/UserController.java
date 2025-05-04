package com.ikeda.authuser.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ikeda.authuser.dtos.UserRecordDto;
import com.ikeda.authuser.models.UserModel;
import com.ikeda.authuser.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity <Object> getOneUser(@PathVariable(value = "userId") UUID userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(userId).get());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId){
        userService.delete(userService.findById(userId).get());

        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
                                             @RequestBody @JsonView(UserRecordDto.UserView.UserPut.class)
                                             UserRecordDto userRecordDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userRecordDto, userService.findById(userId).get()));
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
                                             @RequestBody @JsonView(UserRecordDto.UserView.PasswordPut.class)
                                             UserRecordDto userRecordDto){
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if (!userModelOptional.get().getPassword().equals(userRecordDto.oldPassword())){
            return ResponseEntity.status(HttpStatus.OK).body("Error: Mismatched old password!");
        }
        userService.updatePassword(userRecordDto, userModelOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully.");
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID userId,
                                             @RequestBody @JsonView(UserRecordDto.UserView.ImagePut.class)
                                             UserRecordDto userRecordDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateImagem(userRecordDto, userService.findById(userId).get()));
    }
}
