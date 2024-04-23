/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.hotelapi.service;

import fpt.aptech.hotelapi.dto.BookingCurrentDto;
import fpt.aptech.hotelapi.dto.BookingDto;
import fpt.aptech.hotelapi.dto.BookingStatusDto;
import fpt.aptech.hotelapi.dto.PaymentDto;
import fpt.aptech.hotelapi.dto.RoleDto;
import fpt.aptech.hotelapi.dto.YachtDto;
import fpt.aptech.hotelapi.dto.YachtTypeDto;
import fpt.aptech.hotelapi.dto.UserDto;
import fpt.aptech.hotelapi.models.Booking;
import fpt.aptech.hotelapi.models.Feedback;
import fpt.aptech.hotelapi.models.Payment;
import fpt.aptech.hotelapi.repository.BookingRepository;
import fpt.aptech.hotelapi.repository.PaymentRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author TuanNguyen
 */
@Service
public class PaymentService {

    private BookingRepository _bookingRepo;
    private PaymentRepository _paymentRepo;
    private JavaMailSender _javaMailSender;

    @Autowired
    public PaymentService(BookingRepository _bookingRepo, PaymentRepository _paymentRepo, JavaMailSender _javamailsender) {
        this._bookingRepo = _bookingRepo;
        this._paymentRepo = _paymentRepo;
        this._javaMailSender = _javamailsender;
    }

    private BookingDto mapToBookingDto(Booking booking) {
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
        YachtDto roomDto = new YachtDto();
        roomDto.setId(booking.getYacht_id().getId());
        roomDto.setYacht_no(booking.getYacht_id().getYacht_no());
        roomDto.setYacht_price(booking.getYacht_id().getYacht_price());
        roomDto.setYacht_image(booking.getYacht_id().getYacht_image());
        roomDto.setYacht_capacity(booking.getYacht_id().getYacht_capacity());
        roomDto.setYacht_description(booking.getYacht_id().getYacht_description());
        roomDto.setIs_active(booking.getYacht_id().getIs_active());

        roomDto.setBooking_status_id(booking.getYacht_id().getBooking_status_id().getId());
        roomDto.setBooking_status_info(new BookingStatusDto(booking.getYacht_id().getBooking_status_id().getId(), booking.getYacht_id().getBooking_status_id().getBooking_status_name()));

        roomDto.setYacht_type_id(booking.getYacht_id().getYacht_type_id().getId());
        roomDto.setYacht_type_info(new YachtTypeDto(booking.getYacht_id().getYacht_type_id().getId(), booking.getYacht_id().getYacht_type_id().getYacht_type_name()));

        bookingDto.setYacht_info(roomDto);

        return bookingDto;
    }

    private Payment mapToModel(PaymentDto paymentDto) {
        Booking bookingInfo = _bookingRepo.findById(paymentDto.getBooking_id()).orElse(null);
        Payment payment = new Payment();
        payment.setFinal_amount(bookingInfo.getTotal_price());
        payment.setPayment_type(paymentDto.getPayment_type());
        payment.setBooking(bookingInfo);

        return payment;
    }

    private PaymentDto mapToDto(Payment payment) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(payment.getId());
        paymentDto.setFinal_amount(payment.getFinal_amount());
        paymentDto.setPayment_type(payment.getPayment_type());
        paymentDto.setPayment_status(payment.getPayment_status());
        paymentDto.setIs_active(payment.getIs_active());
        paymentDto.setCreated_at(payment.getCreated_at());
        paymentDto.setBooking_id(payment.getBooking().getId());
        paymentDto.setBooking_info(mapToBookingDto(payment.getBooking()));

        return paymentDto;
    }

    public BookingDto showBookingInfoToPayment(int bookingId) {
        Booking bookingInfo = _bookingRepo.findById(bookingId).orElse(null);
        return mapToBookingDto(bookingInfo);
    }

    public PaymentDto createNewPayment(PaymentDto newPaymentDto) {
        Payment newPayment = mapToModel(newPaymentDto);
        newPayment.setPayment_type("Cash");
        newPayment.setPayment_status("IN_PROGRESS");
        newPayment.setIs_active(true);

        Payment response = _paymentRepo.save(newPayment);
        sendFeedback(response);

        PaymentDto paymentDto = mapToDto(response);

        return paymentDto;
    }

    public void sendFeedback(Payment payment) {
        String str = "Hotel Invoke \n"
                + "Customer Name: " + payment.getBooking().getCustomer_id().getUsername() + "\n"
                + "Room No: " + payment.getBooking().getYacht_id().getYacht_no() + "\n"
                + "Room Price: " + payment.getBooking().getYacht_id().getYacht_price() + "\n"
                + "Booking From: " + payment.getBooking().getBooking_from() + "\n"
                + "Booking To: " + payment.getBooking().getBooking_to() + "\n"
                + "Total Day: " + payment.getBooking().getTotal_day() + "\n"
                + "Payment Type: " + payment.getPayment_type() + "\n" 
                + "Total price: " + payment.getFinal_amount() + "\n";
                
        
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(payment.getBooking().getCustomer_id().getEmail());
        mailMessage.setSubject("Hotel Invoice");
        mailMessage.setText(str);
        mailMessage.setSentDate(new Date());

        _javaMailSender.send(mailMessage);
    }

    public PaymentDto confirmPayment(int paymentId) {
        Payment paymentInfoToConfirm = _paymentRepo.findById(paymentId).orElse(null);

        paymentInfoToConfirm.setPayment_status("COMPLETED");

        Payment response = _paymentRepo.save(paymentInfoToConfirm);

        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(response.getId());
        paymentDto.setFinal_amount(response.getFinal_amount());
        paymentDto.setPayment_type(response.getPayment_type());
        paymentDto.setPayment_status(response.getPayment_status());
        paymentDto.setIs_active(response.getIs_active());
        paymentDto.setCreated_at(response.getCreated_at());
        paymentDto.setBooking_id(response.getBooking().getId());
        paymentDto.setBooking_info(mapToBookingDto(response.getBooking()));

        return paymentDto;
    }

    public List<PaymentDto> allPayment() {
        return _paymentRepo.findAll()
                .stream()
                .map(mapper -> mapToDto(mapper))
                .toList();
    }

    public List<PaymentDto> allPaymentByCustomer(int customerId) {
        return _paymentRepo.findAll()
                .stream()
                .filter(p -> p.getBooking().getCustomer_id().getId() == customerId)
                .map(mapper -> mapToDto(mapper))
                .toList();
    }

}
