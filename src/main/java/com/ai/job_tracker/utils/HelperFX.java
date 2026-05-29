package com.ai.job_tracker.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ai.job_tracker.model.User;
import com.ai.job_tracker.security.userdetails.CustomUserDetails;

public class HelperFX {
    public static User getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
        return userDetails.getUser();
    }

    throw new RuntimeException("Unauthenticated request");
}
}
