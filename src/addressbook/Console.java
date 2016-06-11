/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addressbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Igor Gayvan
 */
public class Console {

    private Scanner scanner;

    private List<ActionListener> actionListeners;
    private List<ShowDataListener> showDataListeners;
    private List<SortActionListener> sortActionListeners;

    private String modeWorking = "CHOICE_MODE";

    private String inputText;

    public Console(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);

        this.actionListeners = new ArrayList<>();
        this.showDataListeners = new ArrayList<>();
        this.sortActionListeners = new ArrayList<>();
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    public void addSortActionListener(SortActionListener sortActionListener) {
        sortActionListeners.add(sortActionListener);
    }

    public void addShowDataListener(ShowDataListener showDataListener) {
        showDataListeners.add(showDataListener);
    }

    public String getInputText() {
        return inputText.trim();
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getModeWorking() {
        return modeWorking;
    }

    /**
     *
     * @param modeWorking ADD_CONTACT - добавление, CHOICE_MODE - главное меню,
     * SHOW_CONTACT - отображение данных о контакте
     *
     */
    public void setModeWorking(String modeWorking) {
        this.modeWorking = modeWorking;
    }

    public void working() {
        while (true) {
            if (modeWorking == "CHOICE_MODE") {
                System.out.println("1 - show list of contacts");
                System.out.println("2 - add new contact");
                System.out.println("3 - show information about contact");
                System.out.println("5 - refresh");
                System.out.println("7 - sort by phone");
                System.out.println("8 - sort by any field");
                System.out.println("9 - filter by full name");
                System.out.println("10 - delete contact");
                System.out.println("11 - update contact");
                System.out.println("0 - exit");
                System.out.print("Your choice? ");
            }

            inputText = scanner.nextLine().trim();

            switch (modeWorking) {
                case "FILTER_BY_FULL_NAME": {
                    for (ShowDataListener addressBookListeners : showDataListeners) {
                        addressBookListeners.filterContactsByFullNameAction();
                    }
                    break;
                }
                case "SHOW_CONTACT": {
                    for (ShowDataListener addressBookListeners : showDataListeners) {
                        addressBookListeners.showContactAction();
                    }
                    break;
                }
                case "ADD_CONTACT": {
                    for (ActionListener actionListeners : actionListeners) {
                        actionListeners.addContactAction();
                    }
                    break;
                }
                case "DEL_CONTACT": {
                    for (ActionListener actionListeners : actionListeners) {
                        actionListeners.delContactAction();
                    }
                    break;
                }
                case "UPD_CONTACT": {
                    for (ActionListener actionListeners : actionListeners) {
                        actionListeners.updContactAction();
                    }
                    break;
                }
                case "SORT_BY_ANY_FIELD": {
                    for (SortActionListener sortActionListener : sortActionListeners) {
                        sortActionListener.sortByAnyField();
                    }
                    break;
                }
                case "CHOICE_MODE":
                    switch (inputText.toLowerCase().trim()) {
                        case "0":
                            for (ActionListener actionListener : actionListeners) {
                                actionListener.exitAction();
                            }
                            break;
                        case "1":
                            for (ShowDataListener showDataListener : showDataListeners) {
                                showDataListener.showListContactsAction();
                            }
                            break;
                        case "2":
                            setModeWorking("ADD_CONTACT");
                            for (ShowDataListener showDataListener : showDataListeners) {
                                showDataListener.showPromptInputContactAction();
                            }
                            break;
                        case "10":
                            setModeWorking("DEL_CONTACT");
                            for (ShowDataListener showDataListener : showDataListeners) {
                                showDataListener.showPromptInputContactIdAction();
                            }
                            break;                            
                        case "11":
                            setModeWorking("UPD_CONTACT");
                            for (ShowDataListener showDataListener : showDataListeners) {
                                showDataListener.showPromptInputContactIdAction();
                            }
                            break;                            
                        case "3":
                            setModeWorking("SHOW_CONTACT");
                            for (ShowDataListener showDataListener : showDataListeners) {
                                showDataListener.showPromptInputContactIdAction();
                            }
                            break;
                        case "5":
                            setModeWorking("REFRESH");
                            for (ActionListener actionListener : actionListeners) {
                                actionListener.refreshDataAction();
                            }
                            break;
                        case "7":
                            setModeWorking("SORT_BY_PHONE");
                            for (SortActionListener sortActionListener : sortActionListeners) {
                                sortActionListener.sortByPhoneAction();
                            }
                            break;
                        case "8":
                            setModeWorking("SORT_BY_ANY_FIELD");
                            for (SortActionListener sortActionListener : sortActionListeners) {
                                sortActionListener.sortByAnyFieldAction();
                            }
                            break;
                        case "9":
                            setModeWorking("FILTER_BY_FULL_NAME");
                            for (ShowDataListener showDataListener : showDataListeners) {
                                showDataListener.showPromptInputFilterNameFullAction();
                            }
                            break;

                        default:
                            System.out.println("Make your choice");
                    }

            }
        }
    }

}
