package com.teamupbe.activity.participant;

public enum ParticipationStatus {
    QUEUED,      // User wants to play
    CONFIRMED,   // User has locked the slot, guaranteed to play
    CANCELLED,   // User cancelled
    REMOVED      // User removed
}