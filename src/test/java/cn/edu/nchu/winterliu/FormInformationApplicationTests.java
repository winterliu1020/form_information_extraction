package cn.edu.nchu.winterliu;

import cn.edu.nchu.service.ModelService;
import com.github.pagehelper.PageHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FormInformationApplicationTests {
    @Autowired
    ModelService modelService;

    @Test
    void contextLoads() {
    }

    @Test
    public void getAllModelByPage() {
        PageHelper.startPage(1,2);
        System.out.println(modelService.findModelByUserID(1));
    }

}
