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
public class ResetPasswordForm  extends Form {
    public ResetPasswordForm(Form previous){
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

        setTitle("Reset Password");
        setLayout(BoxLayout.yCenter());
        setUIID("SignIn");

        TextField emailField = new TextField("", "Email");
        Button submitBtn = new Button("Send Email");
        Label errorLabel = new Label("");
        Container errorContainer = new Container(BoxLayout.xCenter());
        errorLabel.getAllStyles().setFgColor(0xcf0000);
        errorContainer.add(errorLabel);
        
        Label successLabel = new Label("");
        successLabel.getAllStyles().setFgColor(0x008000);
        Container successContainer = new Container(BoxLayout.xCenter());
        successContainer.add(successLabel);

        submitBtn.addActionListener(e -> {
            if (emailField.getText() == "") {
                errorLabel.setText("This field is required!");
                successLabel.setText("");
            } else {
                ServiceUser.getInstance().resetPassword(emailField.getText());
                successLabel.setText("An email has been sent!");
                errorLabel.setText("");
            }
        });
        // btnListTasks.addActionListener(e -> new ListGamesForm(current).show());
        addAll(emailField, successContainer, errorContainer, submitBtn);
    }
}
