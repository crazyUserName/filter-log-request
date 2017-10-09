package cn.learncoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.learncoding.filter.LogFilter;
import cn.learncoding.vo.RequestDataVO;
import cn.learncoding.vo.ResultVO;

@SpringBootApplication
@RestController
public class FilterLogRequestApplication {

	@GetMapping("/testGet")
	public Object testGet(RequestDataVO dataVO){
		return ResultVO.successResult(dataVO);
	}

	@PostMapping("/testPostParam")
	public Object testPostParam(RequestDataVO dataVO){
		return ResultVO.successResult(dataVO);
	}

	@PostMapping(value = "/testPostJson", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public Object testPostJson(@RequestBody  RequestDataVO dataVO){
		return ResultVO.successResult(dataVO);
	}

	@Bean
	public FilterRegistrationBean logFilter() {
		final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		final LogFilter logFilter = new LogFilter();
		filterRegistrationBean.setFilter(logFilter);
		return filterRegistrationBean;
	}

	public static void main(String[] args) {
		SpringApplication.run(FilterLogRequestApplication.class, args);
	}
}
