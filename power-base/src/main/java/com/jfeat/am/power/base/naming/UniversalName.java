package com.jfeat.am.power.base.naming;

/**
 * Created by vincent on 2018/2/27.
 */
public class UniversalName {
    private String firstName;
    private String middleName;
    private String lastName;

    public UniversalName(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UniversalName(String firstName, String middleName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;

        /// ensure
        if(this.middleName != null) {
            if (!firstName.matches("\\w+") || !lastName.matches("\\w+")) {
                // that's chinese
                this.middleName = null;
            }
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString(){

        // if there is middle, that's west style name
        if(this.middleName != null && this.middleName.length()>0){
            return this.firstName + " " + this.lastName + " " + middleName;
        }

        if(this.firstName.matches("\\w+") && this.lastName.matches("\\w+")){
            return this.firstName + " " + this.lastName;
        }

        return this.lastName + this.firstName;
    }
}
