package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.User;
import generator.service.UserService;
import com.leikooo.yubi.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author liang
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-11-06 18:40:34
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




