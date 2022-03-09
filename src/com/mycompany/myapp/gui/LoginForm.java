/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.mycompany.myapp.MyApplication;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServiceUser;

/**
 *
 * @author MAB
 */
public class LoginForm extends Form {

    Form current;

    public LoginForm() {
        current = this; //Récupération de l'interface(Form) en cours
        setTitle("Login");
        setLayout(BoxLayout.yCenter());
        Container registerContainer = new Container(BoxLayout.xCenter());
        Label registerLabel = new Label("Dont't have an account ? ");
        TextField usernameField = new TextField("", "Username");
        TextField passwordField = new TextField("", "Password", 1, TextField.PASSWORD);
        Button submitBtn = new Button("Submit");
        submitBtn.getAllStyles().setBorder(RoundBorder.create().
        rectangle(true).
        color(0x0000FF).
        strokeColor(0).
        strokeOpacity(120).
        stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1)));
        submitBtn.getAllStyles().setFgColor(0xffffff);
        Button registerBtn = new Button("Register");
        Label errorLabel = new Label("");
        Container errorContainer = new Container(BoxLayout.xCenter());
        errorLabel.getAllStyles().setFgColor(0xcf0000);
        errorContainer.add(errorLabel);
        registerContainer.addAll(registerLabel, registerBtn);
        submitBtn.addActionListener(e -> {
            if (usernameField.getText() == "" || passwordField.getText() == "") {
                errorLabel.setText("All fields are required!");
            } else {
                ServiceUser.getInstance().loginUser(usernameField.getText(), passwordField.getText());
                if (MyApplication.loggedUser.getUsername() != null) {
                    errorLabel.setText("");
                    ProfileForm p1 = new ProfileForm();
                    p1.show();
                } else {
                    errorLabel.setText("Invalid credentials.");
                }
            }
        });

        registerBtn.addActionListener(e -> {
            errorLabel.setText("");
            Form p1 = new SignupForm(this);
            p1.show();
        });
        // btnListTasks.addActionListener(e -> new ListGamesForm(current).show());
        addAll(usernameField, passwordField, errorContainer, submitBtn, registerContainer);

    }
}
