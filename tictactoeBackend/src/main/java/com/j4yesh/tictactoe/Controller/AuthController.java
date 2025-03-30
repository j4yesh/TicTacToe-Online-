package com.j4yesh.tictactoe.Controller;

import com.j4yesh.tictactoe.CustomException.InvalidCredentialsException;
import com.j4yesh.tictactoe.CustomException.UserAlreadyExistsException;
import com.j4yesh.tictactoe.Model.AuthUser;
import com.j4yesh.tictactoe.Service.AuthService;
import com.j4yesh.tictactoe.Service.AuthUserDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name="Auth APIs")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private AuthUserDetailService authUserDetailService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody AuthUser user) {
        try {
            return ResponseEntity.ok(authService.registerUser(user));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthUser authRequest) {
        try {
            return ResponseEntity.ok(authService.loginUser(authRequest.getUsername(), authRequest.getPassword()));
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getSelf(){
        try{
            AuthUser authUser= authUserDetailService.getAuthUser();
            authUser.setPassword(null);
            return ResponseEntity.ok().body(authUser);
        }catch (Exception e){
            return  ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
