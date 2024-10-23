public class UserValidator {

    public static boolean isValidRole(String role) {
        try {
            Roles.valueOf(role);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
