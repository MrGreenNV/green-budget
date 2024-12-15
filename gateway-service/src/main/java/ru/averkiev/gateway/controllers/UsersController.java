package ru.averkiev.gateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.gateway.dto.UserCreateDTO;
import ru.averkiev.gateway.dto.UserDTO;
import ru.averkiev.gateway.services.UserService;
import ru.averkiev.gateway.views.UserModelView;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@SuppressWarnings("unused")
public class UsersController {

    private static final String CREATE_USER = "create-user";
    private static final String EDIT_USER = "edit-user";
    private static final String SHOW_USER = "show-user";
    private static final String SHOW_ACTIVE_USER = "show-active-user";
    private static final String SHOW_ALL_USERS = "show-all-user";
    private static final String SHOW_ALL_ACTIVE_USERS = "show-all-active-user";
    private static final String SOFT_DELETE_USER = "soft-delete-user";
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_NUM = 10;
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<EntityModel<UserModelView>> registrationUser(@RequestBody final UserCreateDTO userCreateDTO) {
        final UserModelView user = userService.createUser(userCreateDTO);
        final EntityModel<UserModelView> model = EntityModel.of(user);
        return ResponseEntity.ok(model);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<EntityModel<UserModelView>> editUser(@PathVariable final Long userId, @RequestBody final UserDTO userDTO) {
        final UserModelView user = userService.updateUser(userId, userDTO);
        final EntityModel<UserModelView> model = EntityModel.of(user);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<EntityModel<UserModelView>> showUser(@PathVariable final Long userId) {
        final UserModelView user = userService.getInfoUser(userId);
        final EntityModel<UserModelView> model = EntityModel.of(user);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/active/{userId}")
    public ResponseEntity<EntityModel<UserModelView>> showOnlyActiveUser(@PathVariable final Long userId) {
        final UserModelView user = userService.getInfoOnlyActiveUser(userId);
        final EntityModel<UserModelView> model = EntityModel.of(user);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/active")
    public ResponseEntity<PagedModel<UserModelView>> showAllActiveUsers(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                        @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize) {
        final Page<UserModelView> userPage = userService.getAllActiveUsers(PageRequest.of(page, pageSize));
        final List<UserModelView> usersWithLinks = userPage.getContent()
                .stream()
                .toList();

        final PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                pageSize,
                userPage.getNumber(),
                userPage.getTotalElements(),
                userPage.getTotalPages()
        );

        final PagedModel<UserModelView> pagedModel = PagedModel.of(usersWithLinks, pageMetadata);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping()
    public ResponseEntity<PagedModel<UserModelView>> showAllUsers(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                  @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize) {
        final Page<UserModelView> userPage = userService.getAllUsers(PageRequest.of(page, pageSize));
        final List<UserModelView> usersWithLinks = userPage.getContent()
                .stream()
                .toList();

        final PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                pageSize,
                userPage.getNumber(),
                userPage.getTotalElements(),
                userPage.getTotalPages()
        );

        final PagedModel<UserModelView> pagedModel = PagedModel.of(usersWithLinks, pageMetadata);
        return ResponseEntity.ok(pagedModel);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> softDeleteUser(@PathVariable final Long userId) {
        userService.softDeleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
