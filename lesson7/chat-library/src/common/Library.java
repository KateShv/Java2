package common;

public class Library {
    /*
    /auth_request±login±password
    /auth_accept±nickname
    /auth_error
    /broadcast±msg
    /msg_format_error±msg
    * */
    public static final String DELIMITER = "±";
    public static final String AUTH_REQUEST = "Запрос авторизации от пользователя: ";
    public static final String AUTH_ACCEPT = "Добро пожаловать: ";                                           //"/auth_accept";
    public static final String AUTH_DENIED = "В авторизации отказано, введен неверный логин или пароль.";    //"/auth_denied";
    public static final String MSG_FORMAT_ERROR = "Введены некорректные символы: ";         //"/msg_format_error";
    // если мы вдруг не поняли, что за сообщение и не смогли разобрать
    public static final String TYPE_BROADCAST = "К нам присоединился пользователь: ";           //"/bcast";
    // то есть сообщение, которое будет посылаться всем

    public static String getAuthRequest(String login, String password) {
        return AUTH_REQUEST + DELIMITER + login + DELIMITER + password;
    }

    public static String getAuthAccept(String nickname) {
        return AUTH_ACCEPT + nickname;
    }

    public static String getAuthDenied() {
        return AUTH_DENIED;
    }

    public static String getMsgFormatError(String message) {
        return MSG_FORMAT_ERROR + message;
    }

    public static String getTypeBroadcast(String src, String message) {
        return src + " " + TYPE_BROADCAST + message;
    }

}
