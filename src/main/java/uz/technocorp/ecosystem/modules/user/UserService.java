package uz.technocorp.ecosystem.modules.user;

import java.util.Map;

public interface UserService {
    Map<String, Object> getMe(User user);
}
