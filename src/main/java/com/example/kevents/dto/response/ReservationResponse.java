package com.example.kevents.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {

    private String eventTitle;
    private String attendeeUsername;
    private int seats;
    private String status;
    private String createdAt;

}
