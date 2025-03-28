package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.data.repo.UserRepository;
import denis.f108349.hospital.dto.UserRegistrationRequest;
import denis.f108349.hospital.data.model.User;
import denis.f108349.hospital.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper = new ModelMapper();
    private final UserRepository userRepository;
    
    @Override
    public Mono<User> createUser(UserRegistrationRequest request) {
        User user = this.modelMapper.map(request, User.class);
        return this.userRepository.save(user);
    }
}
