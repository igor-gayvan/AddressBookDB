/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addressbook.listeners;

/**
 *
 * @author human
 */
public interface ShowDataListener {

    public void showListContactsAction();

    public void showContactAction();

    public void showPromptInputContactAction();

    public void showPromptInputContactIdAction();

    public void showPromptInputFilterNameFullAction();

    public void filterContactsByFullNameAction();
}
