package com.ikeda.authuser.services;

import com.ikeda.authuser.dtos.UserRecordDto;
import com.ikeda.authuser.models.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<UserModel> findAll();

    Optional<UserModel> findById(UUID userId);

    void delete(UserModel userModel);

    UserModel registerUser(UserRecordDto userRecordDto);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    UserModel updateUser(UserRecordDto userRecordDto, UserModel userModel);

    UserModel updatePassword(UserRecordDto userRecordDto, UserModel userModel);

    UserModel updateImagem(UserRecordDto userRecordDto, UserModel userModel);
}
