package uz.technocorp.ecosystem.modules.user;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public Map<String, Object> getMe(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("fullName",user.getFullName());
        map.put("pin", user.getPin());
        map.put("role", user.getRole());
        return map;
    }
}
