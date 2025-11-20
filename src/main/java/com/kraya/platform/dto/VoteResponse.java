package com.kraya.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for vote responses.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteResponse {

    private Long voteId;
    private Long debtorId;
    private Long creditorId;
    private Boolean vote;
    private LocalDateTime voteDate;
    private String comment;
    private String message;

    public VoteResponse(String message) {
        this.message = message;
    }

    public VoteResponse(Long voteId, String message) {
        this.voteId = voteId;
        this.message = message;
    }
}
