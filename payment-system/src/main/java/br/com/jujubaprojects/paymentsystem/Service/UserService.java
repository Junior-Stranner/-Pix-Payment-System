package br.com.jujubaprojects.paymentsystem.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.jujubaprojects.paymentsystem.Entity.User;
import br.com.jujubaprojects.paymentsystem.Repository.UserRepository;
import br.com.jujubaprojects.paymentsystem.Utils.RandomString;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user){
        List<User> users = this.userRepository.findAll();
        boolean emailExists = users.stream().anyMatch(eExists -> eExists.getEmail().equals(user.getEmail()));
        if(userRepository.findByEmail(user.getEmail()) != null || emailExists){
            throw new RuntimeException("email already exists");
        }else{
            String encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);

            String randomeCode = RandomString.generateRandomString(64);
            user.setVerificationCode(randomeCode);
            user.setEnable(false);

            return  this.userRepository.save(user);

        }
        
    }
}
