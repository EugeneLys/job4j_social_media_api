package ru.job4j.media.service;

import org.springframework.stereotype.Service;
import ru.job4j.media.model.User;
import ru.job4j.media.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SimpleUserService implements UserService {

    private UserRepository userRepository;

    public SimpleUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public int update(User user) {
        return userRepository.update(user);
    }

    @Override
    public int patch(User user) {
        var currentOptional = userRepository.findById(user.getId());
        if (currentOptional.isEmpty()) {
            return -1;
        }
        var currentUser = currentOptional.get();
        if (user.getEmail() != null) {
            currentUser.setEmail(user.getEmail());
        }
        if (user.getName() != null) {
            currentUser.setName(user.getName());
        }
        if (user.getPassword() != null) {
            currentUser.setPassword(user.getPassword());
        }
        userRepository.save(currentUser);
        return 1;
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
