package sj.project.eatgo.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sj.project.eatgo.domain.User;
import sj.project.eatgo.domain.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotExistedException(email));

        // 비밀번호 비교 (유저가 입력한 비밀번호, DB에 저장된 비밀번호)
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw  new PasswordWrongException();
        }

        return user;
    }

}
