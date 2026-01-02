package com.isurangavithanawasam.hotelmanagementsystem.service.interfac;

import com.isurangavithanawasam.hotelmanagementsystem.dto.Response;
import com.isurangavithanawasam.hotelmanagementsystem.entity.Booking;

public interface IBookingService {

    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);
}
