package com.stayease.service;

import com.stayease.exceptions.NotFoundException;
import com.stayease.model.Hotel;
import com.stayease.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    final private HotelRepository hotelRepository;

    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public void delete(String id) {
        hotelRepository.deleteById(id);
    }

    public Hotel update(Hotel hotel, String id) {
        findById(id);
        hotel.setId(id);
        return hotelRepository.save(hotel);
    }

    public Hotel findById(String id) {
        return hotelRepository.findById(id).orElseThrow(()-> new NotFoundException(
                "Hotel not found"
        ));
    }

}
