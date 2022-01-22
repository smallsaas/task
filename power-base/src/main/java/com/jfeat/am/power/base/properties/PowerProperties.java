package com.jfeat.am.power.base.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "power")
public class PowerProperties {

    private SubmissionProperties submission = new SubmissionProperties();

    public SubmissionProperties getSubmission() {
        return submission;
    }

    public void setSubmission(SubmissionProperties submission) {
        this.submission = submission;
    }

    /**
     * submission config
     */
    public class SubmissionProperties {
        private String fileUploadPath;
        private String fileHost;

        public String getFileUploadPath() {
            return fileUploadPath;
        }

        public void setFileUploadPath(String fileUploadPath) {
            this.fileUploadPath = fileUploadPath;
        }

        public String getFileHost() {
            return fileHost;
        }

        public void setFileHost(String fileHost) {
            this.fileHost = fileHost;
        }
    }
}
