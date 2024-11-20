package api_user.recoverPass.service;

import api_user.recoverPass.exception.InvalidTokenException;
import api_user.recoverPass.exception.ResetTokenAlreadyUsedException;
import api_user.recoverPass.exception.UserNotFoundException;
import api_user.recoverPass.model.PasswordResetToken;
import api_user.recoverPass.repository.PasswordResetTokenRepository;
import api_user.user.model.UserModel;
import api_user.user.repository.UserRepositori;
import api_user.util.Constants;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordRecoveryService {

    private final UserRepositori userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordRecoveryService(UserRepositori userRepository, PasswordResetTokenRepository passwordResetTokenRepository,
                                   JavaMailSender mailSender, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

public void sendRecoveryEmail(String email) throws MessagingException {
    Optional<UserModel> userOpt = userRepository.findByEmail(email);
    if (userOpt.isPresent()) {
        UserModel user = userOpt.get();
    
        Optional<PasswordResetToken> existingTokenOpt = passwordResetTokenRepository.findByUser(user);
        if (existingTokenOpt.isPresent()) {
            PasswordResetToken existingToken = existingTokenOpt.get();

            if (!existingToken.isUsed() && existingToken.getExpiryDate().isAfter(LocalDateTime.now())) {
                existingToken.setUsed(true); 
                passwordResetTokenRepository.save(existingToken);
            } else {
                passwordResetTokenRepository.delete(existingToken); 
            }
        }
        String token = generateToken(user); 
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1); 
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(expiryDate);
        passwordResetToken.setUsed(false); 
        
        try {
            passwordResetTokenRepository.save(passwordResetToken); 
        } catch (DataIntegrityViolationException e) {
            throw new InvalidTokenException(Constants.TK_NOT);
        }
        String resetLink = "http://localhost:4200/passs?token=" + token;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Recuperación de Contraseña");
            helper.setText(Constants.NEXT_PASS + resetLink, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new MessagingException(Constants.ERR_EMAIL + e.getMessage(), e);
        }
    } else {
        throw new UserNotFoundException(Constants.NOT_EMAIL);
    }
}

    public void resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> tokenOpt = passwordResetTokenRepository.findByToken(token);
    
        if (tokenOpt.isEmpty()) {
            throw new IllegalArgumentException("Token inválido");
        }

        PasswordResetToken resetToken = tokenOpt.get();
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("El token ha expirado.");
        }else if (resetToken.isUsed()) {
            throw new ResetTokenAlreadyUsedException("Este enlace ya ha sido utilizado.");
        }
        UserModel user = resetToken.getUser();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);
        userRepository.save(user);
    }

    private String generateToken(UserModel user) {
        return user.getUserId() + "_" + System.currentTimeMillis();
    }
}
