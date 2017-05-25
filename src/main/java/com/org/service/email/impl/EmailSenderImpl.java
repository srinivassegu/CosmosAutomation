package com.org.service.email.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.CollectionUtils;

import com.org.model.EmployeeBean;
import com.org.model.TimeSheetBean;
import com.org.service.email.EmailSender;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Component
public class EmailSenderImpl implements EmailSender {

    private static Logger logger = LoggerFactory.getLogger(EmailSenderImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SimpleMailMessage templateMailMessage;

    @Autowired
    private Configuration freemarkerConfiguration;

    @Override
    public void sendEmail(EmployeeBean employeeBean, List<TimeSheetBean> timeSheetBeans) {

        if (CollectionUtils.isEmpty(timeSheetBeans)) {
            logger.error("No emails are valid to send timesheet reminder, skipping!");
            return;
        }
        for (TimeSheetBean timeSheetBean : timeSheetBeans) {
            this.sendEmail(employeeBean, timeSheetBean);
        }
    }

    public void sendEmail(EmployeeBean employeeBean, TimeSheetBean timeSheetBean) {

        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) {
                try {
                    mimeMessage.setFrom(new InternetAddress(templateMailMessage.getFrom()));
                    addRecipients(mimeMessage, timeSheetBean.getConsultantEmailId());
                    BodyPart messageBodyPart = new MimeBodyPart();
                    String emailBody = getEmailBody(employeeBean, timeSheetBean);

                    mimeMessage.setSubject(getSubject(timeSheetBean.getStartDate().toString(), timeSheetBean.getEndDate()
                            .toString()));

                    messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(emailBody, "text/html")));

                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(messageBodyPart);
                    mimeMessage.setContent(multipart);
                } catch (Exception e) {
                    logger.error("Sending timesheet reminder failed! for emailId: {}, Error: {} ",
                            timeSheetBean.getConsultantEmailId(), e);
                }
            }
        };
        mailSender.send(messagePreparator);
        logger.info("Timesheet Reminder Sent Succesfully for emailId: {}", timeSheetBean.getConsultantEmailId());

    }

    private void addRecipients(MimeMessage mimeMessage, String emailId) {

        try {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailId));
        } catch (Exception e) {
            logger.error("Exception while adding recipients");
            throw new RuntimeException("Exception while adding recipients");
        }
    }

    private String getSubject(String startDate, String endDate) {

        String subject = String.format("Timesheet Submission Reminder for period: %s - %s!", startDate, endDate);
        return subject;
    }

    private String getEmailBody(EmployeeBean employeeBean, TimeSheetBean timeSheetBean) {

        Map<String, Object> data = new HashMap<String, Object>();

        data.put("consultantFirstName", timeSheetBean.getConsultantFirstName());
        data.put("startDate", timeSheetBean.getStartDate());
        data.put("endDate", timeSheetBean.getEndDate());
        data.put("submissionLink", this.formalateSubmissionLink(employeeBean.getOrganizationId(), timeSheetBean));
        data.put("mobileContact", employeeBean.getEmployeeContact());
        data.put("faxContact", employeeBean.getOrganizationFaxContact());
        data.put("employeeName", employeeBean.getEmployeeName());
        data.put("employeeDepartment", employeeBean.getEmployeeDepartment());
        data.put("organizationAddress", employeeBean.getOrganizationAddress());

        try {
            return FreeMarkerTemplateUtils.processTemplateIntoString(
                    this.getFreemarkerConfiguration().getTemplate("timesheet_reminder_email.ftl"), data);
        } catch (IOException | TemplateException e) {
            logger.error("{}", e);
            return "Error while generating email Body. Pls. contact support";
        }
    }

    private String formalateSubmissionLink(int organizationId, TimeSheetBean timeSheetBean) {
        StringJoiner sj = new StringJoiner("/");
        sj.add("https://s3.amazonaws.com/easyemailbucket/timesheets");
        sj.add("organization_" + organizationId);
        sj.add("consultant_" + timeSheetBean.getConsultantId());
        sj.add("weekending_" + timeSheetBean.getEndDate() + ".pdf");
        return sj.toString();
    }

    public Configuration getFreemarkerConfiguration() {
        return freemarkerConfiguration;
    }

    public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.templateMailMessage = simpleMailMessage;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

}
