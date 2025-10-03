package com.example.kevents.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
public class ReservationResponse {

    private String eventTitle;
    private String attendeeUsername;
    private int seats;
    private String status;
    private String createdAt;

}
