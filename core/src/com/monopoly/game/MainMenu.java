package com.monopoly.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu {
    Texture texture;
    float width, height;
    private Stage stage;
    private SpriteBatch batch;
    private Table  table;
    private int currentNumber = 2400;
    private Skin mySkin;
    private MainGame game;
    public MainMenu(Stage stage, SpriteBatch batch){
        this.stage=stage;
        this.batch=batch;
        texture = new Texture("monopolyMenu.jpg");
        mySkin=new Skin(Gdx.files.internal("gdx-skins-master/default/skin/uiskin.json"));
        makeMenu();
    }

    private void makeMenu() {
        table = new Table();
        table.setFillParent(true);
        table.top();
        table.pad(50);// Makes the table fill the stage

        stage.addActor(table);

        Label label = generateLabel(80, "Monopoly!");

        table.add(label);
        table.row();

        TextButton buttonStart = generateTextButton(50,"Start");
        buttonStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game=new MainGame(stage,batch);
            }
        });
        table.add(buttonStart).width(400).height(80);
        table.row();
        Table spinnerTable = new Table();
       // spinnerTable.setFillParent(true);
       // spinnerTable.setDebug(true); // Optional, for visual debugging

        // Create the text field
        TextField textField = new TextField(String.valueOf(currentNumber), mySkin);
        textField.setDisabled(true); // Disable input directly to the text field
        // Create the increment button
        TextButton incrementButton = generateTextButton(40,"+");
        incrementButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentNumber+=100;
                textField.setText(String.valueOf(currentNumber));
            }
        });

        // Create the decrement button
        TextButton decrementButton = generateTextButton(40,"-");
        decrementButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentNumber-=100;
                if(currentNumber<0){
                    currentNumber=0;
                }
                textField.setText(String.valueOf(currentNumber));
            }
        });
        Label labelInitMoney=generateLabel(40, "Initial sum of money");
        // Add elements to the table
        spinnerTable.add(labelInitMoney);
        spinnerTable.add(decrementButton);
        spinnerTable.add(textField).width(60);
        spinnerTable.add(incrementButton);

         table.add(spinnerTable);
         table.row();
        TextButton buttonClose = generateTextButton(50,"Close Window");
        buttonClose.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });
        table.add(buttonClose).width(400).height(80);

    }

    public void update() {
        if(game==null){
            batch.draw(texture,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        else {
           game.update();
        }
    }

    public void dispose() {
        texture.dispose();
    }
    private TextButton generateTextButton(int fontSize, String text){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("truetypefont/Amble-Light.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.borderWidth = 1;
        parameter.color = Color.WHITE;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        //parameter.shadowColor = new Color(0, 0.5f, 0, 0.75f);
        BitmapFont superFont = generator.generateFont(parameter);// font size 24 pixels
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = superFont;
        TextButton button = new TextButton(text, mySkin);
        Label buttonLabel = button.getLabel();
        buttonLabel.setStyle(labelStyle);
        return button;
    }
    private Label generateLabel(int fontSize, String text){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("truetypefont/Amble-Light.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.borderWidth = 1;
        parameter.color = Color.WHITE;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        //parameter.shadowColor = new Color(0, 0.5f, 0, 0.75f);
        BitmapFont superFont = generator.generateFont(parameter);// font size 24 pixels
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = superFont;

        return new Label(text, labelStyle);
    }


}
