package by.runets.travelagency.util.encoding;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
public class EncodingFilter extends GenericFilterBean {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");

	chain.doFilter(request, response);
  }
}
