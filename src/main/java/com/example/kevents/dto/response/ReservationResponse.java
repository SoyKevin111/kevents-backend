package com.example.kevents.dto.response;

import lombok.*;

@Builder
public class ReservationResponse {

    private String eventTitle;
    private String attendeeUsername;
    private int seats;
    private String status;
    private String createdAt;

}
