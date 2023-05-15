package ru.practicum.main_service.user.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main_service.user.dto.NewUserRequest;
import ru.practicum.main_service.user.dto.UserDto;
import ru.practicum.main_service.user.model.User;

import java.util.List;

public interface UserService {

    UserDto createUser(NewUserRequest newUserRequest);

    User getUserById(Long id);

    List<UserDto> getUsersDto(List<Long> ids, Pageable pageable);

    void deleteUserById(Long id);

    void checkUserInBase(Long id);

}
