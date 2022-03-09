/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.MyApplication;
import com.mycompany.myapp.services.ServiceUser;

/**
 *
 * @author MAB
 */
public class SignupForm extends Form {
    public SignupForm(Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

        setTitle("Sign Up");
        setLayout(BoxLayout.yCenter());

        TextField usernameField = new TextField("", "Username");
        TextField emailField = new TextField("", "Email");
        TextField passwordField = new TextField("", "Password", 1, TextField.PASSWORD);
        TextField confirmPasswordField = new TextField("", "Confirm Password", 1, TextField.PASSWORD);
        Button submitBtn = new Button("Register");
        Label errorLabel = new Label("");
        Container errorContainer = new Container(BoxLayout.xCenter());
        errorLabel.getAllStyles().setFgColor(0xcf0000);
        errorContainer.add(errorLabel);

        submitBtn.addActionListener(e -> {
            if (usernameField.getText() == "" || passwordField.getText() == "" || confirmPasswordField.getText() == "" || emailField.getText() == "") {
                errorLabel.setText("All fields are required!");
            } else {
                ServiceUser.getInstance().registerUser(usernameField.getText(), passwordField.getText(), confirmPasswordField.getText(), emailField.getText());
                if (MyApplication.loggedUser.getUsername() != null) {
                    errorLabel.setText("");
                    ProfileForm p1 = new ProfileForm();
                    p1.show();
                } else {
                    errorLabel.setText("Invalid credentials.");
                }
            }
        });
        // btnListTasks.addActionListener(e -> new ListGamesForm(current).show());
        addAll(usernameField, emailField, passwordField, confirmPasswordField, errorContainer, submitBtn);
    }
}
