package ru.averkiev.budget.resolvers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.averkiev.budget.services.UserService;
import ru.averkiev.budget.views.UserModelView;

import java.util.List;

@DgsComponent
public class UserQueryResolver {

    private final UserService userService;

    @Autowired
    public UserQueryResolver(UserService userService) {
        this.userService = userService;
    }

    // Запрос для получения информации о пользователе по ID
    @DgsData(parentType = "Query", field = "getUserInfo")
    public UserModelView getUserInfo(Long id) {
        return userService.getInfoUser(id);
    }

    // Запрос для получения всех активных пользователей с пагинацией
    @DgsData(parentType = "Query", field = "getAllActiveUsers")
    public List<UserModelView> getAllActiveUsers(Integer page, Integer size) {
        Page<UserModelView> usersPage = userService.getAllActiveUsers(PageRequest.of(page, size));
        return usersPage.getContent();
    }

    // Запрос для получения всех пользователей с пагинацией
    @DgsData(parentType = "Query", field = "getAllUsers")
    public List<UserModelView> getAllUsers(Integer page, Integer size) {
        Page<UserModelView> usersPage = userService.getAllUsers(PageRequest.of(page, size));
        return usersPage.getContent();
    }
}
