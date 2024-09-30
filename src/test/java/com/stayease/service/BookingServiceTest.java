package com.stayease.service;

import com.stayease.model.Booking;
import com.stayease.model.Hotel;
import com.stayease.model.User;
import com.stayease.repository.BookingRepository;
import com.stayease.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookingServiceTest {


    @InjectMocks
    BookingService bookingService;


    @Mock
    BookingRepository bookingRepository;

    @Mock
    HotelRepository hotelRepository;

    @Test
    void testBookRoom() {
        User user = User.builder().id("1").email("user@example.com").password("password").firstName("John").lastName("Doe").build();
        Hotel hotel = Hotel.builder().id("1").name("Hotel Sunshine").location("Beachside").description("A beautiful hotel.").availableRooms(10).build();
        Booking booking = Booking.builder().id("1").user(user).hotel(hotel).build();


        when(hotelRepository.findById("1")).thenReturn(Optional.of(hotel));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking createdBooking = bookingService.bookRoom(user, "1");

        assertNotNull(createdBooking);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testCancelBooking() {
        String bookingId = "1";

        User user = User.builder().id("1").email("user@example.com").password("password").firstName("John").lastName("Doe").build();
        Hotel hotel = Hotel.builder().id("1").name("Hotel Sunshine").location("Beachside").description("A beautiful hotel.").availableRooms(10).build();
        Booking booking = Booking.builder().id("1").user(user).hotel(hotel).build();

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        doNothing().when(bookingRepository).deleteById(bookingId);
        bookingService.cancelBooking(bookingId);

        verify(bookingRepository, times(1)).deleteById(bookingId);
    }
}