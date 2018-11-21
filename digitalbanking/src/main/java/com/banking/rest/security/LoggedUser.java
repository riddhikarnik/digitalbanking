package com.banking.rest.security;

import static java.util.Objects.requireNonNull;

import java.util.EnumSet;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.banking.entity.User;

public class LoggedUser extends org.springframework.security.core.userdetails.User {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7461791360465933357L;
	private final User user;
    public final static Integer MAIN_ADMIN_ID = 1;

    public LoggedUser(User user){
        super(user.getLoginId(), user.getPassword(), user.isEnabled(), true, true, true, EnumSet.of(user.getRole()));
        this.user = user;
    }

    public static LoggedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object user = auth.getPrincipal();
        return (user instanceof LoggedUser) ? (LoggedUser) user : null;
    }

    public static LoggedUser get() {
        LoggedUser user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }

    public static int id() {
        return get().user.getId();
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return user.toString();
    }
}
