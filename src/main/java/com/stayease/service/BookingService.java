package com.stayease.service;

import com.stayease.exceptions.NotFoundException;
import com.stayease.model.Booking;
import com.stayease.model.Hotel;
import com.stayease.model.User;
import com.stayease.repository.BookingRepository;
import com.stayease.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    final private BookingRepository bookingRepository;

    final private HotelRepository hotelRepository;

    public Booking bookRoom(User user, String hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(
                () -> new NotFoundException("Hotel not found")
        );
        if (hotel.getAvailableRooms() > 0) {
            hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
            hotelRepository.save(hotel);
            Booking booking = new Booking();
            booking.setUser(user);
            booking.setHotel(hotel);
            booking.setBookingDate(new java.util.Date());
            Booking bookingResult = bookingRepository.save(booking);
            bookingResult.getUser().setPassword(null);
            return bookingResult;
        }
        throw new NotFoundException("No rooms available");
    }

    public void cancelBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new NotFoundException("Booking not found")
        );
        Hotel hotel = booking.getHotel();
        hotel.setAvailableRooms(hotel.getAvailableRooms() + 1);
        hotelRepository.save(hotel);
        bookingRepository.deleteById(bookingId);
    }
}
