package ru.averkiev.budget.services.impl;

import io.micrometer.common.util.internal.logging.InternalLogger;
import io.micrometer.common.util.internal.logging.InternalLoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.averkiev.budget.dto.UserCreateDTO;
import ru.averkiev.budget.dto.UserDTO;
import ru.averkiev.budget.enums.EntityStatus;
import ru.averkiev.budget.exceptions.*;
import ru.averkiev.budget.models.User;
import ru.averkiev.budget.repositories.UserRepository;
import ru.averkiev.budget.services.UserService;
import ru.averkiev.budget.views.UserModelView;

import java.util.List;

import static ru.averkiev.budget.utils.Utils.encodeToBase64;

@Service
@SuppressWarnings("unused")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final InternalLogger logger = InternalLoggerFactory.getInstance(this.getClass());

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserModelView createUser(UserCreateDTO userCreateDTO) {
        final User user = modelMapper.map(userCreateDTO, User.class);
        final String email = user.getEmail().toLowerCase();
        final String username = user.getUsername();

        this.checkForEmptyUsername(username);
        this.checkUniqueUsername(username);
        this.checkForEmptyEmail(email);
        this.checkUniqueEmail(email);

        user.setStatus(EntityStatus.ACTIVE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUsername(username);
        user.setEmail(email);

        final User savedUser = userRepository.save(user);
        logger.info("Пользователь {} успешно сохранён", username);
        return modelMapper.map(savedUser, UserModelView.class);
    }

    @Override
    public UserModelView updateUser(final Long userId, final UserDTO userDto) {
        final User savedUser = modelMapper.map(findActiveUserByIdOrElseThrow(userId, userRepository), User.class);
        final User updatedUser = modelMapper.map(userDto, User.class);

        this.updateUsername(savedUser, updatedUser);
        this.updateEmail(savedUser, updatedUser);
        this.updatePassword(savedUser, updatedUser);

        final User hasUpdatedUser = userRepository.save(savedUser);
        final String username = hasUpdatedUser.getUsername();
        logger.info("Пользователь {} успешно обновлён", username);
        return modelMapper.map(hasUpdatedUser, UserModelView.class);
    }

    @Override
    public UserModelView getInfoUser(final Long userId) {
        final User findUser = this.findUserByIdOrElseThrow(userId);
        return modelMapper.map(findUser, UserModelView.class);
    }

    @Override
    public UserModelView getInfoOnlyActiveUser(final Long userId) {
        final User findUser = findActiveUserByIdOrElseThrow(userId, userRepository);
        return modelMapper.map(findUser, UserModelView.class);
    }

    @Override
    public Page<UserModelView> getAllActiveUsers(final Pageable pageRequest) {
        final Page<User> userPage = userRepository.findAll(pageRequest);
        final List<UserModelView> users = userPage.stream()
                .filter(user -> user.getStatus().equals(EntityStatus.ACTIVE))
                .map(user -> modelMapper.map(user, UserModelView.class))
                .toList();
        return new PageImpl<>(users, pageRequest, userPage.getTotalPages());
    }

    @Override
    public Page<UserModelView> getAllUsers(final Pageable pageRequest) {
        return userRepository.findAll(pageRequest).map(user -> modelMapper.map(user, UserModelView.class));
    }

    @Override
    public void softDeleteUser(final Long userId) {
        final User findUser = findActiveUserByIdOrElseThrow(userId, userRepository);
        findUser.setStatus(EntityStatus.DELETED);
        userRepository.save(findUser);
    }

    private void checkForEmptyUsername(final String username) {
        if (StringUtils.isEmpty(username)) {
            logger.error("Username не может быть пустым");
            throw new UsernameValidationException("Username не может быть пустым");
        }
    }

    private void checkUniqueUsername(final String username) {
        if (userRepository.existsUserByUsername(encodeToBase64(username))) {
            final String msg = String.format("Username %s уже занят другим пользователем", username);
            logger.error(msg);
            throw new UsernameValidationException(msg);
        }
    }

    private User findUserByIdOrElseThrow(final Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("Пользователь с id = %d не найден", userId)));
    }

    static User findActiveUserByIdOrElseThrow(final Long userId, final UserRepository userRepository) {
        return userRepository
                .findById(userId)
                .filter(user -> user.getStatus().equals(EntityStatus.ACTIVE))
                .orElseThrow(() -> new UserNotFoundException(String.format("Пользователь с id = %d не найден", userId)));
    }

    private void checkUniqueEmail(final String email) {
        if (userRepository.existsUserByEmail(encodeToBase64(email))) {
            final String msg = String.format("Email %s уже занят другим пользователем", email);
            logger.error(msg);
            throw new EmailAlreadyExistException(msg);
        }
    }

    private void checkForEmptyEmail(final String email) {
        if (StringUtils.isEmpty(email)) {
            logger.error("Email не может быть пустым");
            throw new EmailValidationException("Email не может быть пустым");
        }
    }

    private void updateUsername(final User savedUser, final User updatedUser) {
        final String updatedUsername = updatedUser.getUsername();
        final String savedUsername = savedUser.getUsername();
        if (!StringUtils.isEmpty(updatedUsername) && !savedUsername.equals(updatedUsername)) {
            checkUniqueUsername(updatedUsername);
            savedUser.setUsername(updatedUsername);
        }
    }

    private void updateEmail(final User savedUser, final User updatedUser) {
        final String updatedEmail = updatedUser.getEmail().toLowerCase();
        final String savedEmail = savedUser.getEmail();
        if (!StringUtils.isEmpty(updatedEmail) && !savedEmail.equals(updatedEmail)) {
            checkUniqueEmail(updatedEmail);
            savedUser.setEmail(updatedEmail);
        }
    }

    private void updatePassword(final User savedUser, final User updatedUser) {
        final String savedPassword = savedUser.getPassword();
        final String updatedPassword = updatedUser.getPassword();
        if (!StringUtils.isEmpty(updatedPassword)) {
            final String encodedPassword = passwordEncoder.encode(updatedPassword);
            if (savedPassword.equals(encodedPassword)) {
                final String msg = "Данный пароль уже установлен";
                logger.error(msg);
                throw new PasswordAlreadyExistException(msg);
            }
            savedUser.setPassword(encodedPassword);
        }
    }
}
