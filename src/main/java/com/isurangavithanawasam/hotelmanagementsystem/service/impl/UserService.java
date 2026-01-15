package com.isurangavithanawasam.hotelmanagementsystem.service.impl;

import com.isurangavithanawasam.hotelmanagementsystem.dto.LoginRequest;
import com.isurangavithanawasam.hotelmanagementsystem.dto.Response;
import com.isurangavithanawasam.hotelmanagementsystem.dto.UserDTO;
import com.isurangavithanawasam.hotelmanagementsystem.entity.User;
import com.isurangavithanawasam.hotelmanagementsystem.exception.OurException;
import com.isurangavithanawasam.hotelmanagementsystem.repo.UserRepository;
import com.isurangavithanawasam.hotelmanagementsystem.service.interfac.IUserService;
import com.isurangavithanawasam.hotelmanagementsystem.utils.JWTUtils;
import com.isurangavithanawasam.hotelmanagementsystem.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public Response register(User user) {
        Response response = new Response();

        try {
            if (user.getRole() == null || user.getRole().isBlank()) {
                user.setRole("USER");
            }
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new OurException(user.getEmail() + "Already Exists");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);
            response.setStatusCode(200);
            response.setUser(userDTO);

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());

        }
        catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during user registration" + e.getMessage());

        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new OurException("User not found"));

            var token = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 Days");
            response.setMessage("Successfull");

        }catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        }catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error occurred during user login" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();

        try {
            List<User> userList = userRepository.findAll();
            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);
            response.setStatusCode(200);
            response.setMessage("Successfull");
            response.setUserList(userDTOList);

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting all users" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserBookingHistory(String userId) {
        Response response = new Response();

        try {
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new OurException("User not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);
            response.setStatusCode(200);
            response.setMessage("Successfull");
            response.setUser(userDTO);


        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all users" + e.getMessage());

        }
        return response;
    }

    @Override
    public Response deleteUser(String userId) {
        Response response = new Response();

        try {
            userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new OurException("User not found"));
            userRepository.deleteById(Long.valueOf(userId));
            response.setStatusCode(200);
            response.setMessage("Successfull");


        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all users" + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getUserById(String email) {
        Response response = new Response();

        try {
            User user = userRepository.findByEmail(email).orElseThrow(()-> new OurException("User not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Successfull");
            response.setUser(userDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all users" + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();

        try {
            User user = userRepository.findByEmail(email).orElseThrow(()-> new OurException("User not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Successfull");
            response.setUser(userDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all users" + e.getMessage());

        }
        return response;
    }
}
