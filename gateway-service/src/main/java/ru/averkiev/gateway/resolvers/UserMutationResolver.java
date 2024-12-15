package ru.averkiev.gateway.resolvers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import org.springframework.beans.factory.annotation.Autowired;
import ru.averkiev.gateway.dto.UserCreateDTO;
import ru.averkiev.gateway.dto.UserDTO;
import ru.averkiev.gateway.services.UserService;
import ru.averkiev.gateway.views.UserModelView;

@DgsComponent
public class UserMutationResolver {

    private final UserService userService;

    @Autowired
    public UserMutationResolver(UserService userService) {
        this.userService = userService;
    }

    // Мутация для создания нового пользователя
    @DgsMutation
    public UserModelView createUser(UserCreateDTO input) {

        return userService.createUser(input);
    }

    // Мутация для обновления существующего пользователя
    @DgsMutation
    public UserModelView updateUser(Long id, UserDTO input) {
        return userService.updateUser(id, input);
    }

    // Мутация для удаления (логического) пользователя
    @DgsMutation
    public Boolean deleteUser(Long id) {
        try {
            userService.softDeleteUser(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
