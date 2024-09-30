package com.stayease.controller;

import com.stayease.model.Booking;
import com.stayease.model.User;
import com.stayease.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class BookingController {


    final private BookingService bookingService;

    @PostMapping("/{hotelId}/book")
    public ResponseEntity<Booking> bookRoom(@AuthenticationPrincipal User user, @PathVariable String hotelId) {
        Booking booking = bookingService.bookRoom(user, hotelId);
        return ResponseEntity.ok(booking);
    }

    @DeleteMapping("/bookings/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable String bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
