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

import org.apache.log4j.Logger;

import com.nixsolutions.entity.User;
import com.nixsolutions.entity.UserType;

public class TableTag extends SimpleTagSupport {

	private List<User> userList;
	private String tableStyle;
	private static final Logger LOG = Logger.getLogger(TableTag.class);

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public void setTableStyle(String tableStyle) {
		this.tableStyle = tableStyle;
	}

	@Override
	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext) getJspContext();
		String edit = pageContext.getServletContext().getContextPath() + "/edit_controller";
		String delete = pageContext.getServletContext().getContextPath() + "/delete_controller";
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
		sb.append("</tr>");
		sb.append("  </thead>");
		sb.append("  <tbody>");
		for (User user : userList) {
			sb.append("<tr>");
			sb.append(" <th scope=\"row\">" + user.getLogin() + "</td>");
			sb.append("<td>" + user.getFirstName() + "</td>");
			sb.append("<td>" + user.getLastName() + "</td>");
			sb.append("<td>" + calculateAge(user.getBirthday()) + "</td>");
			sb.append("<td>" + UserType.getType(user) + "</td>");
			sb.append("<td>");
			sb.append("<form action=\"" + edit + "\" method=\"post\">");
			sb.append("<div>");
			sb.append("<input type=\"hidden\" name=\"login\" value=\"" + user.getLogin() + "\"/>");
			sb.append("<input type=\"submit\"class=\"submitLink\" value=\"Edit\"/>");
			sb.append("</div>");
			sb.append("</form>");
			sb.append("<form action=\"" + delete + "\" method=\"post\">");
			sb.append("<div>");
			sb.append("<input type=\"hidden\" name=\"login\" value=\"" + user.getLogin() + "\"/>");
			sb.append("<input type=\"submit\" class=\"submitLink\" value=\"Delete\" " + onClick + "/>");
			sb.append("</div>");
			sb.append("</form>");
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
