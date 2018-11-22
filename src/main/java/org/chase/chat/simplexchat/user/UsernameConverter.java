package org.chase.chat.simplexchat.user;

public class UsernameConverter {
    public static String convertUsername(final String userName) {
        return userName.toLowerCase().trim();
    }
}
