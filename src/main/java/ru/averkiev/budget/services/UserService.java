package ru.averkiev.budget.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.averkiev.budget.dto.UserCreateDTO;
import ru.averkiev.budget.dto.UserDTO;
import ru.averkiev.budget.views.UserModelView;

public interface UserService {
    UserModelView createUser(final UserCreateDTO userCreateDTO);

    UserModelView updateUser(final Long userId, final UserDTO userDto);

    UserModelView getInfoOnlyActiveUser(final Long userId);

    UserModelView getInfoUser(final Long userId);

    Page<UserModelView> getAllActiveUsers(final Pageable pageRequest);

    Page<UserModelView> getAllUsers(final Pageable pageRequest);

    void softDeleteUser(final Long userId);
}