package bo_adm;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
		"classpath:/skp/bo/adm/spring/context-properties.xml",
		"classpath:/skp/bo/adm/spring/context-datasource.xml",
		"classpath:/skp/bo/adm/spring/context-mybatis.xml",
		"classpath:/skp/bo/adm/spring/context-root.xml",
		"classpath:/skp/bo/adm/spring/web/dispatcher-servlet.xml"
})
public class AbstractJUnit {
	
	protected final ObjectMapper mapper = new ObjectMapper();
	
	protected void print(Object value){
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
