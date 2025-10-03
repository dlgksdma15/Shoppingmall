package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String userId){
        //todo#4-1 회원조회
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isPresent()){ // 값이 있는지
            return userOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public void saveUser(User user) {
        //todo#4-2 회원등록
        //회원 ID 중복 검사
        long count = userRepository.countByUserId(user.getUserId());
        if(count == 0){
            userRepository.save(user);
            log.debug("save: {}",user);
        } else{
            throw new UserAlreadyExistsException("User is already: " + user);
        }
    }

    @Override
    public void updateUser(User user) {
        //todo#4-3 회원수정
        int count = userRepository.countByUserId(user.getUserId());
        if(count == 1){
            userRepository.update(user);
            log.debug("update:{}",user);
        }

    }

    @Override
    public void deleteUser(String userId) {
        //todo#4-4 회원삭제
        int count = userRepository.countByUserId(userId);
        if(count == 1){
            userRepository.deleteByUserId(userId);
            log.debug("delete:{}",userId);
        }

    }

    @Override
    public User doLogin(String userId, String userPassword) {
        //todo#4-5 로그인 구현, userId, userPassword로 일치하는 회원 조회
        Optional<User> userOptional = userRepository.findByUserIdAndUserPassword(userId, userPassword);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("아이디 [" + userId + "]와 비밀번호가 일치하는 회원을 찾을 수 없습니다.");
        }
        User user = userOptional.get();

        userRepository.updateLatestLoginAtByUserId(userId,LocalDateTime.now());
//        userRepository.save(user);
        return user;
    }

}
