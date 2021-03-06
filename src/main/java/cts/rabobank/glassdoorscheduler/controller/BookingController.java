package cts.rabobank.glassdoorscheduler.controller;

import cts.rabobank.glassdoorscheduler.entity.*;
import cts.rabobank.glassdoorscheduler.service.RoomInfoService;
import cts.rabobank.glassdoorscheduler.util.BookingValidator;
import cts.rabobank.glassdoorscheduler.util.CustomMessage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import cts.rabobank.glassdoorscheduler.service.BookingService;
import cts.rabobank.glassdoorscheduler.service.UserInfoService;
import javax.validation.Valid;

@RestController
@RequestMapping("/meetingroomtesting")
public class BookingController extends BookingValidator {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private BookingValidator bookingValidator;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	RoomInfoService roomInfoService;

	@Autowired
	BookingInfo bookingInfo;

	@PostMapping(value = "/canbook")
	public ResponseEntity<CustomMessage> canBooRoom(@RequestBody BookingInfo bookingInfo) {
//		boolean canBook = bookingService.canBookingAllowed(bookingInfo);
//		if (canBook) {
//			return new ResponseEntity<>(new CustomMessage(HttpStatus.OK.value(),
//					"Meeting Room Available"),
//					HttpStatus.OK);
//		}
		return new ResponseEntity<>(new CustomMessage(HttpStatus.CONFLICT.value(),
				"Meeting Room NOT Available"),
				HttpStatus.CONFLICT);
	}

//	@PostMapping(value = "/book", consumes = "application/json", produces = "application/json")
//	public ResponseEntity<CustomMessage> bookRoom(@Valid @RequestBody BookingInfo bookingInfo, Errors errors) {
//
//		bookingValidator.chkBookingRoomInputField(bookingInfo, errors);
//
//		Room room = roomInfoService.findByRoomId((long) bookingInfo.getRoomId());
//		UserInfo userInfo = userInfoService.findUserById((long) bookingInfo.getUsrEmpId());
//
//		Booking booking = new Booking();
//		booking.setBookingDate(bookingInfo.getBookingDate());
//		booking.setBookingStartTime(bookingInfo.getBookingStartTime());
//		booking.setBookingEndTime(bookingInfo.getBookingEndTime());
//		booking.setRoomInfo(room);
//		booking.setPurpose(bookingInfo.getPurpose());
//		booking.setUserInfo(userInfo);
//		bookingService.bookRoom(booking);
//		return new ResponseEntity<>(new CustomMessage(HttpStatus.OK.value(), "Meeting room booked successfully"),
//				HttpStatus.OK);
//	}

	@GetMapping(value = "/cancel/{meetingRoomId}")
	public ResponseEntity<CustomMessage> cancelMeetingRoom(@PathVariable Long meetingRoomId) {
		bookingService.cancelMeetingRoom(meetingRoomId);
		return new ResponseEntity<>(new CustomMessage(HttpStatus.OK.value(), "Meeting room cancelled successfully"),
				HttpStatus.OK);
	}

	@PostMapping(value = "/search")
	public ResponseEntity<?> searchRooms(@RequestBody Searching searchParam) {

		List<SearchResponse> rooms = bookingService.searchMeetingRooms(searchParam); 

		if (rooms.isEmpty()) {
			return new ResponseEntity<CustomMessage>(new CustomMessage(HttpStatus.OK.value(), "No GlassRoom found"),
					HttpStatus.OK);
		}
		return new ResponseEntity<List<SearchResponse>>(rooms, HttpStatus.OK);
	}
}
