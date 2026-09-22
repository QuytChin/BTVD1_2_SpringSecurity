package vn.iotstar.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.iotstar.dto.UserDTO;
import vn.iotstar.entity.User;
import vn.iotstar.mapper.UserMapper;
import vn.iotstar.repository.UserRepository;

/** Cho phep dang nhap bang username HOAC email. */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CustomUserDetailsService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(login, login)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Không tìm thấy username/email: " + login));

        // Su dung MapStruct de chuyen User -> UserDTO theo yeu cau cua bai.
        UserDTO dto = userMapper.toDTO(user);

        return new CustomUserDetails(
                dto.getId(),
                dto.getUsername(),
                dto.getEmail(),
                user.getPassword(),
                dto.getFullName(),
                dto.getImages(),
                dto.getRoleName(),
                dto.isEnabled());
    }
}
