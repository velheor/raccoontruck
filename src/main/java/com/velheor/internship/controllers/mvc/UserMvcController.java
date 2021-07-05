package com.velheor.internship.controllers.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velheor.internship.dto.form.UserViewDtoForm;
import com.velheor.internship.mappers.UserMapper;
import com.velheor.internship.models.User;
import com.velheor.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mvc")
public class UserMvcController {

    private final static String usersView = "users";
    private final static String userView = "user";
    private final UserService userService;
    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;

    @GetMapping("/users")
    @SneakyThrows
    public String getAllUsers(Model model) {
        model.addAttribute("userJson", objectMapper.writeValueAsString(userMapper.toUserViewDto(userService.getAll())));
        return usersView;
    }

    @PostMapping("/users")
    @SneakyThrows
    public String saveAllUsers(@ModelAttribute("userViewDtoForm") UserViewDtoForm userViewDtoForm, Model model) {
        userService.saveAll(userMapper.toUser(userViewDtoForm.getUserViewDtos()));
        model.addAttribute("userJson", objectMapper.writeValueAsString(userMapper.toUserViewDto(userService.getAll())));
        return usersView;
    }

    @GetMapping("/users/{id}")
    @SneakyThrows
    public String getUserInfo(@PathVariable UUID id, Model model) {
        model.addAttribute("user", userMapper.toUserViewDto(userService.findById(id)));
        return userView;
    }
}
