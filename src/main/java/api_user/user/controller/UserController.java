package api_user.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api_user.user.dto.ApiResponse;
import api_user.user.dto.UserDto;
import api_user.user.model.UserModel;
import api_user.user.service.UserService;
import api_user.util.Constants;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = Constants.DOC_REG)
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserModel>> registerUser(@Validated @RequestBody UserDto userDto) {
        try {
            UserModel user = new UserModel();
            user.setUserId(userDto.getIdentificacion());
            user.setName(userDto.getName());
            user.setLastName(userDto.getLastName());
            user.setPassword(userDto.getPassword());
            user.setPhone(userDto.getPhone());
            user.setAddress(userDto.getAddress());
            user.setEmail(userDto.getEmail());
            user.setRole(userDto.getRole());

            UserModel createdUser = userService.registerUser(user);

            ApiResponse<UserModel> response = new ApiResponse<>("200", createdUser);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            ApiResponse<UserModel> response = new ApiResponse<>("400", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<UserModel> response = new ApiResponse<>("500", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = Constants.DOC_GET)
    @GetMapping("/getUsers")
    public ResponseEntity<ApiResponse<List<UserModel>>> getUsers(
        @RequestParam(required = false) Boolean state,
        @RequestParam(required = false) String role) {

        List<UserModel> users = userService.getUsers(state, role);

        if (users.isEmpty()) {
            ApiResponse<List<UserModel>> response = new ApiResponse<>("404", "No content found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        ApiResponse<List<UserModel>> response = new ApiResponse<>("200", users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = Constants.DOC_UPD)
    @PutMapping("/updateUser")
    public ResponseEntity<ApiResponse<UserModel>> updateUser(@RequestBody UserModel user) {
        try {
            UserModel updatedUser = userService.updateUser(user);
            ApiResponse<UserModel> response = new ApiResponse<>("200", updatedUser);
            return new ResponseEntity<>(response, HttpStatus.OK); 
        } catch (IllegalArgumentException e) {
            ApiResponse<UserModel> response = new ApiResponse<>("400", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
}
