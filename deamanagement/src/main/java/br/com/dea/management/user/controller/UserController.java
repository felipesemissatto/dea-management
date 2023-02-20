package br.com.dea.management.user.controller;

import br.com.dea.management.student.dto.StudentDto;
import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "Load the list of user paginated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Page or Page Size params not valid"),
            @ApiResponse(responseCode = "500", description = "Error fetching student list"),
    })
    @GetMapping("/user")
    public Page<User> getUsers(@RequestParam(required = true) Integer page,
                               @RequestParam(required = true) Integer pageSize) {

        Page<User> usersPaged = this.userService.findAllUsersPaginated(page, pageSize);
        return usersPaged;
    }

    @Operation(summary = "Load the user by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "User Id invalid"),
            @ApiResponse(responseCode = "404", description = "User Not found"),
            @ApiResponse(responseCode = "500", description = "Error fetching student list"),
    })
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {

        log.info(String.format("Fetching user by id : Id : %s", id));

        User user = this.userService.findUserById(id);

        log.info(String.format("User loaded successfully : Student : %s", user));

        return user;

    }
}
