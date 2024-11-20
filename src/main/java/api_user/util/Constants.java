package api_user.util;

public class Constants {
    public static final String ERR_ = "ID de usuario inválido: ";
    public static final String ERR_TK = "Error de token de actualice: ";
    public static final String TK_EXP = "{\"error\": \"Token expirado\", \"details\": \"El token de autenticación ha expirado. Por favor, inicie sesión nuevamente.\"}";
    public static final String TK_ERR = "{\"error\": \"Error de autenticación\"";
    public static final String JWT_SECRET_KEY = "TExBVkVfTVVZX1NFQ1JFVEzE3Zmxu7BSGSJx72BSBXM";
    public static final long JWT_TIME_VALIDITY = 1000 * 60  * 15;
    public static final long JWT_TIME_REFRESH_VALIDATE = 1000 * 60  * 60 * 24;
    public static final String TK_NOT = "El token de recuperación no es válido o ya existe uno activo.";
    public static final String NEXT_PASS = "Para restablecer su contraseña, haga clic en el siguiente enlace: ";
    public static final String ERR_EMAIL = "Error al enviar el correo de recuperación: " ;
    public static final String NOT_EMAIL = "No se encontró el correo electrónico registrado.";
    public static final String DOC_REG = "Permite el registro de usuarios. Debes tener rol Administrador";
    public static final String DOC_GET = "Permite la consulta de todos los usuarios";
    public static final String DOC_UPD = "Permite actualizar datos del un usuario";
    public static final String DOC_LOGIN = "Inicia session y genera token";
    public static final String DOC_TOK = "Mantiene un token vigente ";
    public static final String DOC_RECOVER = "Hace el envio de correo para recuperar la contraseña";
    public static final String DOC_PASS = "Hace el cambio de la contraseña";
}
