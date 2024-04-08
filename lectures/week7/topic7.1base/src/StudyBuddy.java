/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class StudyBuddy extends DreamGeek{
    private String institution;
    private String course;
    private String subjectArea;

    /**
     * @param gender   an enum value representing the Geek's gender (male, female or other)
     * @param starSign an enum representing the geek's star sign
     * @param institution      a String representing where the geek is studying, e.g. University of New England
     * @param course           a String representing what the geek is studying, e.g. Diploma of Information Technology
     * @param subjectArea      a String representing the general discipline the geek is studying, e.g. IT
     *
     */
    public StudyBuddy(Gender gender, StarSign starSign, String institution, String course, String subjectArea){
        super(gender, starSign);
        this.institution=institution;
        this.course=course;
        this.subjectArea=subjectArea;
    }

    /**
     * @param minAge   the lowest age a user desires
     * @param maxAge   the highest age a user desires
     * @param gender   an enum value representing the Geek's gender (male, female or other)
     * @param starSign an enum representing the geek's star sign
     * @param institution      a String representing where the geek is studying, e.g. University of New England
     */
    public StudyBuddy(int minAge, int maxAge, Gender gender, StarSign starSign, String institution, String course){
        super(minAge, maxAge, gender, starSign);
        this.institution=institution;
        this.course=course;
    }

    /**
     * @param minAge   the lowest age a user desires
     * @param maxAge   the highest age a user desires
     * @param gender   an enum value representing the Geek's gender (male, female or other)
     * @param starSign an enum representing the geek's star sign
     * @param subjectArea      a String representing the general discipline the geek is studying, e.g. IT
     */
    public StudyBuddy(int minAge, int maxAge, Gender gender, StarSign starSign, String subjectArea){
        super(minAge, maxAge, gender, starSign);
        this.subjectArea=subjectArea;
    }

    /**
     * @return the university of institution the geek is studying at
     */
    public String getInstitution() {
        return institution;
    }

    /**
     * @return the course/program the geek is studying
     */
    public String getCourse() {
        return course;
    }

    /**
     * @return the subject area or field the geek is in
     */
    public String getSubjectArea() {
        return subjectArea;
    }

    @Override
    public String getDescription() {
        return super.getDescription()+" They are currently studying "+getCourse()+" at "+getInstitution()+" in the field of "+getSubjectArea();
    }

    @Override
    public boolean matches(DreamGeek realGeek) {
        if(realGeek instanceof StudyBuddy geekStudyBuddy) {
            if (!super.matches(realGeek)) return false;
            if (geekStudyBuddy.getSubjectArea().equals(this.getSubjectArea())) return true;
            return geekStudyBuddy.getInstitution().equals(this.getInstitution()) && geekStudyBuddy.getCourse().equals(this.getCourse());
        }
        return false;
    }
}
