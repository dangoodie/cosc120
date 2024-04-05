/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class StudyBuddy extends DreamGeek{

    //these fields are unique to StudyBuddy, and add to the DreamGeek functionality
    private final String institution;
    private final String course;
    private final String subjectArea;

    /**
     * @param minAge   the lowest age a user desires
     * @param maxAge   the highest age a user desires
     * @param gender   an enum value representing the Geek's gender (male, female or other)
     * @param starSign an enum representing the geek's star sign
     * @param institution      a String representing where the geek is studying, e.g. University of New England
     * @param course           a String representing what the geek is studying, e.g. Diploma of Information Technology
     * @param subjectArea      a String representing the general discipline the geek is studying, e.g. IT
     */
    public StudyBuddy(int minAge, int maxAge, Gender gender, StarSign starSign, String institution, String course, String subjectArea) {
        super(minAge, maxAge, gender, starSign);
        this.institution=institution;
        this.course=course;
        this.subjectArea=subjectArea;
    }

    //getters
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

    //This is how you override DreamGeek's getDescription method
    //Note the use of super.getDescription() invokes DreamGeek's getDescription() method, to print the geek's star sign and gender
    //Notice the method name, return type and parameter list are the same as the superclass (DreamGeek)
    @Override
    public String getDescription() {
        return super.getDescription()+" They are currently studying "+getCourse()+" at "+getInstitution()+" in the field of "+getSubjectArea();
    }

    //This is how you override DreamGeek's matches method
    //Note the use of super.matches() invokes DreamGeek's matches() method, to ensure the age range, star sign and gender are compatible
    //Notice the method name, return type and parameter list are the same as the superclass (DreamGeek)
    //This method takes advantage of polymorphism = because a StudyBuddy IS-A DreamGeek, a DreamGeek can be passed in as a parameter and used as a StudyBuddy
    @Override
    public boolean matches(DreamGeek realGeek) {
        //If the realGeek is a StudyBuddy (not a Friend or MoreThanAFriend), check if the StudyBuddy fields are compatible with the realGeek
        if(realGeek instanceof StudyBuddy geekStudyBuddy) {
            if (!super.matches(realGeek)) return false;
            //if the superclass matches method returns true, test the StudyBuddy compatibility
            if (geekStudyBuddy.getSubjectArea().equals(this.getSubjectArea())) return true;
            return geekStudyBuddy.getInstitution().equals(this.getInstitution()) && geekStudyBuddy.getCourse().equals(this.getCourse());
            //if neither the subject are nor the institution/course have returned true, then they're not compatible
        }
        return false;
    }
}
