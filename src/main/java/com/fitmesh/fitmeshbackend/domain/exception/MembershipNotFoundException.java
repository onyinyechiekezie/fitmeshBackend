package com.fitmesh.fitmeshbackend.domain.exception;

public class MembershipNotFoundException extends DomainException {
    public MembershipNotFoundException(String membershipId) {
        super("Membership not found: " + membershipId);
    }
}
