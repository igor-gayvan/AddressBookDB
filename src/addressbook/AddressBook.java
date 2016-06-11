/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addressbook;

import addressbook.subject.contact.Contact;
import addressbook.subject.contact.ContactFields;
import addressbook.listeners.SortActionListener;
import addressbook.listeners.ActionListener;
import addressbook.listeners.ShowDataListener;
import addressbook.database.dao.ContactDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Igor Gayvan
 */
public class AddressBook {

    static List<Contact> contactList = new ArrayList<Contact>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Console console = new Console(System.in);
        Contact contact = new Contact();
        ShowData showData = new ShowData();

        ContactDAO contactDAO = new ContactDAO();
        contactList = contactDAO.selectAll();

        console.addActionListener(
                new ActionListener() {
            // Выход
            @Override
            public void exitAction() {
                System.exit(0);
            }

            // Добавление нового контакта
            @Override
            public void addContactAction() {
                ShowData.showAddContact(contact, console.getInputText());

                if (contact.getCurrentInputField() == null) {
                    if (contactDAO.insert(contact)) {
                        contactList.add(contact);
                        System.out.println("\nRecord was inserted\n");
                    } else {
                        System.out.println("Error during inserting");
                    }

                    console.setModeWorking(ConsoleModeWorking.CHOICE_MODE);
                } else {
                    contact.showPromptInputContact();
                }
            }

            // Изменение существующего контакта
            @Override
            public void updContactAction() {
                int tmpId = Integer.valueOf(console.getInputText());
                int indexOfCurContact = contactList.indexOf(new Contact(tmpId));

                Contact contactUpd = contactList.get(indexOfCurContact);
                contactUpd.setCurrentInputField(ContactFields.NAME_FULL);

                Scanner scanner = new Scanner(System.in);

                while (contactUpd.getCurrentInputField() != null) {
                    contactUpd.showPromptInputContact();

                    String newValue = scanner.nextLine().trim();

                    ShowData.showAddContact(contactUpd, newValue);
                }

                contactUpd.setId(tmpId);

                if (contactDAO.update(contactUpd)) {
                    contactList.set(indexOfCurContact, contactUpd);

                    System.out.printf("Contact with id == %d was updated!%n", tmpId);
                } else {
                    System.out.println("Error during updating");
                }

                console.setModeWorking(ConsoleModeWorking.CHOICE_MODE);
            }

            // Удаление существующего контакта
            @Override
            public void delContactAction() {
                int tmpId = Integer.valueOf(console.getInputText());
                contactDAO.delete(tmpId);
                if (contactList.removeIf(contact -> contact.getId() == tmpId)) {
                    System.out.printf("Contact with id == %d was deleted!%n", tmpId);
                } else {
                    System.out.println("Error during deleting...");
                }

                console.setModeWorking(ConsoleModeWorking.CHOICE_MODE);
            }

            @Override
            public void refreshDataAction() {
                contactList = contactDAO.selectAll();
                console.setModeWorking(ConsoleModeWorking.CHOICE_MODE);
            }
        }
        );

        console.addShowDataListener(
                new ShowDataListener() {

            // Показываем приглашения для ввода данных контакта
            @Override
            public void showPromptInputContactAction() {
                contact.showPromptInputContact();
            }

            // Показываем список контактов
            @Override
            public void showListContactsAction() {
                ShowData.showListContact(contactList);
            }

            // Показываем приглашения для ввода ID контакта
            @Override
            public void showPromptInputContactIdAction() {
                showData.showPromptInputContactId();
            }

            // Показываем данные контакта
            @Override
            public void showContactAction() {
                showData.showContactInfo(contactList, Integer.valueOf(console.getInputText()));
                console.setModeWorking(ConsoleModeWorking.CHOICE_MODE);
            }

            // Показываем приглашения для полного наименования для фильтрации
            @Override
            public void showPromptInputFilterNameFullAction() {
                ShowData.showPromptInputFilterNameFull();
            }

            // Фильтруем список контактов по полному наименованию            
            @Override
            public void filterContactsByFullNameAction() {
                contactDAO.setFilterNameFull(console.getInputText());
                contactList = contactDAO.selectAll();
                ShowData.showListContact(contactList);

                console.setModeWorking(ConsoleModeWorking.CHOICE_MODE);
            }
        }
        );

        console.addSortActionListener(
                new SortActionListener() {

            // Сортируем список контактов по телефону и показываем его
            @Override
            public void sortByPhoneAction() {
                ShowData.showListContact(contactList, ContactFields.PHONE);
                console.setModeWorking(ConsoleModeWorking.CHOICE_MODE);
            }

            // Сортируем список контактов по указанному полю и показываем его
            @Override
            public void sortByAnyFieldAction() {
                ShowData.showPromptFieldForSort();
            }

            @Override
            public void sortByAnyField() {
                ShowData.showListContact(contactList, Contact.getCodeFieldByName(console.getInputText().toLowerCase()));

                console.setModeWorking(ConsoleModeWorking.CHOICE_MODE);
            }
        }
        );

        console.working();

        contactDAO.close();
    }
}
