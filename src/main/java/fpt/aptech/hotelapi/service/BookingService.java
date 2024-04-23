/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.hotelapi.service;

import fpt.aptech.hotelapi.dto.BookingCurrentDto;
import fpt.aptech.hotelapi.dto.BookingDto;
import fpt.aptech.hotelapi.dto.BookingStatusDto;
import fpt.aptech.hotelapi.dto.RoleDto;
import fpt.aptech.hotelapi.dto.YachtDto;
import fpt.aptech.hotelapi.dto.YachtTypeDto;
import fpt.aptech.hotelapi.dto.SearchDto;
import fpt.aptech.hotelapi.dto.UserDto;
import fpt.aptech.hotelapi.models.Booking;
import fpt.aptech.hotelapi.models.BookingCurrent;
import fpt.aptech.hotelapi.models.BookingStatus;
import fpt.aptech.hotelapi.models.Yacht;
import fpt.aptech.hotelapi.models.Users;
import fpt.aptech.hotelapi.repository.BookingCurrentRepository;
import fpt.aptech.hotelapi.repository.BookingRepository;
import fpt.aptech.hotelapi.repository.BookingStatusRepository;
import fpt.aptech.hotelapi.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fpt.aptech.hotelapi.repository.YachtRepository;

/**
 *
 * @author TuanNguyen
 */
@Service
public class BookingService {

    private BookingCurrentRepository _bookingCurrentRepo;
    private BookingStatusRepository _bookingStatusRepo;
    private BookingRepository _bookingRepo;
    private YachtRepository _yachtRepo;
    private UserRepository _userRepo;

    @Autowired
    public BookingService(BookingCurrentRepository _bookingCurrentRepo,
            BookingStatusRepository _bookingStatusRepo,
            BookingRepository _bookingRepo,
            YachtRepository _yachtRepo,
            UserRepository _userRepo) {
        this._bookingCurrentRepo = _bookingCurrentRepo;
        this._bookingStatusRepo = _bookingStatusRepo;
        this._bookingRepo = _bookingRepo;
        this._yachtRepo = _yachtRepo;
        this._userRepo = _userRepo;
    }

    private Booking mapToModel(BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setBooking_from(bookingDto.getBooking_from());
        booking.setBooking_to(bookingDto.getBooking_to());

        int total_day = bookingDto.getBooking_to().getDayOfMonth() - bookingDto.getBooking_from().getDayOfMonth();
        booking.setTotal_day(total_day);

        Yacht yachtInfo = _yachtRepo.findById(bookingDto.getYacht_id()).orElse(null);
        double total_price = total_day * yachtInfo.getYacht_price();
        booking.setTotal_price(total_price);

        booking.setNumber_of_member(bookingDto.getNumber_of_member());

        booking.setIs_active(bookingDto.getIs_active());

        BookingCurrent bookingCurrent = _bookingCurrentRepo.findById(bookingDto.getBooking_current_id()).orElse(null);
        booking.setBooking_current_id(bookingCurrent);

        Users customerInfo = _userRepo.findById(bookingDto.getCustomer_id()).orElse(null);
        booking.setCustomer_id(customerInfo);

        booking.setYacht_id(yachtInfo);

        return booking;
    }

    private BookingDto mapToDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();

        bookingDto.setId(booking.getId());
        bookingDto.setBooking_from(booking.getBooking_from());
        bookingDto.setBooking_to(booking.getBooking_to());
        bookingDto.setTotal_day(booking.getTotal_day());
        bookingDto.setTotal_price(booking.getTotal_price());
        bookingDto.setNumber_of_member(booking.getNumber_of_member());
        bookingDto.setIs_active(booking.getIs_active());

        bookingDto.setBooking_current_id(booking.getBooking_current_id().getId());
        bookingDto.setBooking_current_info(new BookingCurrentDto(booking.getBooking_current_id().getId(), booking.getBooking_current_id().getBooking_current_name()));

        bookingDto.setCustomer_id(booking.getCustomer_id().getId());
        UserDto userDto = new UserDto();
        userDto.setId(booking.getCustomer_id().getId());
        userDto.setUsername(booking.getCustomer_id().getUsername());
        userDto.setPassword(booking.getCustomer_id().getPassword());
        userDto.setEmail(booking.getCustomer_id().getEmail());
        userDto.setAddress(booking.getCustomer_id().getAddress());
        userDto.setPhone(booking.getCustomer_id().getPhone());
        userDto.setRole_id(booking.getCustomer_id().getRole_id().getId());
        userDto.setRoleInfo(new RoleDto(booking.getCustomer_id().getRole_id().getId(), booking.getCustomer_id().getRole_id().getRoleName()));
        bookingDto.setCustomer_info(userDto);

        bookingDto.setYacht_id(booking.getYacht_id().getId());
        YachtDto yachtDto = new YachtDto();
        yachtDto.setId(booking.getYacht_id().getId());
        yachtDto.setYacht_no(booking.getYacht_id().getYacht_no());
        yachtDto.setYacht_price(booking.getYacht_id().getYacht_price());
        yachtDto.setYacht_image(booking.getYacht_id().getYacht_image());
        yachtDto.setYacht_capacity(booking.getYacht_id().getYacht_capacity());
        yachtDto.setYacht_description(booking.getYacht_id().getYacht_description());
        yachtDto.setIs_active(booking.getYacht_id().getIs_active());

        yachtDto.setBooking_status_id(booking.getYacht_id().getBooking_status_id().getId());
        yachtDto.setBooking_status_info(new BookingStatusDto(booking.getYacht_id().getBooking_status_id().getId(), booking.getYacht_id().getBooking_status_id().getBooking_status_name()));

        yachtDto.setYacht_type_id(booking.getYacht_id().getYacht_type_id().getId());
        yachtDto.setYacht_type_info(new YachtTypeDto(booking.getYacht_id().getYacht_type_id().getId(), booking.getYacht_id().getYacht_type_id().getYacht_type_name()));

        bookingDto.setYacht_info(yachtDto);

        return bookingDto;
    }

    private YachtDto mapToYachtDto(Yacht yacht) {
        YachtDto yachtDto = new YachtDto();
        yachtDto.setId(yacht.getId());
        yachtDto.setYacht_no(yacht.getYacht_no());
        yachtDto.setYacht_price(yacht.getYacht_price());
        yachtDto.setYacht_image(yacht.getYacht_image());
        yachtDto.setYacht_capacity(yacht.getYacht_capacity());
        yachtDto.setYacht_description(yacht.getYacht_description());
        yachtDto.setIs_active(yacht.getIs_active());

        BookingStatusDto bookingStatusDto = new BookingStatusDto();
        bookingStatusDto.setId(yacht.getBooking_status_id().getId());
        bookingStatusDto.setBooking_status_name(yacht.getBooking_status_id().getBooking_status_name());

        yachtDto.setBooking_status_id(bookingStatusDto.getId());
        yachtDto.setBooking_status_info(bookingStatusDto);

        YachtTypeDto yachtTypeDto = new YachtTypeDto();
        yachtTypeDto.setId(yacht.getYacht_type_id().getId());
        yachtTypeDto.setYacht_type_name(yacht.getYacht_type_id().getYacht_type_name());

        yachtDto.setYacht_type_id(yachtTypeDto.getId());
        yachtDto.setYacht_type_info(yachtTypeDto);

        return yachtDto;
    }

    public List<BookingDto> allBooking() {
        return _bookingRepo.findAll().stream()
                .map(mapper -> mapToDto(mapper)).collect(Collectors.toList());
    }
    
    public BookingDto findById(int id) {
        return _bookingRepo.findById(id).map(mapper -> mapToDto(mapper)).orElse(null);
    }
    
    public BookingDto findTheLatestBookingOfAYacht(int yachtId) {
        List<BookingDto> allBookingOfAYacht = _bookingRepo.findAll()
                .stream()
                .filter(b -> b.getYacht_id().getId() == yachtId)
                .map(mapper -> mapToDto(mapper))
                .toList();
        
        BookingDto latestBooking = Collections.max(allBookingOfAYacht, Comparator.comparing(BookingDto::getBooking_to));
        
        return latestBooking;
    }
    
    public List<BookingDto> allBookingByCustomer(int customerId) {
        return _bookingRepo.findAll().stream()
                .filter(b -> b.getCustomer_id().getId() == customerId)
                .map(mapper -> mapToDto(mapper))
                .toList();
    }

    public BookingDto createNewBooking(BookingDto newBookingDto) {
        BookingStatus bookingStatusReserved = _bookingStatusRepo.findById(2).orElse(null);
        Yacht yachtUpdate = _yachtRepo.findById(newBookingDto.getYacht_id()).orElse(null);
        yachtUpdate.setBooking_status_id(bookingStatusReserved);
        _yachtRepo.save(yachtUpdate);

        newBookingDto.setBooking_current_id(1);
        Booking newBooking = mapToModel(newBookingDto);

        Booking response = _bookingRepo.save(newBooking);

        return mapToDto(response);
    }

    public BookingDto confirmBookingDetailByCustomer(BookingDto newBookingDto) {
        List<BookingDto> findingBooking = _bookingRepo.findAll()
                .stream()
                .filter(b -> (b.getYacht_id().getId() == newBookingDto.getYacht_id())
                && ((newBookingDto.getBooking_from().isEqual(b.getBooking_from()))
                || (newBookingDto.getBooking_from().isEqual(b.getBooking_to()))
                || (newBookingDto.getBooking_to().isEqual(b.getBooking_from()))
                || (newBookingDto.getBooking_to().isEqual(b.getBooking_to()))
                || (newBookingDto.getBooking_from().isBefore(b.getBooking_from()))
                || (newBookingDto.getBooking_from().isAfter(b.getBooking_from()) && newBookingDto.getBooking_from().isBefore(b.getBooking_to()))
                || (newBookingDto.getBooking_to().isBefore(b.getBooking_from()))
                || (newBookingDto.getBooking_to().isAfter(b.getBooking_from()) && newBookingDto.getBooking_to().isBefore(b.getBooking_to())))
                )
                .map(mapper -> mapToDto(mapper)).toList();
        if (findingBooking.isEmpty() == false) {
            return null;
        } 
        else {
            Booking bookingInfo = new Booking();
            bookingInfo.setBooking_from(newBookingDto.getBooking_from());
            bookingInfo.setBooking_to(newBookingDto.getBooking_to());
            bookingInfo.setIs_active(true);
            bookingInfo.setNumber_of_member(newBookingDto.getNumber_of_member());
            int total_day = newBookingDto.getBooking_to().getDayOfMonth() - newBookingDto.getBooking_from().getDayOfMonth();
            bookingInfo.setTotal_day(total_day);
            Yacht yachtInfo = _yachtRepo.findById(newBookingDto.getYacht_id()).orElse(null);
            bookingInfo.setYacht_id(yachtInfo);
            Users customerInfo = _userRepo.findById(newBookingDto.getCustomer_id()).orElse(null);
            bookingInfo.setCustomer_id(customerInfo);
            double total_price = total_day * yachtInfo.getYacht_price();
            bookingInfo.setTotal_price(total_price);
            bookingInfo.setBooking_current_id(_bookingCurrentRepo.findById(1).orElse(null));
            
            return mapToDto(bookingInfo);
        }
    }
    
    public BookingDto createNewBookingByCustomer(BookingDto newBookingDto) {
        BookingStatus bookingStatusReserved = _bookingStatusRepo.findById(2).orElse(null);
        Yacht yachtUpdate = _yachtRepo.findById(newBookingDto.getYacht_id()).orElse(null);
        yachtUpdate.setBooking_status_id(bookingStatusReserved);
        _yachtRepo.save(yachtUpdate);

        newBookingDto.setBooking_current_id(1);
        Booking newBooking = mapToModel(newBookingDto);

        Booking response = _bookingRepo.save(newBooking);

        return mapToDto(response);
    }

    public BookingDto confirmBooking(int bookingId) {
        Booking bookingInfoToConfirm = _bookingRepo.findById(bookingId).orElse(null);
        BookingCurrent confirm = _bookingCurrentRepo.findAll()
                .stream().filter(b -> b.getBooking_current_name().equals("CONFIRMED"))
                .findFirst().orElse(null);
        bookingInfoToConfirm.setBooking_current_id(confirm);

        Booking response = _bookingRepo.save(bookingInfoToConfirm);

        return mapToDto(response);
    }

    public BookingDto checkInBooking(int bookingId) {
        Booking bookingInfoToOperational = _bookingRepo.findById(bookingId).orElse(null);
        BookingCurrent operational = _bookingCurrentRepo.findAll()
                .stream().filter(b -> b.getBooking_current_name().equals("OPERATIONAL"))
                .findFirst().orElse(null);
        bookingInfoToOperational.setBooking_current_id(operational);

        BookingStatus occupiedStatus = _bookingStatusRepo.findAll()
                .stream().filter(b -> b.getBooking_status_name().equals("OCCUPIED"))
                .findFirst().orElse(null);
        Yacht yachtInfo = bookingInfoToOperational.getYacht_id();
        yachtInfo.setBooking_status_id(occupiedStatus);
        _yachtRepo.save(yachtInfo);

        Booking response = _bookingRepo.save(bookingInfoToOperational);

        return mapToDto(response);
    }

    public BookingDto checkOutBooking(int bookingId) {
        Booking bookingInfoToComplete = _bookingRepo.findById(bookingId).orElse(null);
        BookingCurrent completed = _bookingCurrentRepo.findAll()
                .stream().filter(b -> b.getBooking_current_name().equals("COMPLETED"))
                .findFirst().orElse(null);
        bookingInfoToComplete.setBooking_current_id(completed);

        BookingStatus vacancyStatus = _bookingStatusRepo.findAll()
                .stream().filter(b -> b.getBooking_status_name().equals("VACANCY"))
                .findFirst().orElse(null);
        Yacht yachtInfo = bookingInfoToComplete.getYacht_id();
        yachtInfo.setBooking_status_id(vacancyStatus);
        _yachtRepo.save(yachtInfo);

        Booking response = _bookingRepo.save(bookingInfoToComplete);

        return mapToDto(response);
    }

    public BookingDto cancelBooking(int bookingId) {
        Booking bookingInfoToCancel = _bookingRepo.findById(bookingId).orElse(null);
        BookingCurrent canceled = _bookingCurrentRepo.findAll()
                .stream().filter(b -> b.getBooking_current_name().equals("CANCELED"))
                .findFirst().orElse(null);
        bookingInfoToCancel.setBooking_current_id(canceled);
        bookingInfoToCancel.setIs_active(false);

        BookingStatus vacancyStatus = _bookingStatusRepo.findAll()
                .stream().filter(b -> b.getBooking_status_name().equals("VACANCY"))
                .findFirst().orElse(null);
        Yacht yachtInfo = bookingInfoToCancel.getYacht_id();
        yachtInfo.setBooking_status_id(vacancyStatus);
        _yachtRepo.save(yachtInfo);

        Booking response = _bookingRepo.save(bookingInfoToCancel);

        return mapToDto(response);
    }

    //Search Yacht by Booking
    public List<YachtDto> searchAvailableYachtForBooking(SearchDto searchDto) {
        if (searchDto.getBooking_from() == null && searchDto.getBooking_to() == null) {
            if (searchDto.getYacht_type_id() == 0 && (searchDto.getFrom_price() != 0 && searchDto.getTo_price() != 0)) {
                List<YachtDto> allYachtAvailable = _yachtRepo.findAll()
                        .stream()
                        .filter(r -> r.getYacht_price() >= searchDto.getFrom_price() && r.getYacht_price() <= searchDto.getTo_price())
                        .map(mapper -> mapToYachtDto(mapper))
                        .toList();
                return allYachtAvailable;
            } else if (searchDto.getYacht_type_id() != 0 && (searchDto.getFrom_price() != 0 && searchDto.getTo_price() != 0)) {
                List<YachtDto> allYachtAvailable = _yachtRepo.findAll()
                        .stream()
                        .filter(r -> r.getYacht_price() >= searchDto.getFrom_price() && r.getYacht_price() <= searchDto.getTo_price() && r.getYacht_type_id().getId() == searchDto.getYacht_type_id())
                        .map(mapper -> mapToYachtDto(mapper))
                        .toList();
                return allYachtAvailable;
            } else {
                List<YachtDto> allYachtAvailable = _yachtRepo.findAll()
                        .stream()
                        .filter(r -> r.getYacht_type_id().getId() == searchDto.getYacht_type_id())
                        .map(mapper -> mapToYachtDto(mapper))
                        .toList();
                return allYachtAvailable;
            }
        } else {
            if (searchDto.getYacht_type_id() == 0 && (searchDto.getFrom_price() != 0 && searchDto.getTo_price() != 0)) {
                List<BookingDto> allBookingHaveBeenBooked = _bookingRepo.findAll()
                        .stream()
                        .filter(b
                                -> (searchDto.getBooking_from().isEqual(b.getBooking_from()))
                        || (searchDto.getBooking_from().isEqual(b.getBooking_to()))
                        || (searchDto.getBooking_to().isEqual(b.getBooking_from()))
                        || (searchDto.getBooking_to().isEqual(b.getBooking_to()))
                        || (searchDto.getBooking_from().isAfter(b.getBooking_from()) && searchDto.getBooking_from().isBefore(b.getBooking_to()))
                        || (searchDto.getBooking_to().isAfter(b.getBooking_from()) && searchDto.getBooking_to().isBefore(b.getBooking_to()))
                        || (searchDto.getBooking_from().isBefore(b.getBooking_from()) && searchDto.getBooking_to().isAfter(b.getBooking_to()))
                        )
                        .map(mapper -> mapToDto(mapper))
                        .toList();

                System.out.println(allBookingHaveBeenBooked);

                List<YachtDto> allYachtBooked = new ArrayList<>();
                allBookingHaveBeenBooked.stream().forEach(b -> allYachtBooked.add(b.getYacht_info()));
                System.out.println(allYachtBooked);

                List<YachtDto> allYachtAvailable = _yachtRepo.findAll()
                        .stream()
                        .filter(p -> (!allYachtBooked.stream().anyMatch(r -> r.getId() == p.getId())) && (p.getYacht_price() >= searchDto.getFrom_price() && p.getYacht_price() <= searchDto.getTo_price()))
                        .map(mapper -> mapToYachtDto(mapper))
                        .toList();

                return allYachtAvailable;
            } else if (searchDto.getYacht_type_id() != 0 && (searchDto.getFrom_price() != 0 && searchDto.getTo_price() != 0)) {
                List<BookingDto> allBookingHaveBeenBooked = _bookingRepo.findAll()
                        .stream()
                        .filter(b
                                -> (searchDto.getBooking_from().isEqual(b.getBooking_from()))
                        || (searchDto.getBooking_from().isEqual(b.getBooking_to()))
                        || (searchDto.getBooking_to().isEqual(b.getBooking_from()))
                        || (searchDto.getBooking_to().isEqual(b.getBooking_to()))
                        || (searchDto.getBooking_from().isAfter(b.getBooking_from()) && searchDto.getBooking_from().isBefore(b.getBooking_to()))
                        || (searchDto.getBooking_to().isAfter(b.getBooking_from()) && searchDto.getBooking_to().isBefore(b.getBooking_to()))
                        || (searchDto.getBooking_from().isBefore(b.getBooking_from()) && searchDto.getBooking_to().isAfter(b.getBooking_to()))
                        )
                        .map(mapper -> mapToDto(mapper))
                        .toList();

                System.out.println(allBookingHaveBeenBooked);

                List<YachtDto> allYachtBooked = new ArrayList<>();
                allBookingHaveBeenBooked.stream().forEach(b -> allYachtBooked.add(b.getYacht_info()));
                System.out.println(allYachtBooked);

                List<YachtDto> allYachtAvailable = _yachtRepo.findAll()
                        .stream()
                        .filter(p -> (!allYachtBooked.stream().anyMatch(r -> r.getId() == p.getId())) && (p.getYacht_price() >= searchDto.getFrom_price() && p.getYacht_price() <= searchDto.getTo_price()) && (p.getYacht_type_id().getId() == searchDto.getYacht_type_id()))
                        .map(mapper -> mapToYachtDto(mapper))
                        .toList();

                return allYachtAvailable;
            } else if (searchDto.getYacht_type_id() == 0 && searchDto.getFrom_price() == 0 && searchDto.getTo_price() == 0) {
                List<BookingDto> allBookingHaveBeenBooked = _bookingRepo.findAll()
                        .stream()
                        .filter(b
                                -> (searchDto.getBooking_from().isEqual(b.getBooking_from()))
                        || (searchDto.getBooking_from().isEqual(b.getBooking_to()))
                        || (searchDto.getBooking_to().isEqual(b.getBooking_from()))
                        || (searchDto.getBooking_to().isEqual(b.getBooking_to()))
                        || (searchDto.getBooking_from().isAfter(b.getBooking_from()) && searchDto.getBooking_from().isBefore(b.getBooking_to()))
                        || (searchDto.getBooking_to().isAfter(b.getBooking_from()) && searchDto.getBooking_to().isBefore(b.getBooking_to()))
                        || (searchDto.getBooking_from().isBefore(b.getBooking_from()) && searchDto.getBooking_to().isAfter(b.getBooking_to()))
                        )
                        .map(mapper -> mapToDto(mapper))
                        .toList();

                System.out.println(allBookingHaveBeenBooked);

                List<YachtDto> allYachtBooked = new ArrayList<>();
                allBookingHaveBeenBooked.stream().forEach(b -> allYachtBooked.add(b.getYacht_info()));
                System.out.println(allYachtBooked);

                List<YachtDto> allYachtAvailable = _yachtRepo.findAll()
                        .stream()
                        .filter(p -> !allYachtBooked.stream().anyMatch(r -> r.getId() == p.getId()))
                        .map(mapper -> mapToYachtDto(mapper))
                        .toList();

                return allYachtAvailable;
            } else {
                List<BookingDto> allBookingHaveBeenBooked = _bookingRepo.findAll()
                        .stream()
                        .filter(b
                                -> (searchDto.getBooking_from().isEqual(b.getBooking_from()))
                        || (searchDto.getBooking_from().isEqual(b.getBooking_to()))
                        || (searchDto.getBooking_to().isEqual(b.getBooking_from()))
                        || (searchDto.getBooking_to().isEqual(b.getBooking_to()))
                        || (searchDto.getBooking_from().isAfter(b.getBooking_from()) && searchDto.getBooking_from().isBefore(b.getBooking_to()))
                        || (searchDto.getBooking_to().isAfter(b.getBooking_from()) && searchDto.getBooking_to().isBefore(b.getBooking_to()))
                        || (searchDto.getBooking_from().isBefore(b.getBooking_from()) && searchDto.getBooking_to().isAfter(b.getBooking_to()))
                        )
                        .map(mapper -> mapToDto(mapper))
                        .toList();

                System.out.println(allBookingHaveBeenBooked);

                List<YachtDto> allYachtBooked = new ArrayList<>();
                allBookingHaveBeenBooked.stream().forEach(b -> allYachtBooked.add(b.getYacht_info()));
                System.out.println(allYachtBooked);

                List<YachtDto> allYachtAvailable = _yachtRepo.findAll()
                        .stream()
                        .filter(p -> (!allYachtBooked.stream().anyMatch(r -> r.getId() == p.getId())) && (p.getYacht_type_id().getId() == searchDto.getYacht_type_id()))
                        .map(mapper -> mapToYachtDto(mapper))
                        .toList();

                return allYachtAvailable;
            }
        }
    }
}
