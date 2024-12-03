package org.example.service;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User login(@RequestParam String uid, @RequestParam String password){
        User user = userRepository.findByUid(uid);
        noExistId(user);
        faultPassword(user, password);

        return user;
    }

    private void noExistId(User user){
        if(user == null){
            throw new IllegalArgumentException("로그인 실패: 사용자 id가 존재하지 않습니다.");
        }
    }

    private void faultPassword(User user, String password){
        if(!user.getPassword().equals(password)){
            throw new IllegalArgumentException("잘못된 패스워드입니다.");
        }
    }

}
