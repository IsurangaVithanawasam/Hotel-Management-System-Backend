package com.isurangavithanawasam.hotelmanagementsystem.service.interfac;

import com.isurangavithanawasam.hotelmanagementsystem.dto.LoginRequest;
import com.isurangavithanawasam.hotelmanagementsystem.dto.Response;
import com.isurangavithanawasam.hotelmanagementsystem.entity.User;

public interface IUserService {

    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);

}
