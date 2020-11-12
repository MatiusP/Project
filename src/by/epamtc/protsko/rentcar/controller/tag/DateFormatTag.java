package by.epamtc.protsko.rentcar.controller.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateFormatTag extends TagSupport {
    private static final long serialVersionUID = 6618704739219069124L;

    private static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy";
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.write(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN).format(convertDateToLocalDate(date)));
        } catch (IOException e) {
            //logger
        }
        return SKIP_BODY;
    }

    private LocalDate convertDateToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
