package ru.averkiev.gateway.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.averkiev.gateway.dto.UserCreateDTO;
import ru.averkiev.gateway.dto.UserDTO;
import ru.averkiev.gateway.views.UserModelView;

public interface UserService {
    UserModelView createUser(final UserCreateDTO userCreateDTO);

    UserModelView updateUser(final Long userId, final UserDTO userDto);

    UserModelView getInfoOnlyActiveUser(final Long userId);

    UserModelView getInfoUser(final Long userId);

    Page<UserModelView> getAllActiveUsers(final Pageable pageRequest);

    Page<UserModelView> getAllUsers(final Pageable pageRequest);

    void softDeleteUser(final Long userId);
}
