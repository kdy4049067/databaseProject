package org.example.User.service;

import org.example.User.repository.UserRepository;
import org.example.User.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean login(String uid, String password){
        User user = userRepository.findByUid(uid);
        noExistId(user);
        faultPassword(user, password);

        return true;
    }

    public String isManager(String uid) {
        User user = userRepository.findByUid(uid);
        if (user == null || user.getRole() == null) {
            throw new IllegalArgumentException("사용자 정보가 잘못되었습니다.");
        }
        if (user.getRole().equals("manager"))
            return "manager";
        return "student";
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
