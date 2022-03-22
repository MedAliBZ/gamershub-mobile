/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.services.ServiceUser;

/**
 *
 * @author MAB
 */
public class UpdatePasswordForm extends BaseForm {

    public UpdatePasswordForm(Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

        setTitle("Update Password");
        setLayout(BoxLayout.yCenter());
        setUIID("Activate");

        TextField passwordField = new TextField("", "New Password", 1, TextField.PASSWORD);
        TextField confirmPasswordField = new TextField("", "Confirm Password", 1, TextField.PASSWORD);
        TextField oldPasswordField = new TextField("", "Old Password", 1, TextField.PASSWORD);
        Button submitBtn = new Button("Register");
        Label errorLabel = new Label("");
        Container errorContainer = new Container(BoxLayout.xCenter());
        errorLabel.getAllStyles().setFgColor(0xcf0000);
        errorContainer.add(errorLabel);

        submitBtn.addActionListener(e -> {
            if (oldPasswordField.getText() == "" || passwordField.getText() == "" || confirmPasswordField.getText() == "") {
                errorLabel.setText("All fields are required!");
            } else if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                errorLabel.setText("Passwords do not match!");
            } else {
                ServiceUser.getInstance().updateUserPassword(oldPasswordField.getText(),passwordField.getText());
                errorLabel.setText(MyApplication.loggedUser.getError());
                if(MyApplication.loggedUser.getError().equals(""))
                    previous.show();
            }
        });
        addAll(passwordField, confirmPasswordField, oldPasswordField, errorContainer, submitBtn);
    }
}
