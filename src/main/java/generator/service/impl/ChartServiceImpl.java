package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Chart;
import generator.service.ChartService;
import com.leikooo.yubi.mapper.ChartMapper;
import org.springframework.stereotype.Service;

/**
* @author liang
* @description 针对表【chart(图表信息表)】的数据库操作Service实现
* @createDate 2023-11-06 18:40:34
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

}




