/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.services.ServiceUser;

/**
 *
 * @author MAB
 */
public class LoginForm extends BaseForm {

    Form current;

    public LoginForm() {
        super(new BorderLayout());
        current = this;
        if (!Display.getInstance().isTablet()) {
            BorderLayout bl = (BorderLayout) getLayout();
            bl.defineLandscapeSwap(BorderLayout.NORTH, BorderLayout.EAST);
            bl.defineLandscapeSwap(BorderLayout.SOUTH, BorderLayout.CENTER);
        }

        setUIID("SignIn");

        add(BorderLayout.NORTH, new Label(MyApplication.theme.getImage("logo.png"), "LogoLabel"));

        TextField username = new TextField("", "Username", 20, TextField.ANY);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        Button signIn = new Button("Sign In");
        Button signUp = new Button("Sign Up");
        signUp.addActionListener(e -> new SignupForm(current).show());
        signUp.setUIID("Link");
        Label doneHaveAnAccount = new Label("Don't have an account?");
        username.getAllStyles().setMargin(Component.TOP, 30);
        Label errorLabel = new Label("");
        Container errorContainer = new Container(BoxLayout.xCenter());
        errorLabel.getAllStyles().setFgColor(0xcf0000);
        errorContainer.add(errorLabel);
        Button resetPassBtn = new Button("Forgot Password ?");
        resetPassBtn.setUIID("Link");
        resetPassBtn.addActionListener(l -> new ResetPasswordForm(current).show());

        Container content = BoxLayout.encloseY(
                username,
                createLineSeparator(),
                password,
                createLineSeparator(),
                errorContainer,
                signIn,
                FlowLayout.encloseCenter(doneHaveAnAccount, signUp),
                FlowLayout.encloseCenter(resetPassBtn)
        );
        content.setScrollableY(false);
        add(BorderLayout.CENTER, content);
        signIn.requestFocus();
        signIn.addActionListener(e -> {
            if (username.getText() == "" || password.getText() == "") {
                errorLabel.setText("All fields are required!");
            } else {
                ServiceUser.getInstance().loginUser(username.getText(), password.getText());
                if (MyApplication.loggedUser.getUsername() != null) {
                    errorLabel.setText("");
                    //com.codename1.uikit.cleanmodern.ProfileForm p1 = new com.codename1.uikit.cleanmodern.ProfileForm(MyApplication.theme);
                    Form p1 = new ListGamesForm();
                    p1.show();
                    //new NewsfeedForm(MyApplication.theme).show()
                } else {
                    errorLabel.setText("Invalid credentials.");
                }
            }
        });

    }
}
