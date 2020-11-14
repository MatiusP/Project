package by.epamtc.protsko.rentcar.controller.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static final Logger logger = LogManager.getLogger(DateFormatTag.class);
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
            logger.error("Error while writing formatting date to response", e);
        }
        return SKIP_BODY;
    }

    private LocalDate convertDateToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
