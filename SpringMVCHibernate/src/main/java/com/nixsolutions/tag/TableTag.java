package com.nixsolutions.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nixsolutions.model.User;
import com.nixsolutions.model.UserProfileType;

public class TableTag extends SimpleTagSupport {

	private List<User> userList;
	private String tableStyle;
	private static final Logger LOG = LoggerFactory.getLogger(TableTag.class);

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public void setTableStyle(String tableStyle) {
		this.tableStyle = tableStyle;
	}

	@Override
	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext) getJspContext();
		String edit = pageContext.getServletContext().getContextPath() + "/edit-user-";
		String delete = pageContext.getServletContext().getContextPath() + "/delete-user-";
		String onClick = "onclick=\"return confirm('Are you sure?');\"";
		JspWriter out = pageContext.getOut();
		StringBuilder sb = new StringBuilder();
		LOG.info("table style " + tableStyle);
		sb.append("<table class=\"table\">");
		sb.append("<thead class=\"" + tableStyle + "\">");
		sb.append("<tr>");

		sb.append(" <th scope=\"col\">Login</th>");
		sb.append(" <th scope=\"col\">First Name</th>");
		sb.append(" <th scope=\"col\">Last Name</th>");
		sb.append(" <th scope=\"col\">Age</th>");
		sb.append(" <th scope=\"col\">Role</th>");
		sb.append(" <th scope=\"col\">Actions</th>");
		sb.append("  <sec:authorize access=\"hasRole('ADMIN')\">\r\n"
				+ "                            <th width=\"100\"></th>\r\n"
				+ "                        </sec:authorize>");
		sb.append("</tr>");
		sb.append("  </thead>");
		sb.append("  <tbody>");
		for (User user : userList) {
			sb.append("<tr>");
			sb.append(" <th scope=\"row\">" + user.getLogin() + "</td>");
			sb.append("<td>" + user.getFirstName() + "</td>");
			sb.append("<td>" + user.getLastName() + "</td>");
			sb.append("<td>" + calculateAge(user.getBirthday()) + "</td>");
			sb.append("<td>" + UserProfileType.getType(user) + "</td>");

			sb.append("<td>");

			sb.append(" <sec:authorize access=\"hasRole('ADMIN')\">\r\n" + "");

			sb.append("<a href=" + edit + user.getLogin() + ">edit</a>&nbsp &nbsp");
			sb.append("<a href='" + delete + user.getLogin() + "'" + onClick + "\">delete</a>");

			sb.append(" </sec:authorize>\r\n" + "");
			sb.append("</tr>");
		}
		sb.append("  </tbody>");
		sb.append("  </table>");
		LOG.info(sb.toString());

		out.println(sb.toString());

	}

	private int calculateAge(Date date) {
		LocalDate localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
		if ((date != null)) {
			return Period.between(localDate, LocalDate.now()).getYears();
		} else {
			return 0;
		}
	}
}
