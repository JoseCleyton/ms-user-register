package com.user.register.controllers;

import com.user.register.dtos.UserDto;
import com.user.register.models.UserModel;
import com.user.register.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<UserModel> save(@RequestBody @Valid UserDto userDto) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        return new ResponseEntity(this.userService.save(userModel), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> findAll(@PageableDefault(page = 0, size = 5, sort = "userId", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity(this.userService.findAll(pageable), HttpStatus.OK);
    }
}
