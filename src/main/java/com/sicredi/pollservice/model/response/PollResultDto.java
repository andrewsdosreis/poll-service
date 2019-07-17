package com.sicredi.pollservice.model.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PollResultDto {

    private Integer pollId;
    private String topicName;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime endDate;
    private Integer totalVotes;
    private Integer yesVotes;
    private Integer noVotes;
    private String winningOption;

    public PollResultDto(Integer pollId, String topicName, LocalDateTime startDate, LocalDateTime endDate,
            Integer totalVotes, Integer yesVotes, Integer noVotes, String winningOption) {
        this.pollId = pollId;
        this.topicName = topicName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalVotes = totalVotes;
        this.yesVotes = yesVotes;
        this.noVotes = noVotes;
        this.winningOption = winningOption;
    }

    public Integer getPollId() {
        return pollId;
    }

    public void setPollId(Integer pollId) {
        this.pollId = pollId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Integer totalVotes) {
        this.totalVotes = totalVotes;
    }

    public Integer getYesVotes() {
        return yesVotes;
    }

    public void setYesVotes(Integer yesVotes) {
        this.yesVotes = yesVotes;
    }

    public Integer getNoVotes() {
        return noVotes;
    }

    public void setNoVotes(Integer noVotes) {
        this.noVotes = noVotes;
    }

    public String getWinningOption() {
        return winningOption;
    }

    public void setWinningOption(String winningOption) {
        this.winningOption = winningOption;
    }

    @Override
    public String toString() {
        return "PollResultDto [pollId=" + pollId + ", topicName=" + topicName + ", startDate=" + startDate
                + ", endDate=" + endDate + ", totalVotes=" + totalVotes + ", yesVotes=" + yesVotes + ", noVotes="
                + noVotes + " , winningOption=" + winningOption + "]";
    }

    public static class Builder {

        private Integer pollId;
        private String topicName;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Integer totalVotes;
        private Integer yesVotes;
        private Integer noVotes;
        private String winningOption;

        public Builder() {
        }

        public Builder setPollId(Integer pollId) {
            this.pollId = pollId;
            return this;
        }

        public Builder setTopicName(String topicName) {
            this.topicName = topicName;
            return this;
        }

        public Builder setStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder setEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder setTotalVotes(Integer totalVotes) {
            this.totalVotes = totalVotes;
            return this;
        }

        public Builder setYesVotes(Integer yesVotes) {
            this.yesVotes = yesVotes;
            return this;
        }

        public Builder setNoVotes(Integer noVotes) {
            this.noVotes = noVotes;
            return this;
        }

        public Builder setWinningOption() {
            if (this.yesVotes > this.noVotes) {
                this.winningOption = "YES";
            } else if (this.yesVotes < this.noVotes) {
                this.winningOption = "NO";
            } else {
                this.winningOption = "DRAW";
            }
            return this;
        }

        public PollResultDto build() {
            return new PollResultDto(pollId, topicName, startDate, endDate, totalVotes, yesVotes, noVotes, winningOption);
        }
    }

}