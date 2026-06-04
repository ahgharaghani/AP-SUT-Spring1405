package dto;

import model.animal.FarmAnimal;

import java.util.List;

public class RebellionResultDTO {
    public static class RebelCandidateDTO {
        private final int id;
        private final String name;
        private final int governorships;

        public RebelCandidateDTO(FarmAnimal candidate) {
            this.id = candidate.getID();
            this.name = candidate.getName();
            this.governorships = candidate.getGovernorships();
        }

        public int getId()  { return id; }
        public String getName() { return name; }
        public int getGovernorships(){ return governorships; }
    }

    public static class RebelVoteResultDTO {
        private final int id;
        private final String name;
        private final int votes;

        public RebelVoteResultDTO(FarmAnimal candidate, int votes) {
            this.id = candidate.getID();
            this.name = candidate.getName();
            this.votes = votes;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public int getVotes() { return votes; }
    }

    public enum Status {
        NOT_A_WORKER,
        CANDIDATE_NOT_FOUND,
        INVALID_TONG,
        CONDITIONS_NOT_MET,
        ONLY_GOVERNOR_SUGGESTED,
        NO_VALID_CANDIDATE,
        NOT_ENOUGH_SUPPORT,
        SUCCEEDED
    }

    private final Status status;

    private final List<RebelCandidateDTO> candidates;
    private final List<RebelVoteResultDTO> voteResults;
    private final int newGovernorId;
    private final String newGovernorName;
    private final String newPoliticalSystem;

    private RebellionResultDTO(Status status) {
        this.status = status;
        this.candidates = null;
        this.voteResults = null;
        this.newGovernorId = -1;
        this.newGovernorName = null;
        this.newPoliticalSystem = null;
    }

    private RebellionResultDTO(
            List<RebelCandidateDTO>  candidates,
            List<RebelVoteResultDTO> voteResults,
            int newGovernorId,
            String newGovernorName,
            String newPoliticalSystem) {
        this.status = Status.SUCCEEDED;
        this.candidates = candidates;
        this.voteResults = voteResults;
        this.newGovernorId = newGovernorId;
        this.newGovernorName = newGovernorName;
        this.newPoliticalSystem = newPoliticalSystem;
    }

    public static RebellionResultDTO notAWorker() {
        return new RebellionResultDTO(Status.NOT_A_WORKER);
    }

    public static RebellionResultDTO candidateNotFound() {
        return new RebellionResultDTO(Status.CANDIDATE_NOT_FOUND);
    }

    public static RebellionResultDTO invalidTong() {
        return new RebellionResultDTO(Status.INVALID_TONG);
    }

    public static RebellionResultDTO conditionsNotMet() {
        return new RebellionResultDTO(Status.CONDITIONS_NOT_MET);
    }

    public static RebellionResultDTO onlyGovernorSuggested() {
        return new RebellionResultDTO(Status.ONLY_GOVERNOR_SUGGESTED);
    }

    public static RebellionResultDTO noValidCandidate() {
        return new RebellionResultDTO(Status.NO_VALID_CANDIDATE);
    }

    public static RebellionResultDTO notEnoughSupport() {
        return new RebellionResultDTO(Status.NOT_ENOUGH_SUPPORT);
    }

    public static RebellionResultDTO succeeded(
            List<RebelCandidateDTO>  candidates,
            List<RebelVoteResultDTO> voteResults,
            int newGovernorId,
            String newGovernorName,
            String newPoliticalSystem) {
        return new RebellionResultDTO(
                candidates, voteResults,
                newGovernorId, newGovernorName, newPoliticalSystem);
    }

    public boolean isSuccess()  { return status == Status.SUCCEEDED; }
    public boolean isFailure()  { return !isSuccess(); }

    public Status getStatus()                          { return status; }
    public List<RebelCandidateDTO>  getCandidates()    { return candidates; }
    public List<RebelVoteResultDTO> getVoteResults()   { return voteResults; }
    public int    getNewGovernorId()                   { return newGovernorId; }
    public String getNewGovernorName()                 { return newGovernorName; }
    public String getNewPoliticalSystem()              { return newPoliticalSystem; }


}