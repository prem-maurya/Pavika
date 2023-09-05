package com.prem.pavikaa;

public class SubjectModel {
    int subjectImage;
    String subjectName, subjectPdf;

    public SubjectModel(int subjectImage, String subjectName, String subjectPdf) {
        this.subjectImage = subjectImage;
        this.subjectName = subjectName;
        this.subjectPdf = subjectPdf;
    }

    public SubjectModel() {
    }

    public int getSubjectImage() {
        return subjectImage;
    }

    public void setSubjectImage(int subjectImage) {
        this.subjectImage = subjectImage;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectPdf() {
        return subjectPdf;
    }

    public void setSubjectPdf(String subjectPdf) {
        this.subjectPdf = subjectPdf;
    }
}
