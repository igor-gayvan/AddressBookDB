/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addressbook.subject.contact;

import addressbook.subject.EntityAddressBook;

/**
 *
 * @author Igor Gayvan
 */
public class Contact extends EntityAddressBook implements Comparable<Contact> {

    private int id;
    private String nameFull;
    private String phone;
    private String email;
    private String skype;

    private static ContactFields compareField;
    private static int sortAsc = 1;

    private ContactFields currentInputField;

    public Contact() {
        super();
    }

    public Contact(int id) {
        super(id);
        this.id = id;
    }

    public Contact(int id, String nameFull, String phone, String email, String skype) {
        super(id);
        this.id = id;
        this.nameFull = nameFull;
        this.phone = phone;
        this.email = email;
        this.skype = skype;
    }

    public static ContactFields getCompareField() {
        return compareField;
    }

    public static void setCompareField(ContactFields compareField) {
        if ((Contact.compareField != null) && Contact.compareField.equals(compareField)) {
            Contact.sortAsc = Contact.sortAsc * -1;
        } else {
            Contact.sortAsc = 1;
        }
        Contact.compareField = compareField;
    }

    public static int getSortAsc() {
        return sortAsc;
    }

    public static void setSortAsc(byte sortAsc) {
        Contact.sortAsc = sortAsc;
    }

    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public ContactFields getCurrentInputField() {
        return currentInputField;
    }

    public void setCurrentInputField(ContactFields currentInputField) {
        this.currentInputField = currentInputField;
    }

    public void showContact() {
        System.out.println("\nContact's info ");
        System.out.printf("ID: %s%n", this.id);
        System.out.printf("Full name: %s%n", this.nameFull);
        System.out.printf("Phone: %s%n", this.phone);
        System.out.printf("Email: %s%n", this.email);
        System.out.printf("Skype: %s%n", this.skype);
        System.out.println("");
    }

    public void showPromptInputContact() {
        if (currentInputField == null) {
            currentInputField = ContactFields.NAME_FULL;
            System.out.println("\nInput contact's data:");
        }

        switch (currentInputField) {
            case NAME_FULL:
                System.out.printf("Input full name%s:", this.nameFull != null ? " (current value - " + this.nameFull + ")" : "");
                break;
            case PHONE:
                System.out.printf("Input phone%s:", this.phone != null ? " (current value - " + this.phone + ")" : "");
                break;
            case EMAIL:
                System.out.printf("Input email%s:", this.email != null ? " (current value - " + this.email + ")" : "");
                break;
            case SKYPE:
                System.out.printf("Input skype%s:", this.skype != null ? " (current value - " + this.skype + ")" : "");
                break;
        }
    }

    @Override
    public int compareTo(Contact contact) {
        int resultCompare = 0;

        switch (Contact.getCompareField()) {
            case NAME_FULL: {
                resultCompare = this.getNameFull().compareTo(contact.getNameFull());
                break;
            }
            case PHONE: {
                resultCompare = this.getPhone().compareTo(contact.getPhone());
                break;
            }
            case ID: {
                resultCompare = this.getId() >= contact.getId() ? 1 : -1;
                break;
            }
            case SKYPE: {
                resultCompare = this.getSkype().compareTo(contact.getSkype());
                break;
            }
            case EMAIL: {
                resultCompare = this.getEmail().compareTo(contact.getEmail());
                break;
            }
        }

        return Contact.getSortAsc() * resultCompare;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Contact cn = (Contact) object;

        return getId() == cn.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    public static ContactFields getCodeFieldByName(String fieldName) {
        ContactFields contactFields = null;

        switch (fieldName) {
            case "id":
                contactFields = ContactFields.ID;
                break;
            case "phone":
                contactFields = ContactFields.PHONE;
                break;
            case "email":
                contactFields = ContactFields.EMAIL;
                break;
            case "skype":
                contactFields = ContactFields.SKYPE;
                break;
            case "namefull":
                contactFields = ContactFields.NAME_FULL;
                break;
        }

        return contactFields;
    }
}
