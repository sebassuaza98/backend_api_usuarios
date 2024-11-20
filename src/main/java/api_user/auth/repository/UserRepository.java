package api_user.auth.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import api_user.recoverPass.repository.IUserRepository;
import api_user.user.model.UserModel;

@Repository
public class UserRepository implements IUserRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public UserModel findByUserId(String userId) {
        String SQL = "SELECT * FROM \"users\" WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new Object[]{userId}, 
                    new BeanPropertyRowMapper<>(UserModel.class));
        } catch (EmptyResultDataAccessException e) {
            // Manejar el caso donde no se encuentre el usuario
            return null;  // O lanzar una excepción personalizada si lo prefieres
        }
    }

}
