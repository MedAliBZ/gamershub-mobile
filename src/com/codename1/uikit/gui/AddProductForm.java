/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.uikit.entities.Categories;
import com.codename1.uikit.entities.Products;
import com.codename1.uikit.services.ServiceCategories;
import com.codename1.uikit.services.ServiceProducts;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author meriam
 */
public class AddProductForm extends Form {

    String ch;

    public AddProductForm(Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        setTitle("Add Product");
        setLayout(BoxLayout.y());
        this.setUIID("Activate");
        TextField tfName = new TextField("", "Product Name");
        TextField tfDes = new TextField("", "Product Description");
        //TextField tfCatID = new TextField("", "Category ID");
        TextField tfPrice = new TextField("", "Price");
        TextField tfQuantityStocked = new TextField("", "Quantity in stock");
//combo 

        List<Categories> category = new ArrayList();
        category = ServiceCategories.getInstance().getAllCategories();

        Label CategoryLabel = new Label("Category");

        ComboBox categoryBox = new ComboBox();
       
        for (int i = 0; i < category.size(); i++) {
            Categories c = category.get(i);
            categoryBox.addItem(c.getId() + "," + c.getNameCategory());
        }
       // System.out.println(categoryBox.getSelectedItem().toString());
                Container comboCtn = new Container(new BoxLayout(BoxLayout.X_AXIS));
             comboCtn.addAll(CategoryLabel,categoryBox);
        //upload image
        Label imageLabel = new Label("Image");
        Button selectImage = new Button("Select");
        TextField imageField = new TextField("", "Select picture", 10, TextArea.ANY);
        imageField.setEditable(false);

        selectImage.addActionListener((evt) -> {
            Display.getInstance().openGallery((e) -> {
                if (e != null && e.getSource() != null) {
                    String filePath = (String) e.getSource();
                    imageField.setText(filePath.substring(filePath.lastIndexOf('/') + 1));
                    ch = filePath;
                }
            }, Display.GALLERY_IMAGE
            );

        }
        );

        Container photoContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));

        photoContainer.add(imageLabel);
        photoContainer.add(imageField);
        photoContainer.add(selectImage);

        Button btnSubmit = new Button("Submit");

        btnSubmit.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfName.getText().length() == 0) || (tfDes.getText().length() == 0) || (imageField.getText().length() == 0)
                        || (tfPrice.getText().length() == 0) || (tfQuantityStocked.getText().length() == 0)) {
                    Dialog.show("Alert", "please fill all the Fields", new Command("ok"));
                } else {

                    Products c = new Products();
                    String catId = categoryBox.getSelectedItem().toString();
                    StringTokenizer st = new StringTokenizer(catId,",");

                    c.setCategoryId(Integer.parseInt(st.nextToken()));
                    c.setDescription(tfDes.getText());
                    c.setImage(ch);
                    c.setNameProduct(tfName.getText());
                    c.setPrice(Double.parseDouble(tfPrice.getText()));
                    c.setQuantityStocked(Integer.parseInt(tfQuantityStocked.getText()));

                    if (ServiceProducts.getInstance().addProduct(c)) {
                        new ListProductsForm().show();
                    } else {
                        Dialog.show("erreur", "connection Failed", new Command("ok"));

                    }

                }
            }

        }
        );
        addAll(tfName, tfDes, photoContainer, tfPrice, tfQuantityStocked, comboCtn, btnSubmit);

    }

}
